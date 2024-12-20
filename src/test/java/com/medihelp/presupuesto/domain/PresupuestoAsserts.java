package com.medihelp.presupuesto.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PresupuestoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPresupuestoAllPropertiesEquals(Presupuesto expected, Presupuesto actual) {
        assertPresupuestoAutoGeneratedPropertiesEquals(expected, actual);
        assertPresupuestoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPresupuestoAllUpdatablePropertiesEquals(Presupuesto expected, Presupuesto actual) {
        assertPresupuestoUpdatableFieldsEquals(expected, actual);
        assertPresupuestoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPresupuestoAutoGeneratedPropertiesEquals(Presupuesto expected, Presupuesto actual) {
        assertThat(expected)
            .as("Verify Presupuesto auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPresupuestoUpdatableFieldsEquals(Presupuesto expected, Presupuesto actual) {
        assertThat(expected)
            .as("Verify Presupuesto relevant properties")
            .satisfies(e -> assertThat(e.getConsecutivo()).as("check consecutivo").isEqualTo(actual.getConsecutivo()))
            .satisfies(e ->
                assertThat(e.getDescripcionActividad()).as("check descripcionActividad").isEqualTo(actual.getDescripcionActividad())
            )
            .satisfies(e -> assertThat(e.getFechaInicio()).as("check fechaInicio").isEqualTo(actual.getFechaInicio()))
            .satisfies(e -> assertThat(e.getFechaFinal()).as("check fechaFinal").isEqualTo(actual.getFechaFinal()))
            .satisfies(e -> assertThat(e.getFechaRegistro()).as("check fechaRegistro").isEqualTo(actual.getFechaRegistro()))
            .satisfies(e -> assertThat(e.getNombreResponsable()).as("check nombreResponsable").isEqualTo(actual.getNombreResponsable()))
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()))
            .satisfies(e -> assertThat(e.getCorreoResponsable()).as("check correoResponsable").isEqualTo(actual.getCorreoResponsable()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPresupuestoUpdatableRelationshipsEquals(Presupuesto expected, Presupuesto actual) {
        assertThat(expected)
            .as("Verify Presupuesto relationships")
            .satisfies(e -> assertThat(e.getPlan()).as("check plan").isEqualTo(actual.getPlan()))
            .satisfies(e -> assertThat(e.getUnidadFuncional()).as("check unidadFuncional").isEqualTo(actual.getUnidadFuncional()))
            .satisfies(e -> assertThat(e.getRubro()).as("check rubro").isEqualTo(actual.getRubro()))
            .satisfies(e -> assertThat(e.getCentroCosto()).as("check centroCosto").isEqualTo(actual.getCentroCosto()));
    }
}
