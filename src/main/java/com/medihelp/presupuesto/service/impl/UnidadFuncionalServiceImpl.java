package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.UnidadFuncional;
import com.medihelp.presupuesto.repository.UnidadFuncionalRepository;
import com.medihelp.presupuesto.service.UnidadFuncionalService;
import com.medihelp.presupuesto.service.dto.UnidadFuncionalDTO;
import com.medihelp.presupuesto.service.mapper.UnidadFuncionalMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.UnidadFuncional}.
 */
@Service
@Transactional
public class UnidadFuncionalServiceImpl implements UnidadFuncionalService {

    private static final Logger LOG = LoggerFactory.getLogger(UnidadFuncionalServiceImpl.class);

    private final UnidadFuncionalRepository unidadFuncionalRepository;

    private final UnidadFuncionalMapper unidadFuncionalMapper;

    public UnidadFuncionalServiceImpl(UnidadFuncionalRepository unidadFuncionalRepository, UnidadFuncionalMapper unidadFuncionalMapper) {
        this.unidadFuncionalRepository = unidadFuncionalRepository;
        this.unidadFuncionalMapper = unidadFuncionalMapper;
    }

    @Override
    public UnidadFuncionalDTO save(UnidadFuncionalDTO unidadFuncionalDTO) {
        LOG.debug("Request to save UnidadFuncional : {}", unidadFuncionalDTO);
        UnidadFuncional unidadFuncional = unidadFuncionalMapper.toEntity(unidadFuncionalDTO);
        unidadFuncional = unidadFuncionalRepository.save(unidadFuncional);
        return unidadFuncionalMapper.toDto(unidadFuncional);
    }

    @Override
    public UnidadFuncionalDTO update(UnidadFuncionalDTO unidadFuncionalDTO) {
        LOG.debug("Request to update UnidadFuncional : {}", unidadFuncionalDTO);
        UnidadFuncional unidadFuncional = unidadFuncionalMapper.toEntity(unidadFuncionalDTO);
        unidadFuncional = unidadFuncionalRepository.save(unidadFuncional);
        return unidadFuncionalMapper.toDto(unidadFuncional);
    }

    @Override
    public Optional<UnidadFuncionalDTO> partialUpdate(UnidadFuncionalDTO unidadFuncionalDTO) {
        LOG.debug("Request to partially update UnidadFuncional : {}", unidadFuncionalDTO);

        return unidadFuncionalRepository
            .findById(unidadFuncionalDTO.getId())
            .map(existingUnidadFuncional -> {
                unidadFuncionalMapper.partialUpdate(existingUnidadFuncional, unidadFuncionalDTO);

                return existingUnidadFuncional;
            })
            .map(unidadFuncionalRepository::save)
            .map(unidadFuncionalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadFuncionalDTO> findAll() {
        LOG.debug("Request to get all UnidadFuncionals");
        return unidadFuncionalRepository
            .findAll()
            .stream()
            .map(unidadFuncionalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UnidadFuncionalDTO> findOne(Long id) {
        LOG.debug("Request to get UnidadFuncional : {}", id);
        return unidadFuncionalRepository.findById(id).map(unidadFuncionalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete UnidadFuncional : {}", id);
        unidadFuncionalRepository.deleteById(id);
    }
}
