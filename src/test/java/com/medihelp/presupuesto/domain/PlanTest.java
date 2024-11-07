package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.PlanTestSamples.*;
import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;
import static com.medihelp.presupuesto.domain.SubPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plan.class);
        Plan plan1 = getPlanSample1();
        Plan plan2 = new Plan();
        assertThat(plan1).isNotEqualTo(plan2);

        plan2.setId(plan1.getId());
        assertThat(plan1).isEqualTo(plan2);

        plan2 = getPlanSample2();
        assertThat(plan1).isNotEqualTo(plan2);
    }

    @Test
    void subPlanTest() {
        Plan plan = getPlanRandomSampleGenerator();
        SubPlan subPlanBack = getSubPlanRandomSampleGenerator();

        plan.addSubPlan(subPlanBack);
        assertThat(plan.getSubPlans()).containsOnly(subPlanBack);
        assertThat(subPlanBack.getPlan()).isEqualTo(plan);

        plan.removeSubPlan(subPlanBack);
        assertThat(plan.getSubPlans()).doesNotContain(subPlanBack);
        assertThat(subPlanBack.getPlan()).isNull();

        plan.subPlans(new HashSet<>(Set.of(subPlanBack)));
        assertThat(plan.getSubPlans()).containsOnly(subPlanBack);
        assertThat(subPlanBack.getPlan()).isEqualTo(plan);

        plan.setSubPlans(new HashSet<>());
        assertThat(plan.getSubPlans()).doesNotContain(subPlanBack);
        assertThat(subPlanBack.getPlan()).isNull();
    }

    @Test
    void presupuestoTest() {
        Plan plan = getPlanRandomSampleGenerator();
        Presupuesto presupuestoBack = getPresupuestoRandomSampleGenerator();

        plan.addPresupuesto(presupuestoBack);
        assertThat(plan.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getPlan()).isEqualTo(plan);

        plan.removePresupuesto(presupuestoBack);
        assertThat(plan.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getPlan()).isNull();

        plan.presupuestos(new HashSet<>(Set.of(presupuestoBack)));
        assertThat(plan.getPresupuestos()).containsOnly(presupuestoBack);
        assertThat(presupuestoBack.getPlan()).isEqualTo(plan);

        plan.setPresupuestos(new HashSet<>());
        assertThat(plan.getPresupuestos()).doesNotContain(presupuestoBack);
        assertThat(presupuestoBack.getPlan()).isNull();
    }
}
