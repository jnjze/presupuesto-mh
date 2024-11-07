package com.medihelp.presupuesto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadFuncionalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadFuncionalDTO.class);
        UnidadFuncionalDTO unidadFuncionalDTO1 = new UnidadFuncionalDTO();
        unidadFuncionalDTO1.setId(1L);
        UnidadFuncionalDTO unidadFuncionalDTO2 = new UnidadFuncionalDTO();
        assertThat(unidadFuncionalDTO1).isNotEqualTo(unidadFuncionalDTO2);
        unidadFuncionalDTO2.setId(unidadFuncionalDTO1.getId());
        assertThat(unidadFuncionalDTO1).isEqualTo(unidadFuncionalDTO2);
        unidadFuncionalDTO2.setId(2L);
        assertThat(unidadFuncionalDTO1).isNotEqualTo(unidadFuncionalDTO2);
        unidadFuncionalDTO1.setId(null);
        assertThat(unidadFuncionalDTO1).isNotEqualTo(unidadFuncionalDTO2);
    }
}
