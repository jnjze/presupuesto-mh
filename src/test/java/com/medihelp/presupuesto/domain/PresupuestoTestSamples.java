package com.medihelp.presupuesto.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PresupuestoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Presupuesto getPresupuestoSample1() {
        return new Presupuesto()
            .id(1L)
            .consecutivo(1L)
            .descripcionActividad("descripcionActividad1")
            .nombreResponsable("nombreResponsable1")
            .correoResponsable("correoResponsable1");
    }

    public static Presupuesto getPresupuestoSample2() {
        return new Presupuesto()
            .id(2L)
            .consecutivo(2L)
            .descripcionActividad("descripcionActividad2")
            .nombreResponsable("nombreResponsable2")
            .correoResponsable("correoResponsable2");
    }

    public static Presupuesto getPresupuestoRandomSampleGenerator() {
        return new Presupuesto()
            .id(longCount.incrementAndGet())
            .consecutivo(longCount.incrementAndGet())
            .descripcionActividad(UUID.randomUUID().toString())
            .nombreResponsable(UUID.randomUUID().toString())
            .correoResponsable(UUID.randomUUID().toString());
    }
}
