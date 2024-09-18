package di.service.payment;

import di.model.entity.quickTicket.QuickPurchase;
import di.model.entity.quickTicket.QuickTicket;
import di.repository.quickPurchase.QuickPurchaseRepository;
import di.service.emailsevice.service.EmailService;
import di.model.dto.tickets.QuickTicketOrder;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void quickPurchase(QuickTicketOrder responseTicketOrder) {
        List<QuickTicket> tickets = new ArrayList<>();
        if(!responseTicketOrder.getQuantityTickets().isEmpty()){
            QuickPurchase quickPurchase = new QuickPurchase();
            for (Map.Entry<String, Integer> ticketList : responseTicketOrder.getQuantityTickets().entrySet()) {
                for (int i = 0; i < ticketList.getValue(); i++) {
                    QuickTicket quickTicket = TicketFactory.createQuickTicket(ticketList.getKey());
                    quickTicket.setQuickPurchase(quickPurchase);
                    tickets.add(quickTicket);
                }
            }
            quickPurchase.getTicketList().addAll(tickets);
            quickPurchase.setEmail(responseTicketOrder.getEmail());
            quickPurchaseRepository.save(quickPurchase);

            try{
                emailService.sendTicketByEmail(quickPurchase.getEmail(), quickPurchase.getTicketList());
                for(QuickTicket t : tickets){
                    t.isExpired();
                }
            }catch (Exception e){
                System.err.println("Список билетов был пустой : "  + e.getMessage());
            }
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
