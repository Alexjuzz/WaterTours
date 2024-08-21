package di.controller.payment;
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
        public ResponseEntity<String> quickPurchase(@RequestBody User user, @RequestParam String typeTicket){
        paymentService.quickPurchase(user,typeTicket);
        return ResponseEntity.ok("Successful buy");
    }


}
