package di.controller.payment;
import di.model.dto.tickets.ResponseTicket;
import di.model.dto.tickets.ResponseTicketOrder;
import di.model.entity.user.User;
import di.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController implements iPaymentController{
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    @RequestMapping("/quick")
        public ResponseEntity<String> quickPurchase(@RequestBody ResponseTicketOrder order){
        paymentService.quickPurchase(order);
        return ResponseEntity.ok("Successful buy");
    }


}
