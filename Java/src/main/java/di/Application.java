package di;

import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.service.pdfManager.PDFCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
    }


}
