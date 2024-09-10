package di.service.payment;

import di.model.entity.quickTicket.QuickPurchase;
import di.model.entity.quickTicket.QuickTicket;
import di.repository.quickPurchase.QuickPurchaseRepository;
import di.repository.quickPurchase.QuickTicketRepository;
import di.service.emailsevice.service.EmailService;
import di.model.dto.tickets.QuickTicketOrder;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
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
        List<QuickTicket> tickets = new ArrayList<>();

        for (Map.Entry<String, Integer> ticketList : responseTicketOrder.getQuantityTickets().entrySet()) {
            QuickTicket quickTicket = TicketFactory.createQuickTicket(ticketList.getKey());
            tickets.add(quickTicket);
        }

        if (tickets.isEmpty()) {
            throw new RuntimeException("Ошибка: список билетов пуст.");
        }

        QuickPurchase quickPurchase = new QuickPurchase();
        quickPurchase.getTicketList().addAll(tickets);

        try {
            quickPurchaseRepository.save(quickPurchase);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении покупки : " + e.getMessage());
        }

        try {
            emailService.sendTicketByEmail(responseTicketOrder.getEmail(), tickets);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке email: " + e.getMessage());
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
