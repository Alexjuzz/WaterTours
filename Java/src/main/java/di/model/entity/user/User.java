package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.enums.Role;
import di.model.entity.telephone.Telephone;
import di.model.entity.ticket.AbstractTicket;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//TODO : Подумать о Security - На счет USERDETAILS .
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AbstractTicket> userTickets = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Telephone telephone;
    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private Role role;
}
