package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static com.medihelp.presupuesto.domain.UnidadFuncionalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UnidadFuncionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadFuncional.class);
        UnidadFuncional unidadFuncional1 = getUnidadFuncionalSample1();
        UnidadFuncional unidadFuncional2 = new UnidadFuncional();
        assertThat(unidadFuncional1).isNotEqualTo(unidadFuncional2);

        unidadFuncional2.setId(unidadFuncional1.getId());
        assertThat(unidadFuncional1).isEqualTo(unidadFuncional2);

        unidadFuncional2 = getUnidadFuncionalSample2();
        assertThat(unidadFuncional1).isNotEqualTo(unidadFuncional2);
    }

    @Test
    void presupuestoTest() {
        UnidadFuncional unidadFuncional = getUnidadFuncionalRandomSampleGenerator();
        Presupuesto presupuestoBack = getPresupuestoRandomSampleGenerator();

        unidadFuncional.addPresupuesto(presupuestoBack);
        assertThat(unidadFuncional.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getUnidadFuncional()).isEqualTo(unidadFuncional);

        unidadFuncional.removePresupuesto(presupuestoBack);
        assertThat(unidadFuncional.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getUnidadFuncional()).isNull();

        unidadFuncional.presupuestos(new HashSet<>(Set.of(presupuestoBack)));
        assertThat(unidadFuncional.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getUnidadFuncional()).isEqualTo(unidadFuncional);

        unidadFuncional.setPresupuestos(new HashSet<>());
        assertThat(unidadFuncional.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getUnidadFuncional()).isNull();
    }
}
