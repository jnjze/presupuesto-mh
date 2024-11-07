package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.Recurso;
import com.medihelp.presupuesto.repository.RecursoRepository;
import com.medihelp.presupuesto.service.RecursoService;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
import com.medihelp.presupuesto.service.mapper.RecursoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.Recurso}.
 */
@Service
@Transactional
public class RecursoServiceImpl implements RecursoService {

    private static final Logger LOG = LoggerFactory.getLogger(RecursoServiceImpl.class);

    private final RecursoRepository recursoRepository;

    private final RecursoMapper recursoMapper;

    public RecursoServiceImpl(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    @Override
    public RecursoDTO save(RecursoDTO recursoDTO) {
        LOG.debug("Request to save Recurso : {}", recursoDTO);
        Recurso recurso = recursoMapper.toEntity(recursoDTO);
        recurso = recursoRepository.save(recurso);
        return recursoMapper.toDto(recurso);
    }

    @Override
    public RecursoDTO update(RecursoDTO recursoDTO) {
        LOG.debug("Request to update Recurso : {}", recursoDTO);
        Recurso recurso = recursoMapper.toEntity(recursoDTO);
        recurso = recursoRepository.save(recurso);
        return recursoMapper.toDto(recurso);
    }

    @Override
    public Optional<RecursoDTO> partialUpdate(RecursoDTO recursoDTO) {
        LOG.debug("Request to partially update Recurso : {}", recursoDTO);

        return recursoRepository
            .findById(recursoDTO.getId())
            .map(existingRecurso -> {
                recursoMapper.partialUpdate(existingRecurso, recursoDTO);

                return existingRecurso;
            })
            .map(recursoRepository::save)
            .map(recursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecursoDTO> findAll() {
        LOG.debug("Request to get all Recursos");
        return recursoRepository.findAll().stream().map(recursoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecursoDTO> findOne(Long id) {
        LOG.debug("Request to get Recurso : {}", id);
        return recursoRepository.findById(id).map(recursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Recurso : {}", id);
        recursoRepository.deleteById(id);
    }
}
