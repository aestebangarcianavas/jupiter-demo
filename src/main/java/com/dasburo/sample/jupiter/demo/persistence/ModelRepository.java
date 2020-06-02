package com.dasburo.sample.jupiter.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access to the {@link ModelEntity}
 *
 * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 */
@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findByCode(String code);

    List<ModelEntity> findByMarketId(long marketId);
}
