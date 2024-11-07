package com.medihelp.presupuesto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RubroDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RubroDTO.class);
        RubroDTO rubroDTO1 = new RubroDTO();
        rubroDTO1.setId(1L);
        RubroDTO rubroDTO2 = new RubroDTO();
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
        rubroDTO2.setId(rubroDTO1.getId());
        assertThat(rubroDTO1).isEqualTo(rubroDTO2);
        rubroDTO2.setId(2L);
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
        rubroDTO1.setId(null);
        assertThat(rubroDTO1).isNotEqualTo(rubroDTO2);
    }
}
