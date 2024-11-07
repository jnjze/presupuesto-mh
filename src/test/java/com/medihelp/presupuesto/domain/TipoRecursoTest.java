package com.medihelp.presupuesto.domain;

import static com.medihelp.presupuesto.domain.RecursoTestSamples.*;
import static com.medihelp.presupuesto.domain.TipoRecursoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.medihelp.presupuesto.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TipoRecursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRecurso.class);
        TipoRecurso tipoRecurso1 = getTipoRecursoSample1();
        TipoRecurso tipoRecurso2 = new TipoRecurso();
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);

        tipoRecurso2.setId(tipoRecurso1.getId());
        assertThat(tipoRecurso1).isEqualTo(tipoRecurso2);

        tipoRecurso2 = getTipoRecursoSample2();
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);
    }

    @Test
    void recursoTest() {
        TipoRecurso tipoRecurso = getTipoRecursoRandomSampleGenerator();
        Recurso recursoBack = getRecursoRandomSampleGenerator();

        tipoRecurso.addRecurso(recursoBack);
        assertThat(tipoRecurso.getRecursos()).containsOnly(recursoBack);
        assertThat(recursoBack.getTipoRecurso()).isEqualTo(tipoRecurso);

        tipoRecurso.removeRecurso(recursoBack);
        assertThat(tipoRecurso.getRecursos()).doesNotContain(recursoBack);
        assertThat(recursoBack.getTipoRecurso()).isNull();

        tipoRecurso.recursos(new HashSet<>(Set.of(recursoBack)));
        assertThat(tipoRecurso.getRecursos()).containsOnly(recursoBack);
        assertThat(recursoBack.getTipoRecurso()).isEqualTo(tipoRecurso);

        tipoRecurso.setRecursos(new HashSet<>());
        assertThat(tipoRecurso.getRecursos()).doesNotContain(recursoBack);
        assertThat(recursoBack.getTipoRecurso()).isNull();
    }
}
