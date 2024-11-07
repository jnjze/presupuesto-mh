package com.medihelp.presupuesto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Recurso.
 */
@Entity
@Table(name = "recurso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recurso_sequence")
    @SequenceGenerator(name = "recurso_sequence", sequenceName = "recurso_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "mes")
    private String mes;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @Column(name = "observacion", length = 1000)
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "recursos" }, allowSetters = true)
    private TipoRecurso tipoRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Presupuesto presupuesto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recurso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMes() {
        return this.mes;
    }

    public Recurso mes(String mes) {
        this.setMes(mes);
        return this;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Recurso valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public Recurso observacion(String observacion) {
        this.setObservacion(observacion);
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoRecurso getTipoRecurso() {
        return this.tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public Recurso tipoRecurso(TipoRecurso tipoRecurso) {
        this.setTipoRecurso(tipoRecurso);
        return this;
    }

    public Presupuesto getPresupuesto() {
        return this.presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Recurso presupuesto(Presupuesto presupuesto) {
        this.setPresupuesto(presupuesto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recurso)) {
            return false;
        }
        return getId() != null && getId().equals(((Recurso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recurso{" +
            "id=" + getId() +
            ", mes='" + getMes() + "'" +
            ", valor=" + getValor() +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
