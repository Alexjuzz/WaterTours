package di;

import di.service.pdfManager.PDFCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		try {
			String dest = "C:\\Users\\user\\Desktop\\Диплом\\Project\\doc.pdf";
			new PDFCreator().createPdf(dest);
			System.out.println("PDF Created!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(Application.class, args);
    }


}
