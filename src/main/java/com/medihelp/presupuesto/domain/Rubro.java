package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rubro.
 */
@Entity
@Table(name = "rubro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rubro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rubro_sequence")
    @SequenceGenerator(name = "rubro_sequence", sequenceName = "rubro_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rubro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recursos", "plan", "unidadFuncional", "rubro", "centroCosto" }, allowSetters = true)
    private Set<Presupuesto> presupuestos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rubro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Rubro codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Rubro nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Rubro descripcion(String descripcion) {
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
            this.presupuestos.forEach(i -> i.setRubro(null));
        }
        if (presupuestos != null) {
            presupuestos.forEach(i -> i.setRubro(this));
        }
        this.presupuestos = presupuestos;
    }

    public Rubro presupuestos(Set<Presupuesto> presupuestos) {
        this.setPresupuestos(presupuestos);
        return this;
    }

    public Rubro addPresupuesto(Presupuesto presupuesto) {
        this.presupuestos.add(presupuesto);
        presupuesto.setRubro(this);
        return this;
    }

    public Rubro removePresupuesto(Presupuesto presupuesto) {
        this.presupuestos.remove(presupuesto);
        presupuesto.setRubro(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rubro)) {
            return false;
        }
        return getId() != null && getId().equals(((Rubro) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rubro{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
