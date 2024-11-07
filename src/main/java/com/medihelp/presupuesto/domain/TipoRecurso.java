package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoRecurso.
 */
@Entity
@Table(name = "tipo_recurso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoRecurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_recurso_sequence")
    @SequenceGenerator(name = "tipo_recurso_sequence", sequenceName = "tipo_recurso_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoRecurso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoRecurso", "presupuesto" }, allowSetters = true)
    private Set<Recurso> recursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoRecurso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public TipoRecurso codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public TipoRecurso nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TipoRecurso descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Recurso> getRecursos() {
        return this.recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        if (this.recursos != null) {
            this.recursos.forEach(i -> i.setTipoRecurso(null));
        }
        if (recursos != null) {
            recursos.forEach(i -> i.setTipoRecurso(this));
        }
        this.recursos = recursos;
    }

    public TipoRecurso recursos(Set<Recurso> recursos) {
        this.setRecursos(recursos);
        return this;
    }

    public TipoRecurso addRecurso(Recurso recurso) {
        this.recursos.add(recurso);
        recurso.setTipoRecurso(this);
        return this;
    }

    public TipoRecurso removeRecurso(Recurso recurso) {
        this.recursos.remove(recurso);
        recurso.setTipoRecurso(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoRecurso)) {
            return false;
        }
        return getId() != null && getId().equals(((TipoRecurso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoRecurso{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
