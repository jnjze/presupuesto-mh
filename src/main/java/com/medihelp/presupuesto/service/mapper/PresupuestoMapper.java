package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.*;
import com.medihelp.presupuesto.service.dto.*;
import java.util.List;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Presupuesto} and its DTO {@link PresupuestoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PresupuestoMapper extends EntityMapper<PresupuestoDTO, Presupuesto> {
    @Mapping(target = "plan", source = "plan", qualifiedByName = "planId")
    @Mapping(target = "unidadFuncional", source = "unidadFuncional", qualifiedByName = "unidadFuncionalId")
    @Mapping(target = "rubro", source = "rubro", qualifiedByName = "rubroId")
    @Mapping(target = "centroCosto", source = "centroCosto", qualifiedByName = "centroCostoId")
    @Mapping(target = "subPlan", source = "subPlan", qualifiedByName = "subPlanId")
    PresupuestoDTO toDto(Presupuesto s);

    @Named("planId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    PlanDTO toDtoPlanId(Plan plan);

    @Named("unidadFuncionalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    UnidadFuncionalDTO toDtoUnidadFuncionalId(UnidadFuncional unidadFuncional);

    @Named("rubroId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    RubroDTO toDtoRubroId(Rubro rubro);

    @Named("centroCostoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    CentroCostoDTO toDtoCentroCostoId(CentroCosto centroCosto);

    @Named("subPlanId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SubPlanDTO toDtoSubPlanId(SubPlan subPlan);

    @Mapping(target = "presupuesto", ignore = true)
    RecursoDTO recursoToRecursoDTO(Recurso recurso);
}
