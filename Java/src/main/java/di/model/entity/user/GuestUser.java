package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.telephone.Telephone;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("Guest")
public class GuestUser extends User {
    @Column(name = "email", nullable = true, length = 250)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Telephone telephone;
}
