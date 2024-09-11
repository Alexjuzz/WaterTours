package di.service.emailsevice.service;

import di.model.entity.quickTicket.QuickTicket;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.User;
import di.service.pdfManager.PDFCreator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public void sendTicketToUser(User user, AbstractTicket ticket) throws Exception {
        sendTicketToUser(user.getEmail(), "test title", pdfCreator.createPdf(ticket));
    }

    public void sendTicketsToUser(User user) throws Exception {
        String title = "Ticket : ";
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("juzzleee@yandex.ru");
        helper.setTo(user.getEmail());
        helper.setSubject(title);
        helper.setText("Here is your ticket");
        int counter = 1;
        for (AbstractTicket tickets : user.getUserTickets()) {
            ByteArrayResource pdfResource = new ByteArrayResource(pdfCreator.createPdf(tickets));
            helper.addAttachment("ticket " + counter + ".pdf", pdfResource, "application/pdf");
            counter++;
        }
        sender.send(message);
    }

    public void sendTicketByEmail(String email, List<QuickTicket> ticketList) throws Exception {
        String title = "Ticket : ";
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("juzzleee@yandex.ru");
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText("Here is your ticket");
        int counter = 1;
        for (QuickTicket tickets : ticketList) {
            ByteArrayResource pdfResource = new ByteArrayResource(pdfCreator.createQuickPdf(email, tickets));
            helper.addAttachment("ticket " + +counter + ".pdf", pdfResource, "application/pdf");
            counter++;
        }
        sender.send(message);
    }
}
