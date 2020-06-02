package com.dasburo.sample.jupiter.demo.service;

import com.dasburo.sample.jupiter.demo.exception.ConstraintsViolationException;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;
import com.dasburo.sample.jupiter.demo.persistence.ModelRepository;
import org.aspectj.asm.IModelFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelServiceTest {

    private static long VERSION = 0L;
    private static int YEAR_2020 = 2020;
    private static long GERMAN_MARKET_ID = 50000L;


    private ModelRepository modelRepositoryMock;
    private ModelService classUnderTest;

    static Stream<Arguments> generateDataForGetProgram() {
        return Stream.of(Arguments.of(1L, "EXY4", GERMAN_MARKET_ID, "Extension number four",  YEAR_2020 , VERSION));
    }

    @BeforeEach
    public void initMock(){
        modelRepositoryMock = mock(ModelRepository.class);
        classUnderTest = new ModelServiceImpl(modelRepositoryMock);

    }

    @DisplayName("Create model Test")
    @ParameterizedTest
    @MethodSource("generateDataForGetProgram")
    public void createModel_test(long id, String code, long marketId, String modelExtension, int year, long version){
        ModelEntity entity = ModelEntity.builder().id(id).code(code).marketId(marketId).modelExtension(modelExtension).modelYear(year).modelVersion(version).build();
        //When
        when(modelRepositoryMock.save(entity)).thenThrow(new DataIntegrityViolationException("Error saving the entity"));

        final ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> classUnderTest.createModel(entity));
        assertThat(exception).isNotNull();

    }


}
