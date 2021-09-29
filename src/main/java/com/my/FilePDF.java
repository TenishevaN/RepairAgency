package com.my;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.db.model.RepairRequest;

/**
 * {@ code FilePDF} class represents the implementation of the creating pdf document report.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class FilePDF {
    private static String FILE = "d:/FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    public static void createDocumentPDF(java.util.List<RepairRequest> list) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent(document, list);
            document.close();
            File theUMFile = new File(FILE);
            Desktop.getDesktop().open(theUMFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addContent(Document document, java.util.List<RepairRequest> list) throws DocumentException {

        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 2);
        Paragraph paragraph = new Paragraph("Report", catFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        preface.add(new Paragraph(paragraph));
        addEmptyLine(preface, 1);
         createTable( preface, list);
        document.add(preface);
    }

    private static void createTable(Paragraph subCatPart, java.util.List<RepairRequest> list)
           {
        PdfPTable table = new PdfPTable(7);

        PdfPCell c1 = new PdfPCell(new Phrase("N"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("User"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Master"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cost"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Description"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);
        for (RepairRequest item : list) {
            table.addCell(String.valueOf(item.getId()));
            table.addCell(String.valueOf(item.getDate()));
            table.addCell(String.valueOf(item.getStatusName()));
            table.addCell(String.valueOf(item.getUserName()));
            table.addCell(String.valueOf(item.getMasterName()));
            table.addCell(String.valueOf(item.getCost()));
            table.addCell(String.valueOf(item.getDescription()));
        }
        subCatPart.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}