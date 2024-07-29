package di.emailsevice.service;

import com.itextpdf.layout.Document;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import di.service.pdfManager.PDFCreator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private PDFCreator pdfCreator;
    public void sendSimpleMessage(String to, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("juzzleee@yandex.ru");
        message.setTo(to);
        message.setSubject(title);
        message.setText(text);
        sender.send(message);
    }

    private void sendTicketToUser(String to, String title, byte[] ticket) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("juzzleee@yandex.ru");
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText("Here is your ticket");
        ByteArrayResource pdfResource = new ByteArrayResource(ticket);
        helper.addAttachment("ticket.pdf", pdfResource, "application/pdf");
        sender.send(message);
    }

    public void sendTicketToUser(GuestUser user, AbstractTicket ticket) throws Exception {
        sendTicketToUser(user.getEmail(), "test title", pdfCreator.createPdf(ticket));
    }
}
