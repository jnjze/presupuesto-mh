package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_sequence")
    @SequenceGenerator(name = "plan_sequence", sequenceName = "plan_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plan" }, allowSetters = true)
    private Set<SubPlan> subPlans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recursos", "plan", "unidadFuncional", "rubro", "centroCosto" }, allowSetters = true)
    private Set<Presupuesto> presupuestos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Plan codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Plan nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Plan descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<SubPlan> getSubPlans() {
        return this.subPlans;
    }

    public void setSubPlans(Set<SubPlan> subPlans) {
        if (this.subPlans != null) {
            this.subPlans.forEach(i -> i.setPlan(null));
        }
        if (subPlans != null) {
            subPlans.forEach(i -> i.setPlan(this));
        }
        this.subPlans = subPlans;
    }

    public Plan subPlans(Set<SubPlan> subPlans) {
        this.setSubPlans(subPlans);
        return this;
    }

    public Plan addSubPlan(SubPlan subPlan) {
        this.subPlans.add(subPlan);
        subPlan.setPlan(this);
        return this;
    }

    public Plan removeSubPlan(SubPlan subPlan) {
        this.subPlans.remove(subPlan);
        subPlan.setPlan(null);
        return this;
    }

    public Set<Presupuesto> getPresupuestos() {
        return this.presupuestos;
    }

    public void setPresupuestos(Set<Presupuesto> presupuestos) {
        if (this.presupuestos != null) {
            this.presupuestos.forEach(i -> i.setPlan(null));
        }
        if (presupuestos != null) {
            presupuestos.forEach(i -> i.setPlan(this));
        }
        this.presupuestos = presupuestos;
    }

    public Plan presupuestos(Set<Presupuesto> presupuestos) {
        this.setPresupuestos(presupuestos);
        return this;
    }

    public Plan addPresupuesto(Presupuesto presupuesto) {
        this.presupuestos.add(presupuesto);
        presupuesto.setPlan(this);
        return this;
    }

    public Plan removePresupuesto(Presupuesto presupuesto) {
        this.presupuestos.remove(presupuesto);
        presupuesto.setPlan(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plan)) {
            return false;
        }
        return getId() != null && getId().equals(((Plan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
