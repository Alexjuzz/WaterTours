package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.telephone.Telephone;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//TODO : Подумать о Security - На счет USERDETAILS .
@Data
@Entity
@Table(name = "users")
public  class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false,length = 40)
    private String name;

    @Column(name = "email",nullable = false,length = 250)
    private String email;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Telephone telephone;

    @Column(name = "password",nullable = false)
    private String password;


}
