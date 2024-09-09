package di.service.payment;

import di.model.entity.quickPurchase.QuickPurchase;
import di.repository.quickPurchase.QuickPurchaseRepository;
import di.service.emailsevice.service.EmailService;
import di.model.dto.tickets.QuickTicketOrder;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final QuickPurchaseRepository quickPurchaseRepository;

    @Autowired
    public PaymentService(UserRepository userRepository, EmailService emailService, QuickPurchaseRepository quickPurchaseRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.quickPurchaseRepository = quickPurchaseRepository;
    }


    public void quickPurchase(QuickTicketOrder responseTicketOrder) {
        List<AbstractTicket> tickets = new ArrayList<>();

        QuickPurchase quickPurchase = new QuickPurchase();
        quickPurchase.setEmail(responseTicketOrder.getEmail());

        for (Map.Entry<String, Integer> ticketList : responseTicketOrder.getQuantityTickets().entrySet()) {
            AbstractTicket ticket = TicketFactory.createTicket(ticketList.getKey());
            tickets.add(ticket);
        }
        try {
            emailService.sendTicketByEmail(responseTicketOrder.getEmail(), tickets);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при отправке email: " + e.getMessage());
        }
        try {
            quickPurchase.setAbstractTicketList(tickets);
            quickPurchaseRepository.save(quickPurchase);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении покупки: " + e.getMessage());
        }

    }


    //region PRIVATE METHODS
    private void sendTickets(User user, Map<String, Integer> tickets) {

        if (!tickets.isEmpty()) {
            for (Map.Entry<String, Integer> t : tickets.entrySet()) {
                for (int i = 0; i < t.getValue(); i++) {
                    AbstractTicket ticket = TicketFactory.createTicket(t.getKey());
                    ticket.setUser(user);
                    user.getUserTickets().add(ticket);
                }
            }
            try {
                emailService.sendTicketsToUser(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
//endregion
