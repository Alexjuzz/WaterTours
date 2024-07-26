package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.telephone.Telephone;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("Regular")
public class RegularUser extends User{
    @Column(name = "email",nullable = false,length = 250)
    private String email;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Telephone telephone;

    @Column(name = "password",nullable = false)
    private String password;
}
