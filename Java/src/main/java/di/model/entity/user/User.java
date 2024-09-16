package di.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.enums.Role;
import di.model.entity.telephone.Telephone;
import di.model.entity.ticket.AbstractTicket;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//TODO
// Обработать нормальную регистрацию, переделать JPA!
// создать таблицу для хранения почты + уникальный UUID, либо сделать номер телефона + UUID (Возможно написать методы для удаления с
// базы данных отработанных билетов, также добавить удаление старых билетов.
// Настроить requestMatchers - endpoint-ы, да адекватного доступа.
// Подумать о поле логин и имя. - возможно нужно убрать имя в методах формирования писем, а использовать только почту.
// Подумать о странице пользователя с данными ... - явно не скоро.
// По возможности перейти к написанию тестов.
// Возможно убрать класс USER  - сделать абстрактным.


@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public  class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "surname",nullable = true,length = 40)
    private String surname;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AbstractTicket> userTickets = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Telephone telephone;
    @Column(name = "email", nullable = false, length = 250)
    private String email;

}
