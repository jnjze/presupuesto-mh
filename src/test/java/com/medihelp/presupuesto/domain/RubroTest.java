package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static com.medihelp.presupuesto.domain.RubroTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RubroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubro.class);
        Rubro rubro1 = getRubroSample1();
        Rubro rubro2 = new Rubro();
        assertThat(rubro1).isNotEqualTo(rubro2);

        rubro2.setId(rubro1.getId());
        assertThat(rubro1).isEqualTo(rubro2);

        rubro2 = getRubroSample2();
        assertThat(rubro1).isNotEqualTo(rubro2);
    }

    @Test
    void presupuestoTest() {
        Rubro rubro = getRubroRandomSampleGenerator();
        Presupuesto presupuestoBack = getPresupuestoRandomSampleGenerator();

        rubro.addPresupuesto(presupuestoBack);
        assertThat(rubro.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getRubro()).isEqualTo(rubro);

        rubro.removePresupuesto(presupuestoBack);
        assertThat(rubro.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getRubro()).isNull();

        rubro.presupuestos(new HashSet<>(Set.of(presupuestoBack)));
        assertThat(rubro.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getRubro()).isEqualTo(rubro);

        rubro.setPresupuestos(new HashSet<>());
        assertThat(rubro.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getRubro()).isNull();
    }
}
