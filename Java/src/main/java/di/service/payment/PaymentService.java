package di.service.payment;

import di.emailsevice.service.EmailService;
import di.model.dto.tickets.ResponseTicket;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.telephone.Telephone;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
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

    public void quickPurchase(ResponseTicket responseTicket) {




        Optional<User> getUserOptional = userRepository.getUserByTelephone(responseTicket.getUser().getTelephone().toString());
        User guestUser;
        if (getUserOptional.isPresent()) {

            guestUser = getUserOptional.get();

            //TODO доделать обработку отсутвия имейла, либо сделать через смс.
            if (guestUser.getEmail().isEmpty()) {
                throw new RuntimeException();
            }

        } else {

            guestUser = new GuestUser();
            Telephone telephone = new Telephone(user.getTelephone().getNumber());
            telephone.setUser(guestUser);

            guestUser.setName(user.getName());
            guestUser.setEmail(user.getEmail());
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
