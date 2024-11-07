package com.medihelp.presupuesto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroCostoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroCostoDTO.class);
        CentroCostoDTO centroCostoDTO1 = new CentroCostoDTO();
        centroCostoDTO1.setId(1L);
        CentroCostoDTO centroCostoDTO2 = new CentroCostoDTO();
        assertThat(centroCostoDTO1).isNotEqualTo(centroCostoDTO2);
        centroCostoDTO2.setId(centroCostoDTO1.getId());
        assertThat(centroCostoDTO1).isEqualTo(centroCostoDTO2);
        centroCostoDTO2.setId(2L);
        assertThat(centroCostoDTO1).isNotEqualTo(centroCostoDTO2);
        centroCostoDTO1.setId(null);
        assertThat(centroCostoDTO1).isNotEqualTo(centroCostoDTO2);
    }
}
