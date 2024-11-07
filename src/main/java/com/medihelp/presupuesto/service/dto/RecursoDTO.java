package com.medihelp.presupuesto.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.medihelp.presupuesto.domain.Recurso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecursoDTO implements Serializable {

    private Long id;

    private String mes;

    private BigDecimal valor;

    private String observacion;

    private TipoRecursoDTO tipoRecurso;

    private PresupuestoDTO presupuesto;

    private CargoDTO cargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoRecursoDTO getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecursoDTO tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public PresupuestoDTO getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoDTO presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecursoDTO)) {
            return false;
        }

        RecursoDTO recursoDTO = (RecursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoDTO{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", valor=" + getValor() +
            ", observacion='" + getObservacion() + "'" +
            ", tipoRecurso=" + getTipoRecurso() +
            ", presupuesto=" + getPresupuesto() +
            "}";
    }

    public CargoDTO getCargo() {
        return cargo;
    }

    public void setCargo(CargoDTO cargo) {
        this.cargo = cargo;
    }
}
