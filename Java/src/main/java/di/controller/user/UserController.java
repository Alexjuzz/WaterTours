package di.controller.user;

import di.emailsevice.service.EmailService;
import di.model.dto.user.ResponseUser;
import di.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements iUserController {
    private final UserService service;
    private final EmailService emailService;


    @Autowired
    public UserController(UserService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<ResponseUser> createUser(ResponseUser user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @Override
    public ResponseEntity<ResponseUser> updateUser(ResponseUser user) {
        return ResponseEntity.ok(service.updateUser(user));
    }

    @Override
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @Override
    public ResponseEntity<ResponseUser> getByNumber(String number) {
        return ResponseEntity.ok(service.getUserByPhone(number));
    }

    @Override
    public ResponseEntity<String> deleteUser(String email) {
        return ResponseEntity.ok(service.deleteUser(email));
    }


    //TODO ДЕЛАТЬ ПОСЛЕ ТОГО КАК ДОДЕЛАЮ JWT сервис
    @Override
    public ResponseEntity<ResponseUser> registerUser(ResponseUser user) {
        return null;
    }
}