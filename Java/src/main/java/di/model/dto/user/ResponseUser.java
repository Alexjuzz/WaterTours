package di.model.dto.user;

import di.enums.Role;
import di.model.entity.telephone.Telephone;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUser {
    private Long id;
    private String name;
    private String email;
    private Telephone telephone;
    private String password;
    private Role role;
}
