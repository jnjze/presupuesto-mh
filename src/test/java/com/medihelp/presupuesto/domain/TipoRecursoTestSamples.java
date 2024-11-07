package com.medihelp.presupuesto.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TipoRecursoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TipoRecurso getTipoRecursoSample1() {
        return new TipoRecurso().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1");
    }

    public static TipoRecurso getTipoRecursoSample2() {
        return new TipoRecurso().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2");
    }

    public static TipoRecurso getTipoRecursoRandomSampleGenerator() {
        return new TipoRecurso()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
