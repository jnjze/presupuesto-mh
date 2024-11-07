package com.medihelp.presupuesto.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UnidadFuncionalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UnidadFuncional getUnidadFuncionalSample1() {
        return new UnidadFuncional().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1");
    }

    public static UnidadFuncional getUnidadFuncionalSample2() {
        return new UnidadFuncional().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2");
    }

    public static UnidadFuncional getUnidadFuncionalRandomSampleGenerator() {
        return new UnidadFuncional()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
