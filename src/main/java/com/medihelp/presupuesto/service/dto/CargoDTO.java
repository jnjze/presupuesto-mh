package com.medihelp.presupuesto.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medihelp.presupuesto.domain.Cargo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CargoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nombre;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CargoDTO cargoDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cargoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "cargoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
