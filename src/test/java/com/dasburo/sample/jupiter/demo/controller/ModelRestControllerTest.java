package com.dasburo.sample.jupiter.demo.controller;

import com.dasburo.sample.jupiter.demo.data.ModelDTO;
import com.dasburo.sample.jupiter.demo.mapper.ModelMapper;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;
import com.dasburo.sample.jupiter.demo.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ModelRestController.class)
//@Execution(ExecutionMode.CONCURRENT)
public class ModelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelService modelServiceMock;

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(modelServiceMock);
    }

    @DisplayName("Rest controller test to get all the models available within the repository")
    @ParameterizedTest
    @CsvSource( {"4MNSU2,CFP,0,2020,3082"})
    public void getAllModelsTest_thenStatus200(String code, String modelExtension, long modelVersion, int modelYear, long marketId) throws Exception {
        ModelEntity inputModelEntity = ModelEntity.builder().id(400000L).code(code).marketId(marketId).modelExtension(modelExtension).modelVersion(modelVersion).modelYear(modelYear).build();
        List<ModelEntity> modelDTOList = Arrays.asList(inputModelEntity);
        when(modelServiceMock.findAllModels()).thenReturn(modelDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/jupiter-demo/v1/models/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists());

    }

    @DisplayName("Rest Controller test to introduce new models")
    //@RepeatedTest(10)
    //@Disabled
    @Test
    public void postModelTest() throws Exception {
        ModelEntity inputModelEntity = ModelEntity.builder().code("code").marketId(50000l).modelExtension("modelExtension").modelVersion(1l).modelYear(2020).build();
        ModelDTO inputModelDTO = ModelMapper.createModelDTO(inputModelEntity);
        ModelEntity outputModelEntity = ModelEntity.builder().id(888888l).code("code").marketId(50000l).modelExtension("modelExtension").modelVersion(1l).modelYear(2020).build();

        when(modelServiceMock.createModel(inputModelEntity)).thenReturn(outputModelEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/jupiter-demo/v1/models/").content(inputModelDTO.toString()).
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists())

        ;
    }
    @DisplayName("Get all the existing model 10 times")
    @RepeatedTest(10)
    public void getAllModelsTest_Repeated() throws Exception {
        ModelEntity inputModelEntity = ModelEntity.builder().id(88751892l).code("code").marketId(50000l).modelExtension("modelExtension").modelVersion(1l).modelYear(2020).build();
        List<ModelEntity> modelDTOList = Arrays.asList(inputModelEntity);
        when(modelServiceMock.findAllModels()).thenReturn(modelDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/jupiter-demo/v1/models/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists());

    }
}
