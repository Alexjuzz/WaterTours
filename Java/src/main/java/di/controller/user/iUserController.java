package di.controller.user;

import di.model.dto.user.ResponseRegistryUser;
import di.model.dto.user.ResponseUser;
import di.model.entity.user.RegularUser;
import di.model.entity.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public interface iUserController{

    @PostMapping("/createUser")
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RegularUser user);



    @GetMapping("/getAll")
    public  ResponseEntity<List<ResponseUser>> getAllUsers();

    @GetMapping("/getByPhone/{number}")
    public ResponseEntity<ResponseUser> getByNumber(@PathVariable("number") String number);

    /**
     TODO:
     - Переделать регистрацию без телефона.
     - и с телефоном
     - добавить Second Name ?

     **/

    //------------------------------------------------
    @PostMapping("/register")
    public  ResponseEntity<?> registerUser(@Valid @RequestBody ResponseUser registerUser,@RequestParam String type);
}
