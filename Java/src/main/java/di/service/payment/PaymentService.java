package di.service.payment;

import di.customexceptions.telephone.TelephoneNotFoundException;
import di.model.entity.quickTicket.QuickPurchase;
import di.model.entity.quickTicket.QuickTicket;
import di.model.entity.telephone.Telephone;
import di.repository.quickPurchase.QuickPurchaseRepository;
import di.repository.telephone.TelephoneRepository;
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
    private final TelephoneRepository telephoneRepository;

    @Autowired
    public PaymentService(UserRepository userRepository, EmailService emailService, QuickPurchaseRepository quickPurchaseRepository, TelephoneRepository telephoneRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.quickPurchaseRepository = quickPurchaseRepository;
        this.telephoneRepository = telephoneRepository;
    }

    @Transactional
    public void quickPurchase(QuickTicketOrder responseTicketOrder) {
        List<QuickTicket> tickets = new ArrayList<>();
        if (!responseTicketOrder.getQuantityTickets().isEmpty()) {
            QuickPurchase quickPurchase = new QuickPurchase();
            for (Map.Entry<String, Integer> ticketList : responseTicketOrder.getQuantityTickets().entrySet()) {
                for (int i = 0; i < ticketList.getValue(); i++) {
                    QuickTicket quickTicket = TicketFactory.createQuickTicket(ticketList.getKey());
                    quickTicket.setQuickPurchase(quickPurchase);
                    tickets.add(quickTicket);
                }
            }


            Telephone userPhoneNumber = findOrCreateTelephone(responseTicketOrder.getTelephone());

            quickPurchase.setTelephone(userPhoneNumber);

            userPhoneNumber.setQuickPurchase(quickPurchase);

            quickPurchase.getTicketList().addAll(tickets);
            quickPurchase.setEmail(responseTicketOrder.getEmail());
            quickPurchaseRepository.save(quickPurchase);

            try {
                emailService.sendTicketByEmail(quickPurchase.getEmail(), quickPurchase.getTicketList());
                for (QuickTicket t : tickets) {
                    t.isExpired();
                }
            } catch (Exception e) {
                System.err.println("Список билетов был пустой : " + e.getMessage());
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

    private boolean findPhone(String number) {
        if(!number.isEmpty())  return telephoneRepository.existsByNumber(number);
        return false;
    }

    private Telephone getPhone(String number) {
        return telephoneRepository.getTelephoneByNumber(number).orElseThrow(() -> new TelephoneNotFoundException("Телефон не найден"));
    }
    private Telephone createTelephone(String number){
        return  new Telephone(number);
    }

    private Telephone findOrCreateTelephone(String number){
        if(findPhone(number)){

            return getPhone(number);
        }else {
            return createTelephone(number);
        }
    }

}

//endregion
