package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.SubPlan;
import com.medihelp.presupuesto.repository.SubPlanRepository;
import com.medihelp.presupuesto.service.SubPlanService;
import com.medihelp.presupuesto.service.dto.SubPlanDTO;
import com.medihelp.presupuesto.service.mapper.SubPlanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.SubPlan}.
 */
@Service
@Transactional
public class SubPlanServiceImpl implements SubPlanService {

    private static final Logger LOG = LoggerFactory.getLogger(SubPlanServiceImpl.class);

    private final SubPlanRepository subPlanRepository;

    private final SubPlanMapper subPlanMapper;

    public SubPlanServiceImpl(SubPlanRepository subPlanRepository, SubPlanMapper subPlanMapper) {
        this.subPlanRepository = subPlanRepository;
        this.subPlanMapper = subPlanMapper;
    }

    @Override
    public SubPlanDTO save(SubPlanDTO subPlanDTO) {
        LOG.debug("Request to save SubPlan : {}", subPlanDTO);
        SubPlan subPlan = subPlanMapper.toEntity(subPlanDTO);
        subPlan = subPlanRepository.save(subPlan);
        return subPlanMapper.toDto(subPlan);
    }

    @Override
    public SubPlanDTO update(SubPlanDTO subPlanDTO) {
        LOG.debug("Request to update SubPlan : {}", subPlanDTO);
        SubPlan subPlan = subPlanMapper.toEntity(subPlanDTO);
        subPlan = subPlanRepository.save(subPlan);
        return subPlanMapper.toDto(subPlan);
    }

    @Override
    public Optional<SubPlanDTO> partialUpdate(SubPlanDTO subPlanDTO) {
        LOG.debug("Request to partially update SubPlan : {}", subPlanDTO);

        return subPlanRepository
            .findById(subPlanDTO.getId())
            .map(existingSubPlan -> {
                subPlanMapper.partialUpdate(existingSubPlan, subPlanDTO);

                return existingSubPlan;
            })
            .map(subPlanRepository::save)
            .map(subPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubPlanDTO> findAll() {
        LOG.debug("Request to get all SubPlans");
        return subPlanRepository.findAll().stream().map(subPlanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubPlanDTO> findByPlanId(Long id) {
        LOG.debug("Request to get SubPlans by plan id : {}", id);
        return subPlanRepository.findByPlanId(id).stream().map(subPlanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubPlanDTO> findOne(Long id) {
        LOG.debug("Request to get SubPlan : {}", id);
        return subPlanRepository.findById(id).map(subPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SubPlan : {}", id);
        subPlanRepository.deleteById(id);
    }
}
