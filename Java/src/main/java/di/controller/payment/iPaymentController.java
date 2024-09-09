package di.controller.payment;

import di.model.dto.tickets.QuickTicketOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/purchase")
public interface iPaymentController {

    ResponseEntity<String> quickPurchase(@RequestBody QuickTicketOrder order) throws Exception;
}
