package di.security.authService;

import di.enums.Role;
import di.model.dto.sign.SignInRequest;
import di.model.dto.sign.SignUpRequest;
import di.model.entity.user.RegisterUser;
import di.security.jwt.JwtService;
import di.service.user.UserService;
import di.security.jwt.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = new RegisterUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);

        userService.create(user);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(),
                request.getPassword()));

        var user = userService.userDetailsService().loadUserByUsername(request.getUserName());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
