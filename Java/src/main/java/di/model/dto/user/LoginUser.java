package di.model.dto.user;

import lombok.Data;

@Data
public class LoginUser {
    private String username;
    private String password;
    private String email;
}
