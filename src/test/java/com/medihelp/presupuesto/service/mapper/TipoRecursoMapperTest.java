package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.TipoRecursoAsserts.*;
import static com.medihelp.presupuesto.domain.TipoRecursoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoRecursoMapperTest {

    private TipoRecursoMapper tipoRecursoMapper;

    @BeforeEach
    void setUp() {
        tipoRecursoMapper = new TipoRecursoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTipoRecursoSample1();
        var actual = tipoRecursoMapper.toEntity(tipoRecursoMapper.toDto(expected));
        assertTipoRecursoAllPropertiesEquals(expected, actual);
    }
}
