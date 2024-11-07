package com.medihelp.presupuesto.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medihelp.presupuesto.domain.Rubro} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RubroDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nombre;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RubroDTO)) {
            return false;
        }

        RubroDTO rubroDTO = (RubroDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rubroDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RubroDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}