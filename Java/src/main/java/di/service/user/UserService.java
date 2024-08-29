package di.service.user;

import di.customexceptions.email.EmailAlreadyUsedException;
import di.customexceptions.telephone.TelephoneAlreadyExistException;
import di.customexceptions.user.UserEmptyResultDataException;
import di.customexceptions.user.UserNotFoundException;
import di.enums.Role;
import di.model.dto.user.ResponseUser;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.RegisterUser;
import di.model.entity.user.User;
import di.model.entity.telephone.Telephone;
import di.repository.ticket.TicketRepository;
import di.repository.telephone.TelephoneRepository;
import di.repository.user.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository repository;
    private final TelephoneRepository telephoneRepository;
    private final TicketRepository ticketRepository;
    private Validator validator;

    @Autowired
    public UserService(UserRepository repository, TelephoneRepository telephoneRepository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.telephoneRepository = telephoneRepository;
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
        RegisterUser requestUser = new RegisterUser();
        requestUser.setName(user.getName());
        requestUser.setEmail(user.getEmail());
        requestUser.setPassword(user.getPassword());
        requestUser.setRole(user.getRole());
        Telephone telephone = addTelephoneToUser(user.getTelephone().getNumber(), requestUser);
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
        Optional<RegisterUser> getUser = repository.getRegistryUserByPhone((number));
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
        response.setTelephone(user.getTelephone());


        if(user instanceof RegisterUser registerUser) {
            response.setRole(registerUser.getRole());
            response.setPassword(registerUser.getPassword());
        }

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
    public ResponseUser createGuestUser(User user) {
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

        Telephone telephone = addTelephoneToUser(user.getTelephone().getNumber(), requestUser);
        requestUser.setTelephone(telephone);
        return convertUserToResponseUser(repository.save(requestUser));
    }

    private ResponseUser convertGuestToResponseUser(User user) {
        ResponseUser response = new ResponseUser();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setTelephone(user.getTelephone());
        return response;
    }
    //endregion


    //region Add telephone to user
    private Telephone addTelephoneToUser(String number, User user) {
        Telephone telephone = new Telephone(number);
        telephone.setUser(user);
        return telephone;
    }

    //endregion
    //TODO добвить проверку билетов через repository.
    //region Quick Purchase

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
        updateUser.setName(user.getName());
        updateUser.setTelephone(addTelephoneToUser(user.getTelephone().getNumber(), updateUser));
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

    ///////
    public User getByUsername(String username) {
        return repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public RegisterUser getRegisterUser(String name){
        return  repository.findByNameRegisterUser(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getRegisterUser;
    }
    public RegisterUser getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getRegisterUser(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        repository.save(user);
    }

}
