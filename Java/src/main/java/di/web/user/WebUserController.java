package di.web.user;

import di.model.dto.user.LoginUser;
import di.model.dto.user.ResponseUser;
import di.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebUserController {
    private final WebUserService service;

    @Autowired
    public WebUserController(WebUserService service, UserRepository repository) {
        this.service = service;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new ResponseUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("User") ResponseUser responseUser) {
        service.registerUser(responseUser);

        return "redirect:/boats/selection";

    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute LoginUser loginUser, Model model) {
        boolean isAuth = service.authenticate(loginUser.getUsername(),loginUser.getEmail(), loginUser.getPassword());

        if (isAuth) {
            return "booking";
        } else {
            model.addAttribute("error", "Неверное имя или пароль");
            return "login";
        }
    }
}
