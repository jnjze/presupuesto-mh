package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static com.medihelp.presupuesto.domain.RecursoTestSamples.*;
import static com.medihelp.presupuesto.domain.TipoRecursoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recurso.class);
        Recurso recurso1 = getRecursoSample1();
        Recurso recurso2 = new Recurso();
        assertThat(recurso1).isNotEqualTo(recurso2);

        recurso2.setId(recurso1.getId());
        assertThat(recurso1).isEqualTo(recurso2);

        recurso2 = getRecursoSample2();
        assertThat(recurso1).isNotEqualTo(recurso2);
    }

    @Test
    void tipoRecursoTest() {
        Recurso recurso = getRecursoRandomSampleGenerator();
        TipoRecurso tipoRecursoBack = getTipoRecursoRandomSampleGenerator();

        recurso.setTipoRecurso(tipoRecursoBack);
        assertThat(recurso.getTipoRecurso()).isEqualTo(tipoRecursoBack);

        recurso.tipoRecurso(null);
        assertThat(recurso.getTipoRecurso()).isNull();
    }

    @Test
    void presupuestoTest() {
        Recurso recurso = getRecursoRandomSampleGenerator();
        Presupuesto presupuestoBack = getPresupuestoRandomSampleGenerator();

        recurso.setPresupuesto(presupuestoBack);
        assertThat(recurso.getPresupuesto()).isEqualTo(presupuestoBack);

        recurso.presupuesto(null);
        assertThat(recurso.getPresupuesto()).isNull();
    }
}
