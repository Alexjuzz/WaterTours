package di.controller.payment;

import di.model.dto.tickets.ResponseTicketOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/purchase")
public interface iPaymentController {

    ResponseEntity<String> quickPurchase(@RequestBody ResponseTicketOrder order) throws Exception;
}
