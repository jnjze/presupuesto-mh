package com.medihelp.presupuesto.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RubroTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rubro getRubroSample1() {
        return new Rubro().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1");
    }

    public static Rubro getRubroSample2() {
        return new Rubro().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2");
    }

    public static Rubro getRubroRandomSampleGenerator() {
        return new Rubro()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
