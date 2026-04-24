package ro.upt.ac.centrurecuperare.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;
import ro.upt.ac.centrurecuperare.service.ExportService;
import ro.upt.ac.centrurecuperare.service.InternareService;
import ro.upt.ac.centrurecuperare.service.PacientService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    private final ExportService exportService;
    private final PacientService pacientService;
    private final InternareService internareService;

    // =========== EXPORT PACIENTI ==============

    @GetMapping("/pacienti/excel")
    public ResponseEntity<byte[]> exportPacientiExcel() {
        logger.info("Request export pacienti in Excel");

        try {
            List<Pacient> pacienti = pacientService.getAllPacienti();
            byte[] excelBytes = exportService.exportPacientiToExcel(pacienti);

            String filename = "pacienti_" + getCurrentTimestamp() + ".xlsx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);

        } catch (IOException e) {
            logger.error("Eroare la export pacienti Excel", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ==================== EXPORT INTERNARI ====================

    @GetMapping("/internari/excel")
    public ResponseEntity<byte[]> exportInternariExcel() {
        logger.info("Request export internari in Excel");

        try {
            List<Internare> internari = internareService.getAllInternari();
            byte[] excelBytes = exportService.exportInternariToExcel(internari);

            String filename = "internari_" + getCurrentTimestamp() + ".xlsx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);

        } catch (IOException e) {
            logger.error("Eroare la export internari Excel", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/internari/pdf")
    public ResponseEntity<byte[]> exportInternariPdf() {
        logger.info("Request export internari in PDF");

        try {
            List<Internare> internari = internareService.getAllInternari();
            byte[] pdfBytes = exportService.exportInternariToPdf(internari);

            String filename = "raport_internari_" + getCurrentTimestamp() + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (IOException e) {
            logger.error("Eroare la export internari PDF", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ========== HELPER ============

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
}