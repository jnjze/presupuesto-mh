package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.Presupuesto;
import com.medihelp.presupuesto.domain.Recurso;
import com.medihelp.presupuesto.domain.enumeration.Estado;
import com.medihelp.presupuesto.repository.PresupuestoRepository;
import com.medihelp.presupuesto.repository.RecursoRepository;
import com.medihelp.presupuesto.service.PresupuestoService;
import com.medihelp.presupuesto.service.dto.PresupuestoDTO;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
import com.medihelp.presupuesto.service.mapper.PresupuestoMapper;
import com.medihelp.presupuesto.service.mapper.RecursoMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.Presupuesto}.
 */
@Service
@Transactional
public class PresupuestoServiceImpl implements PresupuestoService {

    private static final Logger LOG = LoggerFactory.getLogger(PresupuestoServiceImpl.class);

    private final PresupuestoRepository presupuestoRepository;

    private final RecursoRepository recursoRepository;

    private final PresupuestoMapper presupuestoMapper;

    private final RecursoMapper recursoMapper;

    public PresupuestoServiceImpl(
        PresupuestoRepository presupuestoRepository,
        RecursoRepository recursoRepository,
        PresupuestoMapper presupuestoMapper,
        RecursoMapper recursoMapper
    ) {
        this.presupuestoRepository = presupuestoRepository;
        this.recursoRepository = recursoRepository;
        this.presupuestoMapper = presupuestoMapper;
        this.recursoMapper = recursoMapper;
    }

    @Override
    public PresupuestoDTO save(PresupuestoDTO presupuestoDTO) {
        LOG.debug("Request to save Presupuesto : {}", presupuestoDTO);
        Presupuesto presupuesto = presupuestoMapper.toEntity(presupuestoDTO);

        presupuesto.setEstado(Estado.SIN_ASIGNAR);
        presupuesto.setFechaRegistro(LocalDate.now(ZoneId.of("America/Bogota")));
        presupuesto = presupuestoRepository.save(presupuesto);

        return presupuestoMapper.toDto(presupuesto);
    }

    @Override
    public PresupuestoDTO update(PresupuestoDTO presupuestoDTO) {
        LOG.debug("Request to update Presupuesto : {}", presupuestoDTO);
        Presupuesto presupuesto = presupuestoMapper.toEntity(presupuestoDTO);

        presupuesto = presupuestoRepository.save(presupuesto);
        return presupuestoMapper.toDto(presupuesto);
    }

    @Override
    public Optional<PresupuestoDTO> partialUpdate(PresupuestoDTO presupuestoDTO) {
        LOG.debug("Request to partially update Presupuesto : {}", presupuestoDTO);

        return presupuestoRepository
            .findById(presupuestoDTO.getId())
            .map(existingPresupuesto -> {
                presupuestoMapper.partialUpdate(existingPresupuesto, presupuestoDTO);

                return existingPresupuesto;
            })
            .map(presupuestoRepository::save)
            .map(presupuestoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PresupuestoDTO> findAll() {
        LOG.debug("Request to get all Presupuestos");
        return presupuestoRepository
            .findAll(Sort.by(Sort.Direction.DESC, "id"))
            .stream()
            .map(presupuestoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PresupuestoDTO> findOne(Long id) {
        LOG.debug("Request to get Presupuesto : {}", id);
        return presupuestoRepository.findById(id).map(presupuestoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Presupuesto : {}", id);
        presupuestoRepository.deleteById(id);
    }
}
