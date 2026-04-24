package ro.upt.ac.centrurecuperare.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.upt.ac.centrurecuperare.dto.RezultatAlocare;
import ro.upt.ac.centrurecuperare.model.Internare;
import ro.upt.ac.centrurecuperare.model.Pacient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private static final Logger logger = LoggerFactory.getLogger(ExportService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ==================== EXCEL EXPORTS ====================

    public byte[] exportPacientiToExcel(List<Pacient> pacienti) throws IOException {
        logger.info("Exportare {} pacienti in format Excel", pacienti.size());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Pacienti");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nume", "Prenume", "CNP", "Data Nasterii", "Telefon", "Adresa"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 1;
            for (Pacient pacient : pacienti) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(pacient.getId());
                row.createCell(1).setCellValue(pacient.getNume());
                row.createCell(2).setCellValue(pacient.getPrenume());
                row.createCell(3).setCellValue(pacient.getCnp());
                row.createCell(4).setCellValue(pacient.getDataNasterii() != null ?
                        pacient.getDataNasterii().format(DATE_FORMATTER) : "");
                row.createCell(5).setCellValue(pacient.getTelefon() != null ? pacient.getTelefon() : "");
                row.createCell(6).setCellValue(pacient.getAdresa() != null ? pacient.getAdresa() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            logger.info("Export Excel pacienti finalizat cu succes");
            return out.toByteArray();
        }
    }

    /**
     * Export internari la format Excel
     */
    public byte[] exportInternariToExcel(List<Internare> internari) throws IOException {
        logger.info("Exportare {} internari in format Excel", internari.size());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Internari");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Pacient", "Data Internare", "Data Externare", "Durata (zile)",
                    "Prioritate", "Status", "Salon", "Observatii"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 1;
            for (Internare internare : internari) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(internare.getId());
                row.createCell(1).setCellValue(internare.getPacient().getNume() + " " +
                        internare.getPacient().getPrenume());
                row.createCell(2).setCellValue(internare.getDataInternare() != null ?
                        internare.getDataInternare().format(DATE_FORMATTER) : "");
                row.createCell(3).setCellValue(internare.getDataExternare() != null ?
                        internare.getDataExternare().format(DATE_FORMATTER) : "");
                row.createCell(4).setCellValue(internare.getDurataEstimata() != null ?
                        internare.getDurataEstimata() : 0);
                row.createCell(5).setCellValue(internare.getPrioritate() != null ?
                        internare.getPrioritate().toString() : "");
                row.createCell(6).setCellValue(internare.getStatus() != null ?
                        internare.getStatus().toString() : "");
                row.createCell(7).setCellValue(internare.getSalon() != null ?
                        "Salon " + internare.getSalon().getNumar() : "Nealocat");
                row.createCell(8).setCellValue(internare.getObservatii() != null ?
                        internare.getObservatii() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            logger.info("Export Excel internari finalizat cu succes");
            return out.toByteArray();
        }
    }

    /**
     * Export rezultate optimizare la format Excel
     */
    public byte[] exportRezultateOptimizareToExcel(List<RezultatAlocare> rezultate, String algoritm) throws IOException {
        logger.info("Exportare {} rezultate optimizare ({}) in format Excel", rezultate.size(), algoritm);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Rezultate " + algoritm);

            // Title style
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Rezultate Optimizare - " + algoritm);
            titleCell.setCellStyle(titleStyle);

            // Empty row
            sheet.createRow(1);

            // Header row
            Row headerRow = sheet.createRow(2);
            String[] headers = {"Pacient", "CNP", "Prioritate", "Status", "Salon Alocat", "Motiv"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 3;
            for (RezultatAlocare rezultat : rezultate) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(rezultat.getInternare().getPacient().getNume() + " " +
                        rezultat.getInternare().getPacient().getPrenume());
                row.createCell(1).setCellValue(rezultat.getInternare().getPacient().getCnp());
                row.createCell(2).setCellValue(rezultat.getInternare().getPrioritate().toString());
                row.createCell(3).setCellValue(rezultat.isSuccess() ? "SUCCES" : "ESUAT");
                row.createCell(4).setCellValue(rezultat.getSalonAlocat() != null ?
                        "Salon " + rezultat.getSalonAlocat().getNumar() : "N/A");
                row.createCell(5).setCellValue(rezultat.getMotiv() != null ? rezultat.getMotiv() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            logger.info("Export Excel rezultate optimizare finalizat cu succes");
            return out.toByteArray();
        }
    }

    // ========= PDF EXPORTS =========

    /**
     * Export internari la format PDF
     */
    public byte[] exportInternariToPdf(List<Internare> internari) throws IOException {
        logger.info("Exportare {} internari in format PDF", internari.size());

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph("RAPORT INTERNARI")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Table
            Table table = new Table(new float[]{1, 3, 2, 2, 2, 2, 3});
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

            // Headers
            table.addHeaderCell("ID");
            table.addHeaderCell("Pacient");
            table.addHeaderCell("Data Internare");
            table.addHeaderCell("Durata");
            table.addHeaderCell("Prioritate");
            table.addHeaderCell("Status");
            table.addHeaderCell("Salon");

            // Data
            for (Internare internare : internari) {
                table.addCell(String.valueOf(internare.getId()));
                table.addCell(internare.getPacient().getNume() + " " + internare.getPacient().getPrenume());
                table.addCell(internare.getDataInternare() != null ?
                        internare.getDataInternare().format(DATE_FORMATTER) : "");
                table.addCell(internare.getDurataEstimata() + " zile");
                table.addCell(internare.getPrioritate().toString());
                table.addCell(internare.getStatus().toString());
                table.addCell(internare.getSalon() != null ?
                        "Salon " + internare.getSalon().getNumar() : "Nealocat");
            }

            document.add(table);
            document.close();

            logger.info("Export PDF internari finalizat cu succes");
            return out.toByteArray();
        }
    }

    /**
     * Export rezultate optimizare la format PDF
     */
    public byte[] exportRezultateOptimizareToPdf(List<RezultatAlocare> rezultate, String algoritm) throws IOException {
        logger.info("Exportare {} rezultate optimizare ({}) in format PDF", rezultate.size(), algoritm);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph("REZULTATE OPTIMIZARE - " + algoritm)
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Statistici
            long succes = rezultate.stream().filter(RezultatAlocare::isSuccess).count();
            long esuat = rezultate.size() - succes;
            double rataSucces = rezultate.size() > 0 ? (succes * 100.0 / rezultate.size()) : 0;

            Paragraph stats = new Paragraph(String.format(
                    "Total: %d | Succes: %d | Esuat: %d | Rata Succes: %.2f%%",
                    rezultate.size(), succes, esuat, rataSucces
            )).setBold();
            document.add(stats);

            document.add(new Paragraph("\n"));

            // Table
            Table table = new Table(new float[]{3, 2, 2, 2, 3});
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

            // Headers
            table.addHeaderCell("Pacient");
            table.addHeaderCell("Prioritate");
            table.addHeaderCell("Status");
            table.addHeaderCell("Salon");
            table.addHeaderCell("Motiv");

            // Data
            for (RezultatAlocare rezultat : rezultate) {
                table.addCell(rezultat.getInternare().getPacient().getNume() + " " +
                        rezultat.getInternare().getPacient().getPrenume());
                table.addCell(rezultat.getInternare().getPrioritate().toString());
                table.addCell(rezultat.isSuccess() ? "SUCCES" : "ESUAT");
                table.addCell(rezultat.getSalonAlocat() != null ?
                        "Salon " + rezultat.getSalonAlocat().getNumar() : "N/A");
                table.addCell(rezultat.getMotiv() != null ? rezultat.getMotiv() : "");
            }

            document.add(table);
            document.close();

            logger.info("Export PDF rezultate optimizare finalizat cu succes");
            return out.toByteArray();
        }
    }
}