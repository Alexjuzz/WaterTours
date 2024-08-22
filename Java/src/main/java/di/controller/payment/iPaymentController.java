package di.controller.payment;

import di.model.dto.tickets.ResponseTicketOrder;
import di.model.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/purchase")
public interface iPaymentController {

    ResponseEntity<String> quickPurchase(@RequestBody ResponseTicketOrder order);
}
