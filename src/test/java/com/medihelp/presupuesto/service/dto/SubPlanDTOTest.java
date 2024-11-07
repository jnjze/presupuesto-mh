package com.medihelp.presupuesto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubPlanDTO.class);
        SubPlanDTO subPlanDTO1 = new SubPlanDTO();
        subPlanDTO1.setId(1L);
        SubPlanDTO subPlanDTO2 = new SubPlanDTO();
        assertThat(subPlanDTO1).isNotEqualTo(subPlanDTO2);
        subPlanDTO2.setId(subPlanDTO1.getId());
        assertThat(subPlanDTO1).isEqualTo(subPlanDTO2);
        subPlanDTO2.setId(2L);
        assertThat(subPlanDTO1).isNotEqualTo(subPlanDTO2);
        subPlanDTO1.setId(null);
        assertThat(subPlanDTO1).isNotEqualTo(subPlanDTO2);
    }
}
