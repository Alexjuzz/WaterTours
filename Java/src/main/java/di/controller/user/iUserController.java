package di.controller.user;

import di.model.dto.user.ResponseRegistryUser;
import di.model.dto.user.ResponseUser;
import di.model.entity.user.GuestUser;
//import di.model.entity.user.RegularUser;
import di.model.entity.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface iUserController{


    @PostMapping("/createUser")
    ResponseEntity<ResponseUser> createUser(@RequestBody ResponseUser user);

    @PostMapping("/updateUser")
    ResponseEntity<ResponseUser> updateUser(@RequestBody ResponseUser user);

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@Valid @RequestBody ResponseUser registerUser);

    @GetMapping("/getAll")
    ResponseEntity<List<ResponseUser>> getAllUsers();

    @GetMapping("/getByPhone/{number}")
    ResponseEntity<ResponseUser> getByNumber(@PathVariable("number") String number);

    @DeleteMapping
    ResponseEntity<String> deleteUser(@RequestBody String email);

}
