package com.medihelp.presupuesto.service.dto;

import com.medihelp.presupuesto.domain.enumeration.Estado;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.medihelp.presupuesto.domain.Presupuesto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PresupuestoDTO implements Serializable {

    private Long id;

    private Long consecutivo;

    private String descripcionActividad;

    private LocalDate fechaInicio;

    private LocalDate fechaFinal;

    private LocalDate fechaRegistro;

    private String nombreResponsable;

    private Estado estado;

    private String correoResponsable;

    private String observaciones;

    private PlanDTO plan;

    private UnidadFuncionalDTO unidadFuncional;

    private RubroDTO rubro;

    private CentroCostoDTO centroCosto;

    private SubPlanDTO subPlan;

    private String anio;

    private List<RecursoDTO> recursos;

    private Boolean finalizada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCorreoResponsable() {
        return correoResponsable;
    }

    public void setCorreoResponsable(String correoResponsable) {
        this.correoResponsable = correoResponsable;
    }

    public PlanDTO getPlan() {
        return plan;
    }

    public void setPlan(PlanDTO plan) {
        this.plan = plan;
    }

    public UnidadFuncionalDTO getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(UnidadFuncionalDTO unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public RubroDTO getRubro() {
        return rubro;
    }

    public void setRubro(RubroDTO rubro) {
        this.rubro = rubro;
    }

    public CentroCostoDTO getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCostoDTO centroCosto) {
        this.centroCosto = centroCosto;
    }

    public SubPlanDTO getSubPlan() {
        return subPlan;
    }

    public void setSubPlan(SubPlanDTO subPlan) {
        this.subPlan = subPlan;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresupuestoDTO)) {
            return false;
        }

        PresupuestoDTO presupuestoDTO = (PresupuestoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, presupuestoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PresupuestoDTO{" +
            "id=" + getId() +
            ", consecutivo=" + getConsecutivo() +
            ", descripcionActividad='" + getDescripcionActividad() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", nombreResponsable='" + getNombreResponsable() + "'" +
            ", estado='" + getEstado() + "'" +
            ", correoResponsable='" + getCorreoResponsable() + "'" +
            ", plan=" + getPlan() +
            ", unidadFuncional=" + getUnidadFuncional() +
            ", rubro=" + getRubro() +
            ", centroCosto=" + getCentroCosto() +
            "}";
    }

    public List<RecursoDTO> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoDTO> recursos) {
        this.recursos = recursos;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }
}
