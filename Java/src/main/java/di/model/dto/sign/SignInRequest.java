package di.model.dto.sign;

import lombok.Data;

@Data
public class SignInRequest {
    private String userName;
    private String password;
}
