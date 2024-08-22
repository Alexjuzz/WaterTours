package di.service.payment;

import di.emailsevice.service.EmailService;
import di.model.dto.tickets.ResponseTicket;
import di.model.dto.tickets.ResponseTicketOrder;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.telephone.Telephone;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {
    private final UserRepository userRepository;
    private final EmailService emailService;


    public PaymentService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private ArrayList<AbstractTicket> getTickets(Map<String, Integer> tickets) {
        ArrayList<AbstractTicket> resultTickets = new ArrayList<>();
        if (!tickets.isEmpty()) {
            for (Map.Entry<String, Integer> t : tickets.entrySet()) {
                for (int i = 0; i < t.getValue(); i++) {
                    resultTickets.add(TicketFactory.createTicket(t.getKey()));
                }
            }
        }
        return resultTickets;
    }

    //todo дописать методы. после получения пользователя или создания нового надо сделать метод который бы по количеству и названию добавлял
    // пользвателю нужные билеты. Проблема в том что старые билеты нужно тоже сохранить и не печатать.
    public void quickPurchase(ResponseTicketOrder responseTicket) {
        ArrayList<AbstractTicket> tickets = getTickets(responseTicket.getQuantityTickets());

        Optional<User> getUserOptional = userRepository.getUserByTelephone(responseTicket.getUser().getTelephone().toString());
        User guestUser;
        if (getUserOptional.isPresent()) {

            guestUser = getUserOptional.get();

            if (guestUser.getEmail().isEmpty()) {
                throw new RuntimeException();
            }
            return;
        } else {

            guestUser = new GuestUser();
            Telephone telephone = new Telephone(responseTicket.getUser().getTelephone().getNumber());
            telephone.setUser(guestUser);

            guestUser.setName(responseTicket.getUser().getName());
            guestUser.setEmail(responseTicket.getUser().getEmail());
        }

        guestUser.getUserTickets().add(ticket);
        ticket.setUser(guestUser);

        userRepository.save(guestUser);

        try {
            emailService.sendTicketToUser(guestUser, ticket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
