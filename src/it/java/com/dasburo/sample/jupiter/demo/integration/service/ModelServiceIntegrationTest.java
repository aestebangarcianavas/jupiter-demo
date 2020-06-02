package com.dasburo.sample.jupiter.demo.integration.service;

import com.dasburo.sample.jupiter.demo.exception.ConstraintsViolationException;
import com.dasburo.sample.jupiter.demo.exception.EntityNotFoundException;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;
import com.dasburo.sample.jupiter.demo.service.ModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {PersistenceConfigurationTest.class})
@ActiveProfiles("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelServiceIntegrationTest {

    @Autowired
    private ModelService classUnderTest;

    static Stream<Long> longProvider() {
        return Stream.of(3082L, 3080L, 7000L);
    }

    static Stream<Long> marketIdsNoAvailable() {
        return Stream.of(3000000L, 30000000000222L, 70000022222000L);
    }


    @DisplayName("Parameterized test for filling up my repository")
    @ParameterizedTest
    @CsvSource( {"4MNSU2,CFP,0,2020,3082", "5NMKU2,MNO,0,2019,3080", "EXEC,MDC,0,2020,7000"})
    @Rollback(false)
    @Order(1)
    public void fillupRepositoryTest(String code, String modelExtension, long modelVersion, int modelYear, long marketId) throws ConstraintsViolationException {
        ModelEntity inputModelEntity = ModelEntity.builder().code(code).marketId(marketId).modelExtension(modelExtension).modelVersion(modelVersion).modelYear(modelYear).build();

        ModelEntity storedModelEntity = classUnderTest.createModel(inputModelEntity);
        assertEquals(code, storedModelEntity.getCode());
        assertEquals(marketId, storedModelEntity.getMarketId());
        assertEquals(modelExtension, storedModelEntity.getModelExtension());
        assertEquals(modelVersion, storedModelEntity.getModelVersion());
        assertEquals(modelYear, storedModelEntity.getModelYear());
    }

    @DisplayName("Find by Code Test")
    @ParameterizedTest
    @ValueSource(strings = {"4MNSU2", "5NMKU2", "EXEC"})
    @Order(2)
    public void findByCodeTest(String code) throws EntityNotFoundException {
        final List<ModelEntity> foundModelList = this.classUnderTest.findModelByCode(code);
        assertNotEquals(0, foundModelList.size());
        assertEquals(code, foundModelList.get(0).getCode());
    }

    @DisplayName("Find by Market Id")
    @ParameterizedTest
    @MethodSource("longProvider")
    @Order(3)
    public void findByMarketId(long marketId) throws EntityNotFoundException {
        final List<ModelEntity> foundModelEntityList = this.classUnderTest.findAllByMarketId(marketId);
        assertNotNull(foundModelEntityList);
        assertEquals(1, foundModelEntityList.size());
        assertEquals(marketId, foundModelEntityList.get(0).getMarketId());

    }

    @DisplayName("Find by Market Id having exceptions")
    @ParameterizedTest
    @MethodSource("marketIdsNoAvailable")
    @Order(4)
    public void findByMarketId_thenThrowException(long marketId){
        try{
            final List<ModelEntity> foundModelEntityList = this.classUnderTest.findAllByMarketId(marketId);
        }catch(EntityNotFoundException exception){
            assertNotNull(exception);
            assertThat(exception.getMessage()).contains(String.valueOf(marketId));
        }

    }


}
