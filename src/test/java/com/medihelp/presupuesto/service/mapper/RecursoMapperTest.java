package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.RecursoAsserts.*;
import static com.medihelp.presupuesto.domain.RecursoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecursoMapperTest {

    private RecursoMapper recursoMapper;

    @BeforeEach
    void setUp() {
        recursoMapper = new RecursoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecursoSample1();
        var actual = recursoMapper.toEntity(recursoMapper.toDto(expected));
        assertRecursoAllPropertiesEquals(expected, actual);
    }
}
