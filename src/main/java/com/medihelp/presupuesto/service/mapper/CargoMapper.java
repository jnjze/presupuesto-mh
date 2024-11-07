package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.Cargo;
import com.medihelp.presupuesto.service.dto.CargoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Cargo} and its DTO {@link CargoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CargoMapper extends EntityMapper<CargoDTO, Cargo> {}
