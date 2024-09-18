package di.security.authService;

import di.enums.Role;
import di.model.dto.sign.SignInRequest;
import di.model.dto.sign.SignUpRequest;
import di.model.entity.user.RegisterUser;
import di.security.jwt.JwtService;
import di.service.user.UserService;
import di.security.jwt.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO
// Рассмотреть регистрацию пользователя, авторизацию привести эти методы в порядок.
// Проверить работу этих методов, потом переходить к тестам  работы с  базой.

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        userService.checkEmail(request.getEmail());
        var user = new RegisterUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
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
     * Изменена логика > loadByUserName а передаем параметр вместо имени пользователя(или логина) имейл - сделано для точной уникальности.
     */
    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        var user = userService.userDetailsService().loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}