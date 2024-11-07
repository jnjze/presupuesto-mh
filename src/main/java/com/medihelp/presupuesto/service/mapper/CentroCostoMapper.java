package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.CentroCosto;
import com.medihelp.presupuesto.service.dto.CentroCostoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroCosto} and its DTO {@link CentroCostoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroCostoMapper extends EntityMapper<CentroCostoDTO, CentroCosto> {}
