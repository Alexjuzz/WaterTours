package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.telephone.Telephone;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("Regular")
public class RegularUser extends User {

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
}
