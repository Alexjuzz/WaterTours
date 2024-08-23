package di.service.payment;

import di.customexceptions.user.UserNotFoundException;
import di.emailsevice.service.EmailService;
import di.model.dto.tickets.QuickTicketOrder;
import di.model.dto.tickets.ResponseTicketOrder;
import di.model.dto.tickets.TicketFactory;
import di.model.entity.telephone.Telephone;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import di.repository.user.UserRepository;
import org.springframework.stereotype.Service;

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

    private void sendTickets(User user, Map<String, Integer> tickets)  {

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
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    //todo Переписать метод для покупки билета под новый класс QUICKORDER с использованием нового наследника GuestUSer
    // Отредактировать все JPA разделить их под разные типы Register, Guest, User подумать нужен ли User ?

    public void quickPurchase(ResponseTicketOrder responseTicketOrder)  {

        Optional<User> getUserOptional = userRepository.getUserByTelephone(responseTicketOrder.getUser().getTelephone().toString());
        User guestUser;
        if (getUserOptional.isPresent()) {

            guestUser = getUserOptional.get();

            if (guestUser.getEmail().isEmpty()) {
                throw new UserNotFoundException("Пользователь с данной почтой отсутвует.");
            }

        } else {

            guestUser = new User();

            Telephone telephone = new Telephone(responseTicketOrder.getUser().getTelephone().getNumber());
            telephone.setUser(guestUser);
            guestUser.setTelephone(telephone);
            guestUser.setName(responseTicketOrder.getUser().getName());
            guestUser.setEmail(responseTicketOrder.getUser().getEmail());
            guestUser.setRole(responseTicketOrder.getUser().getRole());
            guestUser.setPassword(responseTicketOrder.getUser().getPassword());
        }

        sendTickets(guestUser, responseTicketOrder.getQuantityTickets());


        userRepository.save(guestUser);

    }
    public void quickPurchase2(QuickTicketOrder quickTicketOrder){
        GuestUser guestUser = new GuestUser();
        guestUser.setEmail(quickTicketOrder.getGuestUser().getEmail());
        guestUser.setUserTickets(quickTicketOrder.getGuestUser().getUserTickets());

    }
}
