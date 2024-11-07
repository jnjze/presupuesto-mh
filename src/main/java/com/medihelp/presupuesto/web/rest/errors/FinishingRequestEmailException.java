package com.medihelp.presupuesto.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class FinishingRequestEmailException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    public FinishingRequestEmailException() {
        super(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ProblemDetailWithCauseBuilder.instance()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withTitle("El correo de finalización de solicitud no pudo ser enviado.")
                .build(),
            null
        );
    }
}
