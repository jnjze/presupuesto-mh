package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.Rubro;
import com.medihelp.presupuesto.repository.RubroRepository;
import com.medihelp.presupuesto.service.RubroService;
import com.medihelp.presupuesto.service.dto.RubroDTO;
import com.medihelp.presupuesto.service.mapper.RubroMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.Rubro}.
 */
@Service
@Transactional
public class RubroServiceImpl implements RubroService {

    private static final Logger LOG = LoggerFactory.getLogger(RubroServiceImpl.class);

    private final RubroRepository rubroRepository;

    private final RubroMapper rubroMapper;

    public RubroServiceImpl(RubroRepository rubroRepository, RubroMapper rubroMapper) {
        this.rubroRepository = rubroRepository;
        this.rubroMapper = rubroMapper;
    }

    @Override
    public RubroDTO save(RubroDTO rubroDTO) {
        LOG.debug("Request to save Rubro : {}", rubroDTO);
        Rubro rubro = rubroMapper.toEntity(rubroDTO);
        rubro = rubroRepository.save(rubro);
        return rubroMapper.toDto(rubro);
    }

    @Override
    public RubroDTO update(RubroDTO rubroDTO) {
        LOG.debug("Request to update Rubro : {}", rubroDTO);
        Rubro rubro = rubroMapper.toEntity(rubroDTO);
        rubro = rubroRepository.save(rubro);
        return rubroMapper.toDto(rubro);
    }

    @Override
    public Optional<RubroDTO> partialUpdate(RubroDTO rubroDTO) {
        LOG.debug("Request to partially update Rubro : {}", rubroDTO);

        return rubroRepository
            .findById(rubroDTO.getId())
            .map(existingRubro -> {
                rubroMapper.partialUpdate(existingRubro, rubroDTO);

                return existingRubro;
            })
            .map(rubroRepository::save)
            .map(rubroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RubroDTO> findAll() {
        LOG.debug("Request to get all Rubros");
        return rubroRepository.findAll().stream().map(rubroMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RubroDTO> findOne(Long id) {
        LOG.debug("Request to get Rubro : {}", id);
        return rubroRepository.findById(id).map(rubroMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Rubro : {}", id);
        rubroRepository.deleteById(id);
    }
}
