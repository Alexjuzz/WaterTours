package di.service.pdfManager;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import di.service.generatorQR.GeneratorQR;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class PDFCreator {

    public void createPdf(String dest) throws Exception {
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        Document document = new Document(pdf);

        // Add a paragraph for the header
        Paragraph header = new Paragraph("Water tours ticket: ")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(35);
        document.add(header);




        byte[] qrCode = GeneratorQR.generateQRCodeImage("John Doe - 2024-07-23 - Concert");
        ImageData imageData = ImageDataFactory.create(qrCode);
        Image qrImage = new Image(imageData);
        qrImage.setWidth(250);
        qrImage.setHeight(250);
        qrImage.setFixedPosition(
                (pdf.getDefaultPageSize().getWidth() - qrImage.getImageScaledWidth() - 75) ,
                pdf.getDefaultPageSize().getTop()- 330);

        document.add(qrImage);
        Table table = new Table(new float[]{1, 2});

        float yPosition = qrImage.getImageHeight() ;

        table.addCell("Name");
        table.addCell("John Doe");
        table.addCell("Event");
        table.addCell("Concert");
        table.addCell("Date");
        table.addCell("2024-07-23");
        table.setFixedPosition(36, yPosition, pdf.getDefaultPageSize().getWidth() - 72);
        document.add(table);
        document.close();
    }
}