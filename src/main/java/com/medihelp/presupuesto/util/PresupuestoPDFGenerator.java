package com.medihelp.presupuesto.util;

import com.medihelp.presupuesto.service.dto.PresupuestoDTO;
import com.medihelp.presupuesto.service.dto.RecursoDTO;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PresupuestoPDFGenerator {

    public static File generatePresupuestoPDF(PresupuestoDTO presupuesto, String fileName) throws IOException {
        // Create a temporary file in the system's temp directory
        File tempFile = File.createTempFile(fileName, ".pdf");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Solicitud de Presupuesto");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);

                // Display each property with appropriate labels
                contentStream.showText("ID: " + presupuesto.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Consecutivo: " + presupuesto.getConsecutivo());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Descripción Actividad: " + presupuesto.getDescripcionActividad());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(
                    "Fecha Inicio: " + (presupuesto.getFechaInicio() != null ? presupuesto.getFechaInicio().format(dateFormatter) : "")
                );
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(
                    "Fecha Final: " + (presupuesto.getFechaFinal() != null ? presupuesto.getFechaFinal().format(dateFormatter) : "")
                );
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(
                    "Fecha Registro: " +
                    (presupuesto.getFechaRegistro() != null ? presupuesto.getFechaRegistro().format(dateFormatter) : "")
                );
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nombre Responsable: " + presupuesto.getNombreResponsable());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Estado: " + (presupuesto.getEstado() != null ? presupuesto.getEstado().name() : ""));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Correo Responsable: " + presupuesto.getCorreoResponsable());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Observaciones: " + presupuesto.getObservaciones());
                contentStream.newLineAtOffset(0, -20);

                if (presupuesto.getPlan() != null) {
                    contentStream.showText("Plan: " + presupuesto.getPlan().toString());
                    contentStream.newLineAtOffset(0, -20);
                }
                if (presupuesto.getUnidadFuncional() != null) {
                    contentStream.showText("Unidad Funcional: " + presupuesto.getUnidadFuncional().toString());
                    contentStream.newLineAtOffset(0, -20);
                }
                if (presupuesto.getRubro() != null) {
                    contentStream.showText("Rubro: " + presupuesto.getRubro().toString());
                    contentStream.newLineAtOffset(0, -20);
                }
                if (presupuesto.getCentroCosto() != null) {
                    contentStream.showText("Centro de Costo: " + presupuesto.getCentroCosto().toString());
                    contentStream.newLineAtOffset(0, -20);
                }
                if (presupuesto.getSubPlan() != null) {
                    contentStream.showText("Sub Plan: " + presupuesto.getSubPlan().toString());
                    contentStream.newLineAtOffset(0, -20);
                }
                contentStream.showText("Año: " + presupuesto.getAnio());
                contentStream.newLineAtOffset(0, -20);

                // Display resources in a structured way
                List<RecursoDTO> recursos = presupuesto.getRecursos();
                if (recursos != null && !recursos.isEmpty()) {
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Recursos:");
                    for (RecursoDTO recurso : recursos) {
                        contentStream.newLineAtOffset(20, -20);
                        contentStream.showText(
                            "Mes: " +
                            recurso.getMes() +
                            ", Tipo: " +
                            recurso.getTipoRecurso() +
                            ", Monto: " +
                            recurso.getValor() +
                            ", Descripción: " +
                            recurso.getObservacion()
                        );
                    }
                }

                contentStream.endText();
            }

            // Save the document to the temp file
            document.save(tempFile);
        }

        // Return the File object
        return tempFile;
    }
}
