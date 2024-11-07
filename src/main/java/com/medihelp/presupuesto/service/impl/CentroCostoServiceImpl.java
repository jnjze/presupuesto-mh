package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.CentroCosto;
import com.medihelp.presupuesto.repository.CentroCostoRepository;
import com.medihelp.presupuesto.service.CentroCostoService;
import com.medihelp.presupuesto.service.dto.CentroCostoDTO;
import com.medihelp.presupuesto.service.mapper.CentroCostoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.CentroCosto}.
 */
@Service
@Transactional
public class CentroCostoServiceImpl implements CentroCostoService {

    private static final Logger LOG = LoggerFactory.getLogger(CentroCostoServiceImpl.class);

    private final CentroCostoRepository centroCostoRepository;

    private final CentroCostoMapper centroCostoMapper;

    public CentroCostoServiceImpl(CentroCostoRepository centroCostoRepository, CentroCostoMapper centroCostoMapper) {
        this.centroCostoRepository = centroCostoRepository;
        this.centroCostoMapper = centroCostoMapper;
    }

    @Override
    public CentroCostoDTO save(CentroCostoDTO centroCostoDTO) {
        LOG.debug("Request to save CentroCosto : {}", centroCostoDTO);
        CentroCosto centroCosto = centroCostoMapper.toEntity(centroCostoDTO);
        centroCosto = centroCostoRepository.save(centroCosto);
        return centroCostoMapper.toDto(centroCosto);
    }

    @Override
    public CentroCostoDTO update(CentroCostoDTO centroCostoDTO) {
        LOG.debug("Request to update CentroCosto : {}", centroCostoDTO);
        CentroCosto centroCosto = centroCostoMapper.toEntity(centroCostoDTO);
        centroCosto = centroCostoRepository.save(centroCosto);
        return centroCostoMapper.toDto(centroCosto);
    }

    @Override
    public Optional<CentroCostoDTO> partialUpdate(CentroCostoDTO centroCostoDTO) {
        LOG.debug("Request to partially update CentroCosto : {}", centroCostoDTO);

        return centroCostoRepository
            .findById(centroCostoDTO.getId())
            .map(existingCentroCosto -> {
                centroCostoMapper.partialUpdate(existingCentroCosto, centroCostoDTO);

                return existingCentroCosto;
            })
            .map(centroCostoRepository::save)
            .map(centroCostoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroCostoDTO> findAll() {
        LOG.debug("Request to get all CentroCostos");
        return centroCostoRepository.findAll().stream().map(centroCostoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentroCostoDTO> findOne(Long id) {
        LOG.debug("Request to get CentroCosto : {}", id);
        return centroCostoRepository.findById(id).map(centroCostoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CentroCosto : {}", id);
        centroCostoRepository.deleteById(id);
    }
}
