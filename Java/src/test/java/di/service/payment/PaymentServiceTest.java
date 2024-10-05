package di.service.payment;


import di.model.dto.tickets.QuickTicketOrder;
import di.model.entity.quickTicket.QuickPurchase;
import di.model.entity.telephone.Telephone;
import di.repository.quickPurchase.QuickPurchaseRepository;
import di.repository.telephone.TelephoneRepository;
import di.repository.user.UserRepository;
import di.service.emailsevice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private QuickPurchaseRepository quickPurchaseRepository;
    @Mock
    private TelephoneRepository telephoneRepository;

    @InjectMocks
    private PaymentServiceTest paymentServiceTest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //TODO ДОПИСАТЬ ТЕСТ

    @Test
    void testQuickPurchase(){
        QuickTicketOrder testOrder = new QuickTicketOrder();
        testOrder.setTelephone("123456789");
        testOrder.setEmail("example@test.com");

        Map<String, Integer> ticketList = new HashMap<>();
        ticketList.put("Adult", 2);
        testOrder.setQuantityTickets(ticketList);

        Telephone testTelephone = new Telephone("123456789");
        when(TelephoneRepository.existByNumber)
    }



}
