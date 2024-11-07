package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.UnidadFuncional;
import com.medihelp.presupuesto.service.dto.UnidadFuncionalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadFuncional} and its DTO {@link UnidadFuncionalDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnidadFuncionalMapper extends EntityMapper<UnidadFuncionalDTO, UnidadFuncional> {}
