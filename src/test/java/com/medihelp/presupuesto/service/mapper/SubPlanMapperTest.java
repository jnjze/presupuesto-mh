package com.medihelp.presupuesto.service.mapper;

import static com.medihelp.presupuesto.domain.SubPlanAsserts.*;
import static com.medihelp.presupuesto.domain.SubPlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubPlanMapperTest {

    private SubPlanMapper subPlanMapper;

    @BeforeEach
    void setUp() {
        subPlanMapper = new SubPlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubPlanSample1();
        var actual = subPlanMapper.toEntity(subPlanMapper.toDto(expected));
        assertSubPlanAllPropertiesEquals(expected, actual);
    }
}
