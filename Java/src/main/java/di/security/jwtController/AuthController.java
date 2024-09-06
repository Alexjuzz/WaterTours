package di.security.jwtController;

import di.model.dto.sign.SignInRequest;
import di.model.dto.sign.SignUpRequest;
import di.security.authService.AuthenticationService;
import lombok.RequiredArgsConstructor;
import di.security.jwt.JwtAuthenticationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        System.out.println("Received sign-up request: " + request);
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
