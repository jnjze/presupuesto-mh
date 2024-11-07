package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.Rubro;
import com.medihelp.presupuesto.service.dto.RubroDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rubro} and its DTO {@link RubroDTO}.
 */
@Mapper(componentModel = "spring")
public interface RubroMapper extends EntityMapper<RubroDTO, Rubro> {}
