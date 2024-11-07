package com.medihelp.presupuesto.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;

public class HeaderCustom {

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);

        try {
            headers.add("X-" + applicationName + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException var5) {}

        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation ? applicationName + "." + entityName + ".created" : "Solicitud creada con consecutivo " + param;
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".updated"
            : "Solicitud actualizada, consecutivo " + param;
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createEntityDeletionAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".deleted"
            : "La solicitud con identificador " + param + "ha sido eliminada";
        return createAlert(applicationName, message, param);
    }
}
