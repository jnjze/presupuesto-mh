package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.CentroCostoAsserts.*;
import static com.medihelp.presupuesto.domain.CentroCostoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CentroCostoMapperTest {

    private CentroCostoMapper centroCostoMapper;

    @BeforeEach
    void setUp() {
        centroCostoMapper = new CentroCostoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCentroCostoSample1();
        var actual = centroCostoMapper.toEntity(centroCostoMapper.toDto(expected));
        assertCentroCostoAllPropertiesEquals(expected, actual);
    }
}
