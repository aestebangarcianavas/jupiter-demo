package com.dasburo.sample.jupiter.demo.service;

import com.dasburo.sample.jupiter.demo.exception.ConstraintsViolationException;
import com.dasburo.sample.jupiter.demo.exception.EntityNotFoundException;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service which provides operations with the Model repository.
 *
 * @author Ana Esteban Garcia-Navas(<a href="mailto:ags@dasburo.com">ramoni</a>)
 */
public interface ModelService {

    ModelEntity createModel(@NotNull final ModelEntity modelEntity) throws ConstraintsViolationException;

    ModelEntity findModelById(@NotNull final long modelId) throws EntityNotFoundException;

    List<ModelEntity> findModelByCode(@NotNull final String code) throws EntityNotFoundException;

    List<ModelEntity> findAllByMarketId(@NotNull long marketId) throws EntityNotFoundException;

    List<ModelEntity> findAllModels() throws EntityNotFoundException;
}
