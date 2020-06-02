package com.dasburo.sample.jupiter.demo.integration;

import com.dasburo.sample.jupiter.demo.integration.service.PersistenceConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ActiveProfiles("integration")
@ContextConfiguration(classes = {PersistenceConfigurationTest.class})
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
