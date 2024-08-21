package di.service.user;

import di.customexceptions.email.EmailAlreadyUsedException;
import di.customexceptions.telephone.TelephoneAlreadyExistException;
import di.customexceptions.user.UserEmptyResultDataException;
import di.customexceptions.user.UserNotFoundException;
import di.emailsevice.service.EmailService;
import di.model.dto.user.ResponseUser;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import di.model.entity.telephone.Telephone;
import di.repository.ticket.TicketRepository;
import di.repository.telephone.TelephoneRepository;
import di.repository.user.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository repository;
    private final TelephoneRepository telephoneRepository;
    private final EmailService emailService;
    private final TicketRepository ticketRepository;
    private Validator validator;

    @Autowired
    public UserService(UserRepository repository, TelephoneRepository telephoneRepository, EmailService emailService, TicketRepository ticketRepository) {
        this.repository = repository;
        this.telephoneRepository = telephoneRepository;
        this.emailService = emailService;
        this.ticketRepository = ticketRepository;
    }


    // region  USER
    public ResponseUser createUser(ResponseUser user) {
        //TODO доделать validator
        Set<ConstraintViolation<Telephone>> validation = validator.validate(user.getTelephone());
        if (!validation.isEmpty()) {
            throw new ConstraintViolationException(validation);
        }
        if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
            throw new TelephoneAlreadyExistException("Telephone number already exist");
        }
        User requestUser = new User();
        requestUser.setName(user.getName());
        requestUser.setEmail(user.getEmail());
        requestUser.setPassword(user.getPassword());
        requestUser.setRole(user.getRole());
        Telephone telephone = createAndLinkTelephone(user.getTelephone().getNumber(), requestUser);
        requestUser.setTelephone(telephone);

        return convertUserToResponseUser(repository.save(requestUser));
    }

    public List<ResponseUser> getAllUsers() {
        try {
            List<ResponseUser> responseUsers = repository.findAll().stream().map(this::convertUserToResponseUser).toList();
            if (responseUsers.isEmpty()) {
                throw new UserEmptyResultDataException("Users not found");
            }
            return responseUsers;
        } catch (UserEmptyResultDataException ex) {
            throw new UserEmptyResultDataException(ex.getMessage());
        }
    }

    public ResponseUser getUserByPhone(String number) {
        Optional<User> getUser = repository.getUserByTelephone(number);
        if (getUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с номером %s не найден", number));
        }
        return convertUserToResponseUser(getUser.get());

    }

    private ResponseUser convertUserToResponseUser(User user) {
        ResponseUser response = new ResponseUser();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setTelephone(user.getTelephone());
        response.setRole(user.getRole());

        return response;
    }


    //region 26/04/2024

    /**
     * Регистрирует нового пользователя, проверяя, что электронная почта и телефонный номер еще не использовались.
     *
     * @param user Данные пользователя для регистрации.
     * @return ResponseRegistryUser Данные зарегистрированного пользователя.
     * @throws EmailAlreadyUsedException      если указанный email уже используется.
     * @throws TelephoneAlreadyExistException если указанный телефонный номер уже зарегистрирован.
     */


    public boolean checkValidData(ResponseUser user) {
        if (repository.getUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Email already used");
        }
        if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
            throw new TelephoneAlreadyExistException("Telephone number already exist");
        }
        return true;
    }
// endregion

    //region GUEST USER
    public ResponseUser createGuestUser(GuestUser user) {
        //TODO доделать validator
        Set<ConstraintViolation<Telephone>> validation = validator.validate(user.getTelephone());
        if (!validation.isEmpty()) {
            throw new ConstraintViolationException(validation);
        }

        if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
            throw new TelephoneAlreadyExistException("Telephone number already exist");
        }

        GuestUser requestUser = new GuestUser();
        requestUser.setName(user.getName());
        requestUser.setEmail(user.getEmail());

        Telephone telephone = createAndLinkTelephone(user.getTelephone().getNumber(), requestUser);
        requestUser.setTelephone(telephone);
        return convertGuestToResponseUser(repository.save(requestUser));
    }

    private ResponseUser convertGuestToResponseUser(GuestUser user) {
        ResponseUser response = new ResponseUser();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setTelephone(user.getTelephone());
        return response;
    }
    //endregion


    //region Add telephone to user
    private Telephone createAndLinkTelephone(String number, User user) {
        Telephone telephone = new Telephone(number);
        telephone.setUser(user);
        return telephone;
    }

    //endregion
    //TODO добвить проверку билетов через repository.
    //region Quick Purchase
    public void quickPurchase(User user, String typeTicket) {
        AbstractTicket ticket;
        if (typeTicket.equals("Adult")) {
            ticket = new AdultTicket();
        } else if (typeTicket.equals("Child")) {
            ticket = new ChildTicket();
        } else if (typeTicket.equals("Senior")) {
            ticket = new SeniorTicket();
        } else {
            //TODO Доделать обработку не правльного выбора.
            throw new RuntimeException();
        }
        Optional<User> getUserOptional = repository.getUserByTelephone(user.getTelephone().toString());
        User guestUser;
        if (getUserOptional.isPresent()) {

            guestUser = getUserOptional.get();

            //TODO доделать обработку отсутвия имейла, либо сделать через смс.
            if (guestUser.getEmail().isEmpty()) {
                throw new RuntimeException();
            }

        } else {

            guestUser = new GuestUser();
            guestUser.setTelephone(createAndLinkTelephone(user.getTelephone().getNumber(), guestUser));
            guestUser.setName(user.getName());
            guestUser.setEmail(user.getEmail());
        }
        guestUser.getUserTickets().add(ticket);
        ticket.setUser(guestUser);

        repository.save(guestUser);
        try {
            emailService.sendTicketToUser(guestUser, ticket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkAvailableTicket(UUID ticket) {
        Optional<AbstractTicket> responseTicket = ticketRepository.getTicketByUniqueCode(ticket);
        if (responseTicket.isPresent()) {
            AbstractTicket ticketObj = responseTicket.get();
            if (ticketObj.isValid()) {
                ticketObj.changeAvailableTicket();
                ticketRepository.save(ticketObj);
                return true;
            }
        }
        return false;
    }

    public ResponseUser updateUser(ResponseUser user) {
        Optional<User> getUser = repository.getUserByEmail(user.getEmail());
        if (getUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с почтой %s не найден", user.getEmail()));
        }
        User updateUser = getUser.get();
        updateUser.setRole(user.getRole());
        updateUser.setName(user.getName());
        updateUser.setTelephone(createAndLinkTelephone(user.getTelephone().getNumber(), updateUser));
        repository.save(updateUser);
        return convertUserToResponseUser(updateUser);
    }

    public String deleteUser(String email) {
            Optional<User>  getUser = repository.getUserByEmail(email);
            if(getUser.isEmpty()){
                throw new UserNotFoundException(String.format("Пользователь с почтой %s не найден", email));
            }
            repository.delete(getUser.get());
            return "Пользователь успешно удален.";
    }

    //endregion
}
