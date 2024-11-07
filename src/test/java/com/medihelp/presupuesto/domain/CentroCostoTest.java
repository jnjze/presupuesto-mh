package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.CentroCostoTestSamples.*;
import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CentroCostoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroCosto.class);
        CentroCosto centroCosto1 = getCentroCostoSample1();
        CentroCosto centroCosto2 = new CentroCosto();
        assertThat(centroCosto1).isNotEqualTo(centroCosto2);

        centroCosto2.setId(centroCosto1.getId());
        assertThat(centroCosto1).isEqualTo(centroCosto2);

        centroCosto2 = getCentroCostoSample2();
        assertThat(centroCosto1).isNotEqualTo(centroCosto2);
    }

    @Test
    void presupuestoTest() {
        CentroCosto centroCosto = getCentroCostoRandomSampleGenerator();
        Presupuesto presupuestoBack = getPresupuestoRandomSampleGenerator();

        centroCosto.addPresupuesto(presupuestoBack);
        assertThat(centroCosto.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getCentroCosto()).isEqualTo(centroCosto);

        centroCosto.removePresupuesto(presupuestoBack);
        assertThat(centroCosto.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getCentroCosto()).isNull();

        centroCosto.presupuestos(new HashSet<>(Set.of(presupuestoBack)));
        assertThat(centroCosto.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getCentroCosto()).isEqualTo(centroCosto);

        centroCosto.setPresupuestos(new HashSet<>());
        assertThat(centroCosto.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getCentroCosto()).isNull();
    }
}
