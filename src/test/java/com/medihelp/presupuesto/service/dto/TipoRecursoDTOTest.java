package com.medihelp.presupuesto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoRecursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRecursoDTO.class);
        TipoRecursoDTO tipoRecursoDTO1 = new TipoRecursoDTO();
        tipoRecursoDTO1.setId(1L);
        TipoRecursoDTO tipoRecursoDTO2 = new TipoRecursoDTO();
        assertThat(tipoRecursoDTO1).isNotEqualTo(tipoRecursoDTO2);
        tipoRecursoDTO2.setId(tipoRecursoDTO1.getId());
        assertThat(tipoRecursoDTO1).isEqualTo(tipoRecursoDTO2);
        tipoRecursoDTO2.setId(2L);
        assertThat(tipoRecursoDTO1).isNotEqualTo(tipoRecursoDTO2);
        tipoRecursoDTO1.setId(null);
        assertThat(tipoRecursoDTO1).isNotEqualTo(tipoRecursoDTO2);
    }
}
