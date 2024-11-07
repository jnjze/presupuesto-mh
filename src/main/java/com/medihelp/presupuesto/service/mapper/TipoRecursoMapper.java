package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.TipoRecurso;
import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoRecurso} and its DTO {@link TipoRecursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoRecursoMapper extends EntityMapper<TipoRecursoDTO, TipoRecurso> {}
