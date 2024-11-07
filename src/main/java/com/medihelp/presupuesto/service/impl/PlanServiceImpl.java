package com.medihelp.presupuesto.service.impl;

import com.medihelp.presupuesto.domain.Plan;
import com.medihelp.presupuesto.repository.PlanRepository;
import com.medihelp.presupuesto.service.PlanService;
import com.medihelp.presupuesto.service.dto.PlanDTO;
import com.medihelp.presupuesto.service.mapper.PlanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.medihelp.presupuesto.domain.Plan}.
 */
@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private static final Logger LOG = LoggerFactory.getLogger(PlanServiceImpl.class);

    private final PlanRepository planRepository;

    private final PlanMapper planMapper;

    public PlanServiceImpl(PlanRepository planRepository, PlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    @Override
    public PlanDTO save(PlanDTO planDTO) {
        LOG.debug("Request to save Plan : {}", planDTO);
        Plan plan = planMapper.toEntity(planDTO);
        plan = planRepository.save(plan);
        return planMapper.toDto(plan);
    }

    @Override
    public PlanDTO update(PlanDTO planDTO) {
        LOG.debug("Request to update Plan : {}", planDTO);
        Plan plan = planMapper.toEntity(planDTO);
        plan = planRepository.save(plan);
        return planMapper.toDto(plan);
    }

    @Override
    public Optional<PlanDTO> partialUpdate(PlanDTO planDTO) {
        LOG.debug("Request to partially update Plan : {}", planDTO);

        return planRepository
            .findById(planDTO.getId())
            .map(existingPlan -> {
                planMapper.partialUpdate(existingPlan, planDTO);

                return existingPlan;
            })
            .map(planRepository::save)
            .map(planMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanDTO> findAll() {
        LOG.debug("Request to get all Plans");
        return planRepository.findAll().stream().map(planMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanDTO> findOne(Long id) {
        LOG.debug("Request to get Plan : {}", id);
        return planRepository.findById(id).map(planMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Plan : {}", id);
        planRepository.deleteById(id);
    }
}
