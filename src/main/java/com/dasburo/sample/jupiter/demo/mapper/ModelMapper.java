package com.dasburo.sample.jupiter.demo.mapper;

import com.dasburo.sample.jupiter.demo.data.ModelDTO;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to convert the entities into data transfer object.
 *
 * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 * @since 01.02.2020
 */
public class ModelMapper {

    public static ModelDTO createModelDTO(ModelEntity modelEntity) {
        ModelDTO modelDTO = ModelDTO.builder().id(modelEntity.getId()).modelExtension(modelEntity.getModelExtension()).modelVersion(modelEntity.getModelVersion()).code(modelEntity.getCode()).marketId(modelEntity.getMarketId()).modelYear(modelEntity.getModelYear()).build();
        return modelDTO;
    }

    public static List<ModelDTO> creatModelDTOListfromModelEntityList(List<ModelEntity> modelEntityList) {
        return modelEntityList.stream().map(ModelMapper::createModelDTO).collect(Collectors.toList());
    }

    public static ModelEntity createDOfromDTO(ModelDTO inputModel) {
        ModelEntity modelEntity = ModelEntity.builder().code(inputModel.getCode()).marketId(inputModel.getMarketId()).modelExtension(inputModel.getModelExtension()).modelVersion(inputModel.getModelVersion()).modelYear(inputModel.getModelYear()).build();
        return modelEntity;
    }
}
