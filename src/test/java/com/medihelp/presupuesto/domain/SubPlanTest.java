package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.PlanTestSamples.*;
import static com.medihelp.presupuesto.domain.SubPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubPlan.class);
        SubPlan subPlan1 = getSubPlanSample1();
        SubPlan subPlan2 = new SubPlan();
        assertThat(subPlan1).isNotEqualTo(subPlan2);

        subPlan2.setId(subPlan1.getId());
        assertThat(subPlan1).isEqualTo(subPlan2);

        subPlan2 = getSubPlanSample2();
        assertThat(subPlan1).isNotEqualTo(subPlan2);
    }

    @Test
    void planTest() {
        SubPlan subPlan = getSubPlanRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        subPlan.setPlan(planBack);
        assertThat(subPlan.getPlan()).isEqualTo(planBack);

        subPlan.plan(null);
        assertThat(subPlan.getPlan()).isNull();
    }
}
