package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.UnidadFuncionalAsserts.*;
import static com.medihelp.presupuesto.domain.UnidadFuncionalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnidadFuncionalMapperTest {

    private UnidadFuncionalMapper unidadFuncionalMapper;

    @BeforeEach
    void setUp() {
        unidadFuncionalMapper = new UnidadFuncionalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUnidadFuncionalSample1();
        var actual = unidadFuncionalMapper.toEntity(unidadFuncionalMapper.toDto(expected));
        assertUnidadFuncionalAllPropertiesEquals(expected, actual);
    }
}
