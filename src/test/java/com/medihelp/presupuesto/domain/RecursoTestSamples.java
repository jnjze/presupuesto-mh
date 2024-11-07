package com.medihelp.presupuesto.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecursoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recurso getRecursoSample1() {
        return new Recurso().id(1L).mes("mes1").observacion("observacion1");
    }

    public static Recurso getRecursoSample2() {
        return new Recurso().id(2L).mes("mes2").observacion("observacion2");
    }

    public static Recurso getRecursoRandomSampleGenerator() {
        return new Recurso().id(longCount.incrementAndGet()).mes(UUID.randomUUID().toString()).observacion(UUID.randomUUID().toString());
    }
}
