package di.service.user;


import di.customexceptions.email.EmailAlreadyUsedException;
import di.customexceptions.telephone.TelephoneAlreadyExistException;
import di.emailsevice.service.EmailService;
import di.model.dto.user.ResponseRegistryUser;
import di.model.dto.user.ResponseUser;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.RegularUser;
import di.model.entity.user.User;
import di.model.entity.telephone.Telephone;
import di.repository.TicketRepository;
import di.repository.telephone.TelephoneRepository;
import di.repository.user.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Transactional
@Service
public class UserService {

    private final UserRepository repository;
    private final TelephoneRepository telephoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TicketRepository ticketRepository;

    @Autowired
    private Validator validator;

    @Autowired
    public UserService(UserRepository repository, TelephoneRepository telephoneRepository, PasswordEncoder passwordEncoder, EmailService emailService, TicketRepository ticketRepository) {
        this.repository = repository;
        this.telephoneRepository = telephoneRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.ticketRepository = ticketRepository;
    }


    // region REGULAR USER
    public ResponseUser createRegularUser(RegularUser user) {
        //TODO доделать validator
        Set<ConstraintViolation<Telephone>> validation = validator.validate(user.getTelephone());
        if (!validation.isEmpty()) {
            throw new ConstraintViolationException(validation);
        }

        if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
            throw new TelephoneAlreadyExistException("Telephone number already exist");
        }
       RegularUser requestUser = new RegularUser();
        requestUser.setName(user.getName());
        requestUser.setEmail(user.getEmail());
        requestUser.setPassword(user.getPassword());

        Telephone telephone = createAndLinkTelephone(user.getTelephone().getNumber(),requestUser);
        requestUser.setTelephone(telephone);

        return convertUserToResponseUser(repository.save(requestUser));
    }

    public List<ResponseUser> getAllUsers() {
        return repository.findAll().stream().map(this::convertUserToResponseUser).toList();
    }

    public ResponseUser getUserByPhone(String number) {
        RegularUser user = repository.getByTelephone(number);
        return convertUserToResponseUser(user);
    }



    private ResponseUser convertUserToResponseUser(User user) {
        ResponseUser response = new ResponseUser();
        response.setId(user.getId());
        response.setName(user.getName());
        if(user instanceof  RegularUser){
            response.setEmail(((RegularUser) user).getEmail());
            response.setPassword(((RegularUser) user).getPassword());
            response.setTelephone(((RegularUser) user).getTelephone());
        }
        if(user instanceof GuestUser){
            response.setEmail(((GuestUser) user).getEmail());
            response.setTelephone(((GuestUser) user).getTelephone());

        }
        return response;
    }

    private ResponseRegistryUser convertUserToResponseRegistryUser(RegularUser user) {
        ResponseRegistryUser responseUser = new ResponseRegistryUser();
        responseUser.setEmail(user.getEmail());
        responseUser.setName(user.getName());
        responseUser.setId(user.getId());
        responseUser.setTelephone(user.getTelephone().getNumber());
        return responseUser;
    }
    //endregion

    //region 26/04/2024

    /**
     * Регистрирует нового пользователя, проверяя, что электронная почта и телефонный номер еще не использовались.
     *
     * @param user Данные пользователя для регистрации.
     * @return ResponseRegistryUser Данные зарегистрированного пользователя.
     * @throws EmailAlreadyUsedException      если указанный email уже используется.
     * @throws TelephoneAlreadyExistException если указанный телефонный номер уже зарегистрирован.
     */

    public ResponseRegistryUser registerUser(ResponseUser user) {
        checkValidData(user);
        RegularUser registerUser = new RegularUser();
        registerUser.setEmail(user.getEmail());
        registerUser.setName(user.getName());
        registerUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Telephone telephone = new Telephone(user.getTelephone().getNumber());
        telephone.setUser(registerUser);

        registerUser.setTelephone(telephone);
        return convertUserToResponseRegistryUser(repository.save(registerUser));
    }



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

        Telephone telephone = createAndLinkTelephone(user.getTelephone().getNumber(),requestUser);
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
    private ResponseRegistryUser convertGuestToResponseRegistryUser(GuestUser user) {
        ResponseRegistryUser responseUser = new ResponseRegistryUser();
        responseUser.setEmail(user.getEmail());
        responseUser.setName(user.getName());
        responseUser.setId(user.getId());
        responseUser.setTelephone(user.getTelephone().getNumber());
        return responseUser;
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
    public void quickPurchase(GuestUser user, String typeTicket) {
        AbstractTicket ticket;
        if(typeTicket.equals("Adult")){
             ticket = new AdultTicket();
        }else if(typeTicket.equals("Child")){
             ticket = new ChildTicket();
        }else if(typeTicket.equals("Senior")){
             ticket = new SeniorTicket();
        }else {
            //TODO Доделать обработку не правльного выбора.
            throw  new RuntimeException();
        }
        Optional<GuestUser> getUserOptional =   repository.getGuestUserByTelephone(user.getTelephone().toString());
        GuestUser guestUser;
        if(getUserOptional.isPresent()){

            guestUser = getUserOptional.get();

            //TODO доделать обработку отсутвия имейла, либо сделать через смс.
            if(guestUser.getEmail().isEmpty()){
                throw  new RuntimeException();
            }

        }else {

            guestUser = new GuestUser();
            guestUser.setTelephone(createAndLinkTelephone(user.getTelephone().getNumber(),guestUser));
            guestUser.setName(user.getName());
            guestUser.setEmail(user.getEmail());
        }
        guestUser.getUserTickets().add(ticket);
        ticket.setUser(guestUser);

        repository.save(guestUser);
        try {
            emailService.sendTicketToUser(guestUser,ticket);
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

    //endregion
}
