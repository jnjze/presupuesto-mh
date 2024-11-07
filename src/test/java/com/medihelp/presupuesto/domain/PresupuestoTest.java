package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.CentroCostoTestSamples.*;
import static com.medihelp.presupuesto.domain.PlanTestSamples.*;
import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static com.medihelp.presupuesto.domain.RecursoTestSamples.*;
import static com.medihelp.presupuesto.domain.RubroTestSamples.*;
import static com.medihelp.presupuesto.domain.UnidadFuncionalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PresupuestoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presupuesto.class);
        Presupuesto presupuesto1 = getPresupuestoSample1();
        Presupuesto presupuesto2 = new Presupuesto();
        assertThat(presupuesto1).isNotEqualTo(presupuesto2);

        presupuesto2.setId(presupuesto1.getId());
        assertThat(presupuesto1).isEqualTo(presupuesto2);

        presupuesto2 = getPresupuestoSample2();
        assertThat(presupuesto1).isNotEqualTo(presupuesto2);
    }

    @Test
    void recursoTest() {
        Presupuesto presupuesto = getPresupuestoRandomSampleGenerator();
        Recurso recursoBack = getRecursoRandomSampleGenerator();

        presupuesto.addRecurso(recursoBack);
        assertThat(presupuesto.getRecursos()).containsOnly(recursoBack);
        assertThat(recursoBack.getPresupuesto()).isEqualTo(presupuesto);

        presupuesto.removeRecurso(recursoBack);
        assertThat(presupuesto.getRecursos()).doesNotContain(recursoBack);
        assertThat(recursoBack.getPresupuesto()).isNull();

        presupuesto.recursos(new HashSet<>(Set.of(recursoBack)));
        assertThat(presupuesto.getRecursos()).containsOnly(recursoBack);
        assertThat(recursoBack.getPresupuesto()).isEqualTo(presupuesto);

        presupuesto.setRecursos(new HashSet<>());
        assertThat(presupuesto.getRecursos()).doesNotContain(recursoBack);
        assertThat(recursoBack.getPresupuesto()).isNull();
    }

    @Test
    void planTest() {
        Presupuesto presupuesto = getPresupuestoRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        presupuesto.setPlan(planBack);
        assertThat(presupuesto.getPlan()).isEqualTo(planBack);

        presupuesto.plan(null);
        assertThat(presupuesto.getPlan()).isNull();
    }

    @Test
    void unidadFuncionalTest() {
        Presupuesto presupuesto = getPresupuestoRandomSampleGenerator();
        UnidadFuncional unidadFuncionalBack = getUnidadFuncionalRandomSampleGenerator();

        presupuesto.setUnidadFuncional(unidadFuncionalBack);
        assertThat(presupuesto.getUnidadFuncional()).isEqualTo(unidadFuncionalBack);

        presupuesto.unidadFuncional(null);
        assertThat(presupuesto.getUnidadFuncional()).isNull();
    }

    @Test
    void rubroTest() {
        Presupuesto presupuesto = getPresupuestoRandomSampleGenerator();
        Rubro rubroBack = getRubroRandomSampleGenerator();

        presupuesto.setRubro(rubroBack);
        assertThat(presupuesto.getRubro()).isEqualTo(rubroBack);

        presupuesto.rubro(null);
        assertThat(presupuesto.getRubro()).isNull();
    }

    @Test
    void centroCostoTest() {
        Presupuesto presupuesto = getPresupuestoRandomSampleGenerator();
        CentroCosto centroCostoBack = getCentroCostoRandomSampleGenerator();

        presupuesto.setCentroCosto(centroCostoBack);
        assertThat(presupuesto.getCentroCosto()).isEqualTo(centroCostoBack);

        presupuesto.centroCosto(null);
        assertThat(presupuesto.getCentroCosto()).isNull();
    }
}
