package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.TipoRecurso;
import com.medihelp.presupuesto.repository.TipoRecursoRepository;
import com.medihelp.presupuesto.service.TipoRecursoService;
import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
import com.medihelp.presupuesto.service.mapper.TipoRecursoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.TipoRecurso}.
 */
@Service
@Transactional
public class TipoRecursoServiceImpl implements TipoRecursoService {

    private static final Logger LOG = LoggerFactory.getLogger(TipoRecursoServiceImpl.class);

    private final TipoRecursoRepository tipoRecursoRepository;

    private final TipoRecursoMapper tipoRecursoMapper;

    public TipoRecursoServiceImpl(TipoRecursoRepository tipoRecursoRepository, TipoRecursoMapper tipoRecursoMapper) {
        this.tipoRecursoRepository = tipoRecursoRepository;
        this.tipoRecursoMapper = tipoRecursoMapper;
    }

    @Override
    public TipoRecursoDTO save(TipoRecursoDTO tipoRecursoDTO) {
        LOG.debug("Request to save TipoRecurso : {}", tipoRecursoDTO);
        TipoRecurso tipoRecurso = tipoRecursoMapper.toEntity(tipoRecursoDTO);
        tipoRecurso = tipoRecursoRepository.save(tipoRecurso);
        return tipoRecursoMapper.toDto(tipoRecurso);
    }

    @Override
    public TipoRecursoDTO update(TipoRecursoDTO tipoRecursoDTO) {
        LOG.debug("Request to update TipoRecurso : {}", tipoRecursoDTO);
        TipoRecurso tipoRecurso = tipoRecursoMapper.toEntity(tipoRecursoDTO);
        tipoRecurso = tipoRecursoRepository.save(tipoRecurso);
        return tipoRecursoMapper.toDto(tipoRecurso);
    }

    @Override
    public Optional<TipoRecursoDTO> partialUpdate(TipoRecursoDTO tipoRecursoDTO) {
        LOG.debug("Request to partially update TipoRecurso : {}", tipoRecursoDTO);

        return tipoRecursoRepository
            .findById(tipoRecursoDTO.getId())
            .map(existingTipoRecurso -> {
                tipoRecursoMapper.partialUpdate(existingTipoRecurso, tipoRecursoDTO);

                return existingTipoRecurso;
            })
            .map(tipoRecursoRepository::save)
            .map(tipoRecursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoRecursoDTO> findAll() {
        LOG.debug("Request to get all TipoRecursos");
        return tipoRecursoRepository.findAll().stream().map(tipoRecursoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoRecursoDTO> findOne(Long id) {
        LOG.debug("Request to get TipoRecurso : {}", id);
        return tipoRecursoRepository.findById(id).map(tipoRecursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TipoRecurso : {}", id);
        tipoRecursoRepository.deleteById(id);
    }
}
