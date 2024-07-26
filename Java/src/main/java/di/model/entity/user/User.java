package di.model.entity.user;

import jakarta.persistence.*;
import lombok.Data;


//TODO : Подумать о Security - На счет USERDETAILS .
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public abstract class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false,length = 40)
    private String name;

}
