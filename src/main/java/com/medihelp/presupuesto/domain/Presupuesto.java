package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medihelp.presupuesto.domain.enumeration.Estado;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Presupuesto.
 */
@Entity
@Table(name = "presupuesto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presupuesto_sequence")
    @SequenceGenerator(name = "presupuesto_sequence", sequenceName = "presupuesto_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "consecutivo")
    private Long consecutivo;

    @Column(name = "descripcion_actividad", length = 1000)
    private String descripcionActividad;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "nombre_responsable")
    private String nombreResponsable;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "correo_responsable")
    private String correoResponsable;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    @Column(name = "anio")
    private String anio;

    @Column(name = "finalizada")
    private Boolean finalizada;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoRecurso", "presupuesto" }, allowSetters = true)
    private Set<Recurso> recursos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subPlans", "presupuestos" }, allowSetters = true)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "presupuestos" }, allowSetters = true)
    private UnidadFuncional unidadFuncional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "presupuestos" }, allowSetters = true)
    private Rubro rubro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "presupuestos" }, allowSetters = true)
    private CentroCosto centroCosto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plan" }, allowSetters = true)
    private SubPlan subPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Presupuesto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsecutivo() {
        return this.consecutivo;
    }

    public Presupuesto consecutivo(Long consecutivo) {
        this.setConsecutivo(consecutivo);
        return this;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getDescripcionActividad() {
        return this.descripcionActividad;
    }

    public Presupuesto descripcionActividad(String descripcionActividad) {
        this.setDescripcionActividad(descripcionActividad);
        return this;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Presupuesto fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return this.fechaFinal;
    }

    public Presupuesto fechaFinal(LocalDate fechaFinal) {
        this.setFechaFinal(fechaFinal);
        return this;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public LocalDate getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Presupuesto fechaRegistro(LocalDate fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreResponsable() {
        return this.nombreResponsable;
    }

    public Presupuesto nombreResponsable(String nombreResponsable) {
        this.setNombreResponsable(nombreResponsable);
        return this;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Presupuesto estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCorreoResponsable() {
        return this.correoResponsable;
    }

    public Presupuesto correoResponsable(String correoResponsable) {
        this.setCorreoResponsable(correoResponsable);
        return this;
    }

    public void setCorreoResponsable(String correoResponsable) {
        this.correoResponsable = correoResponsable;
    }

    public Set<Recurso> getRecursos() {
        return this.recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        if (this.recursos != null) {
            this.recursos.forEach(i -> i.setPresupuesto(null));
        }
        if (recursos != null) {
            recursos.forEach(i -> i.setPresupuesto(this));
        }
        this.recursos = recursos;
    }

    public Presupuesto recursos(Set<Recurso> recursos) {
        this.setRecursos(recursos);
        return this;
    }

    public Presupuesto addRecurso(Recurso recurso) {
        this.recursos.add(recurso);
        recurso.setPresupuesto(this);
        return this;
    }

    public Presupuesto removeRecurso(Recurso recurso) {
        this.recursos.remove(recurso);
        recurso.setPresupuesto(null);
        return this;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Presupuesto plan(Plan plan) {
        this.setPlan(plan);
        return this;
    }

    public UnidadFuncional getUnidadFuncional() {
        return this.unidadFuncional;
    }

    public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public Presupuesto unidadFuncional(UnidadFuncional unidadFuncional) {
        this.setUnidadFuncional(unidadFuncional);
        return this;
    }

    public Rubro getRubro() {
        return this.rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Presupuesto rubro(Rubro rubro) {
        this.setRubro(rubro);
        return this;
    }

    public CentroCosto getCentroCosto() {
        return this.centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Presupuesto centroCosto(CentroCosto centroCosto) {
        this.setCentroCosto(centroCosto);
        return this;
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

    public SubPlan getSubPlan() {
        return subPlan;
    }

    public void setSubPlan(SubPlan subPlan) {
        this.subPlan = subPlan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Presupuesto)) {
            return false;
        }
        return getId() != null && getId().equals(((Presupuesto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Presupuesto{" +
            "id=" + getId() +
            ", consecutivo=" + getConsecutivo() +
            ", descripcionActividad='" + getDescripcionActividad() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", nombreResponsable='" + getNombreResponsable() + "'" +
            ", estado='" + getEstado() + "'" +
            ", correoResponsable='" + getCorreoResponsable() + "'" +
            "}";
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }
}
