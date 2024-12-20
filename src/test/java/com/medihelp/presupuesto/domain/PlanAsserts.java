package com.medihelp.presupuesto.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPlanAllPropertiesEquals(Plan expected, Plan actual) {
        assertPlanAutoGeneratedPropertiesEquals(expected, actual);
        assertPlanAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPlanAllUpdatablePropertiesEquals(Plan expected, Plan actual) {
        assertPlanUpdatableFieldsEquals(expected, actual);
        assertPlanUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPlanAutoGeneratedPropertiesEquals(Plan expected, Plan actual) {
        assertThat(expected)
            .as("Verify Plan auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPlanUpdatableFieldsEquals(Plan expected, Plan actual) {
        assertThat(expected)
            .as("Verify Plan relevant properties")
            .satisfies(e -> assertThat(e.getCodigo()).as("check codigo").isEqualTo(actual.getCodigo()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPlanUpdatableRelationshipsEquals(Plan expected, Plan actual) {
        // empty method
    }
}
