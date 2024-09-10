package di.service.pdfManager;

import com.google.zxing.WriterException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import di.model.entity.quickTicket.QuickTicket;
import di.model.entity.ticket.AbstractTicket;
import di.service.generatorQR.GeneratorQR;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class PDFCreator {

    public byte[]  createPdf(AbstractTicket ticket) throws Exception {
        ByteArrayOutputStream  response = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(response);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Water tours ticket: ")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(35);
        document.add(header);




        byte[] qrCode = GeneratorQR.generateQRCodeImage(ticket.getUniqueTicketId().toString());
        ImageData imageData = ImageDataFactory.create(qrCode);
        Image qrImage = new Image(imageData);
        qrImage.setWidth(250);
        qrImage.setHeight(250);
        qrImage.setFixedPosition(
                (pdf.getDefaultPageSize().getWidth() - qrImage.getImageScaledWidth() - 75) ,
                pdf.getDefaultPageSize().getTop()- 330);

        document.add(qrImage);
        Table table = new Table(new float[]{1, 2});
        table.addCell("Name");
        table.addCell(ticket.getUser().getName());
        table.addCell("Phone");
        table.addCell(ticket.getUser().getTelephone().getNumber());
        table.addCell("Email");
        table.addCell(ticket.getUser().getEmail());
        table.addCell("Date");
        table.addCell(ticket.getDateStamp());
        table.addCell("Price");
        table.addCell(String.valueOf(ticket.getPrice()));
        table.setFixedPosition(36,300 , pdf.getDefaultPageSize().getWidth() - 72);
        document.add(table);
        document.close();
        return response.toByteArray();
    }

    public byte[] createQuickPdf(String email, QuickTicket tickets) throws IOException, WriterException {
        ByteArrayOutputStream  response = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(response);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Water tours ticket: ")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(35);
        document.add(header);




        byte[] qrCode = GeneratorQR.generateQRCodeImage(tickets.getUniqueTicketId().toString());
        ImageData imageData = ImageDataFactory.create(qrCode);
        Image qrImage = new Image(imageData);
        qrImage.setWidth(250);
        qrImage.setHeight(250);
        qrImage.setFixedPosition(
                (pdf.getDefaultPageSize().getWidth() - qrImage.getImageScaledWidth() - 75) ,
                pdf.getDefaultPageSize().getTop()- 330);

        document.add(qrImage);
        Table table = new Table(new float[]{1, 2});
        table.addCell("Email");
        table.addCell(email);
        table.addCell("Date");
        table.addCell(tickets.getDateStamp());
        table.addCell("Price");
        table.addCell(String.valueOf(tickets.getPrice()));
        table.setFixedPosition(36,300 , pdf.getDefaultPageSize().getWidth() - 72);
        document.add(table);
        document.close();
        return response.toByteArray();
    }
}