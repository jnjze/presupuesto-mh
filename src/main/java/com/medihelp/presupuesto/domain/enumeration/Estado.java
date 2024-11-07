package com.medihelp.presupuesto.domain.enumeration;

/**
 * The Estado enumeration.
 */
public enum Estado {
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado"),
    SIN_ASIGNAR("Sin asignar"),
    DEVOLUCION("Devolución");

    private String name;

    Estado(String devolucion) {
        this.name = devolucion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
