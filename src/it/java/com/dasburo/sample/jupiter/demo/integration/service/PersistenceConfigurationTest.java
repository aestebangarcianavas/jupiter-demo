package com.dasburo.sample.jupiter.demo.integration.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.dasburo.sample.jupiter.demo"})
@EnableJpaRepositories("com.dasburo.sample.jupiter.demo.persistence")
@EntityScan("com.dasburo.sample.jupiter.demo.persistence")
@Transactional
public class PersistenceConfigurationTest {
}
