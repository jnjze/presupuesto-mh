package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentroCosto.
 */
@Entity
@Table(name = "centro_costo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroCosto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contro_costo_sequence")
    @SequenceGenerator(name = "centro_costo_sequence", sequenceName = "centro_costo_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centroCosto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recursos", "plan", "unidadFuncional", "rubro", "centroCosto" }, allowSetters = true)
    private Set<Presupuesto> presupuestos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroCosto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public CentroCosto codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public CentroCosto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public CentroCosto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Presupuesto> getPresupuestos() {
        return this.presupuestos;
    }

    public void setPresupuestos(Set<Presupuesto> presupuestos) {
        if (this.presupuestos != null) {
            this.presupuestos.forEach(i -> i.setCentroCosto(null));
        }
        if (presupuestos != null) {
            presupuestos.forEach(i -> i.setCentroCosto(this));
        }
        this.presupuestos = presupuestos;
    }

    public CentroCosto presupuestos(Set<Presupuesto> presupuestos) {
        this.setPresupuestos(presupuestos);
        return this;
    }

    public CentroCosto addPresupuesto(Presupuesto presupuesto) {
        this.presupuestos.add(presupuesto);
        presupuesto.setCentroCosto(this);
        return this;
    }

    public CentroCosto removePresupuesto(Presupuesto presupuesto) {
        this.presupuestos.remove(presupuesto);
        presupuesto.setCentroCosto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroCosto)) {
            return false;
        }
        return getId() != null && getId().equals(((CentroCosto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroCosto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
