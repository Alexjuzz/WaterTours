package di.controller.user;

import di.customexceptions.user.UserEmptyResultDataException;
import di.customexceptions.user.UserNotFoundException;
import di.emailsevice.service.EmailService;
import di.model.dto.user.ResponseRegistryUser;
import di.model.dto.user.ResponseUser;
import di.model.entity.user.User;
import di.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController implements iUserController {
    private final UserService service;
    private final EmailService emailService;


    @Autowired
    public UserController(UserService service,EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }


    @Override
    public ResponseEntity<ResponseUser> createUser(@Valid User user) {
        return ResponseEntity.ok(service.createUser(user));
    }


    @Override
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        try {
            List<ResponseUser> responseUsers = service.getAllUsers();
            if (responseUsers.isEmpty()) {
                throw new UserEmptyResultDataException("Users not found");
            }
            return ResponseEntity.ok(responseUsers);
        } catch (UserEmptyResultDataException ex) {
            throw new UserEmptyResultDataException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseUser> getByNumber(String number) {
        try {
            ResponseUser responseUser = service.getUserByPhone(number);
            if (responseUser == null) {
                throw new UserNotFoundException("User not found");
            }
            return ResponseEntity.ok(responseUser);
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    //-------------------------------------------------------------------- 26/04/2024
    @Override
    public ResponseEntity<?> registerUser(ResponseUser user, String type) {
        if (!"BY_PHONE".equals(type) && !"BY_EMAIL".equals(type)) {
            return ResponseEntity.badRequest().body("Указан неверный тип регистрации.");
        }
        if(type.equals("BY_PHONE")){
            return ResponseEntity.ok(service.registerUser(user));
        }
        if(type.equals("BY_EMAIL")){
            String message = String.format(
                    "Здорова заебал, билеты брать будешь ??? вот твой Активационный КОД для регистрации %s. Твой номер: %s"
            , UUID.randomUUID(),user.getTelephone().getNumber());
            emailService.sendSimpleMessage(user.getEmail(), "Activation link", message);
            return ResponseEntity.ok(service.registerUser(user));
        }
        return ResponseEntity.badRequest().body("Указан неверный тип регистрации.");
    }
}