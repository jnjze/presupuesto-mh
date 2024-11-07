package com.medihelp.presupuesto.service.mapper;

import com.medihelp.presupuesto.domain.Cargo;
import com.medihelp.presupuesto.domain.Presupuesto;
import com.medihelp.presupuesto.domain.Recurso;
import com.medihelp.presupuesto.domain.TipoRecurso;
import com.medihelp.presupuesto.service.dto.CargoDTO;
import com.medihelp.presupuesto.service.dto.PresupuestoDTO;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
import com.medihelp.presupuesto.service.dto.TipoRecursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recurso} and its DTO {@link RecursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecursoMapper extends EntityMapper<RecursoDTO, Recurso> {
    @Mapping(target = "tipoRecurso", source = "tipoRecurso", qualifiedByName = "tipoRecursoId")
    @Mapping(target = "presupuesto", source = "presupuesto", qualifiedByName = "presupuestoId")
    @Mapping(target = "cargo", source = "cargo", qualifiedByName = "cargoId")
    RecursoDTO toDto(Recurso s);

    @Named("tipoRecursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    TipoRecursoDTO toDtoTipoRecursoId(TipoRecurso tipoRecurso);

    @Named("presupuestoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "consecutivo", source = "consecutivo")
    PresupuestoDTO toDtoPresupuestoId(Presupuesto presupuesto);

    @Named("cargoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    CargoDTO toDtoCargoId(Cargo cargo);
}
