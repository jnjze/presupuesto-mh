package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.Plan;
import com.medihelp.presupuesto.domain.SubPlan;
import com.medihelp.presupuesto.service.dto.PlanDTO;
import com.medihelp.presupuesto.service.dto.SubPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubPlan} and its DTO {@link SubPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubPlanMapper extends EntityMapper<SubPlanDTO, SubPlan> {
    @Mapping(target = "plan", source = "plan", qualifiedByName = "planId")
    SubPlanDTO toDto(SubPlan s);

    @Named("planId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlanDTO toDtoPlanId(Plan plan);
}
