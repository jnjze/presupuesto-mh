package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.RubroAsserts.*;
import static com.medihelp.presupuesto.domain.RubroTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RubroMapperTest {

    private RubroMapper rubroMapper;

    @BeforeEach
    void setUp() {
        rubroMapper = new RubroMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRubroSample1();
        var actual = rubroMapper.toEntity(rubroMapper.toDto(expected));
        assertRubroAllPropertiesEquals(expected, actual);
    }
}
