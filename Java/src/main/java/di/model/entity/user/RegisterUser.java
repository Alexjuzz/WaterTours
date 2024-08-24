package di.model.entity.user;

import di.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("register_users")
public class RegisterUser extends User {
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "role", nullable = true)
    private Role role;
}