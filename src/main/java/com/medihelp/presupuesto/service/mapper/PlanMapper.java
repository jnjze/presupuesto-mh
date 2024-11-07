package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.Plan;
import com.medihelp.presupuesto.service.dto.PlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plan} and its DTO {@link PlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanMapper extends EntityMapper<PlanDTO, Plan> {}
