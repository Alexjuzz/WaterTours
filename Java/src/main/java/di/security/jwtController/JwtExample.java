package di.security.jwtController;

import di.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class JwtExample {
    private final UserService userService;

    @GetMapping
    public String example(){
        return "Hello world";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String exampleAdm(){
        return "Hello admin";
    }

    @GetMapping("/get-admin")
    public void getAdmin(){
        userService.getAdmin();
    }
}
