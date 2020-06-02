package com.dasburo.sample.jupiter.demo.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for the persistence layer.
 *
 * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories("com.dasburo.sample.jupiter.demo.persistence")
@EntityScan("com.dasburo.sample.jupiter.demo.persistence")
public class PersistenceConfiguration {
}
