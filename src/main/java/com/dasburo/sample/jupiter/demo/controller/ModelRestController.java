package com.dasburo.sample.jupiter.demo.controller;

import com.dasburo.sample.jupiter.demo.data.ModelDTO;
import com.dasburo.sample.jupiter.demo.data.Response;
import com.dasburo.sample.jupiter.demo.exception.ConstraintsViolationException;
import com.dasburo.sample.jupiter.demo.exception.EntityNotFoundException;
import com.dasburo.sample.jupiter.demo.mapper.ModelMapper;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;
import com.dasburo.sample.jupiter.demo.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Rest controller to access to the {@link com.dasburo.sample.jupiter.demo.persistence.ModelEntity}
 *
 * @author Ana Esteban Garcia-Navas(<a href="mailto:ags@dasburo.com">anaesteban</a>)
 */
@RestController
@RequestMapping("/jupiter-demo/v1/models")
public class ModelRestController {

    private final ModelService modelService;

    @Autowired
    public ModelRestController(final ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/{modelId}")
    public ResponseEntity<Response> getModelsById(long modelId) {
        try {
            ModelEntity modelEntity = modelService.findModelById(modelId);
            return createSuccessfulResponse(modelEntity);
        } catch (EntityNotFoundException exception) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<Response> getAllModels() {
        try {
            List<ModelEntity> modelEntityList = modelService.findAllModels();
            return createSuccessfulResponse(modelEntityList);
        } catch (EntityNotFoundException exception) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        }

    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> createModel(@RequestBody ModelDTO inputModel) {
        try {
            ModelEntity modelEntity = modelService.createModel(ModelMapper.createDOfromDTO(inputModel));
            final URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(modelEntity.getId()).toUri();
            return createSuccessfulResponse(uri, modelEntity);
        } catch (ConstraintsViolationException exception) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        }

    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<Response> getModelsByCode(String code) {
        try {
            List<ModelEntity> modelEntityList = modelService.findModelByCode(code);
            return createSuccessfulResponse(modelEntityList);
        } catch (EntityNotFoundException exception) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    private ResponseEntity<Response> createSuccessfulResponse(ModelEntity modelEntity) {
        Response response = Response.builder().model(ModelMapper.createModelDTO(modelEntity)).build();
        ResponseEntity<Response> responseEntity = ResponseEntity.ok().body(response);
        return responseEntity;

    }

    private ResponseEntity<Response> createSuccessfulResponse(URI uri, ModelEntity modelEntity) {
        Response response = Response.builder().model(ModelMapper.createModelDTO(modelEntity)).build();
        return ResponseEntity.created(uri).body(response);

    }


    private ResponseEntity<Response> createSuccessfulResponse(List<ModelEntity> modelEntityList) {
        Response response = Response.builder().model(ModelMapper.creatModelDTOListfromModelEntityList(modelEntityList)).build();
        ResponseEntity<Response> responseEntity = ResponseEntity.ok().body(response);
        return responseEntity;

    }

    private ResponseEntity<Response> createResponseWithMessage(HttpStatus status, String message) {

        Response response = Response.builder().errorMessage(message).build();
        ResponseEntity<Response> responseEntity = ResponseEntity.status(status).body(response);

        return responseEntity;
    }


}
