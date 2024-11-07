package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.PresupuestoAsserts.*;
import static com.medihelp.presupuesto.domain.PresupuestoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PresupuestoMapperTest {

    private PresupuestoMapper presupuestoMapper;

    @BeforeEach
    void setUp() {
        presupuestoMapper = new PresupuestoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPresupuestoSample1();
        var actual = presupuestoMapper.toEntity(presupuestoMapper.toDto(expected));
        assertPresupuestoAllPropertiesEquals(expected, actual);
    }
}
