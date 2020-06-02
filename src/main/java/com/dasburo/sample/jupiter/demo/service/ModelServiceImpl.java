package com.dasburo.sample.jupiter.demo.service;

import com.dasburo.sample.jupiter.demo.Assertions;
import com.dasburo.sample.jupiter.demo.exception.ConstraintsViolationException;
import com.dasburo.sample.jupiter.demo.exception.EntityNotFoundException;
import com.dasburo.sample.jupiter.demo.persistence.ModelEntity;
import com.dasburo.sample.jupiter.demo.persistence.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service which provides operations with the Model repository.
 *
 * @author Ana Esteban Garcia-Navas(<a href="mailto:ags@dasburo.com">anaesteban</a>)
 */

@Service
class ModelServiceImpl implements ModelService {

    private static final Logger LOG = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;

    @Autowired
    public ModelServiceImpl(final ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public ModelEntity createModel(final ModelEntity modelEntity) throws ConstraintsViolationException {
        Assertions.isNotNull(modelEntity, "The model can not be null!");
        try {
            return this.modelRepository.save(modelEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new ConstraintsViolationException(exception.getMessage());
        }
    }

    @Override
    public ModelEntity findModelById(final long modelId) throws EntityNotFoundException {
        Assertions.isNotNull(modelId, "The model identifier can not be null!");
        ModelEntity modelEntity = modelRepository.findById(modelId).orElseThrow(() -> new
                EntityNotFoundException("Could not find the model entity with the id: " + modelId));
        return modelEntity;
    }

    @Override
    public List<ModelEntity> findModelByCode(final String code) throws EntityNotFoundException {
        Assertions.isNotBlank(code, "Code must not be empty!!");
        final List<ModelEntity> modelEntityFoundList = modelRepository.findByCode(code);
        if (modelEntityFoundList.isEmpty()) {
            throw new EntityNotFoundException("No model found with the code " + code);
        }
        return modelEntityFoundList;
    }

    @Override
    public List<ModelEntity> findAllByMarketId(long marketId) throws EntityNotFoundException {
        Assertions.isNotNull(marketId, "MarketId can not be null");
        final List<ModelEntity> modelEntityList = modelRepository.findByMarketId(marketId);
        if (modelEntityList.isEmpty()) {
            throw new EntityNotFoundException("No model found for the marketId = " + marketId);
        }
        return modelEntityList;
    }

    @Override
    public List<ModelEntity> findAllModels() {
        final List<ModelEntity> modelEntityList = modelRepository.findAll();
        return modelEntityList;
    }
}
