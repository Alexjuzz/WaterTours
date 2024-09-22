package di.model.entity.telephone;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.quickTicket.QuickPurchase;
import di.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Проверяет корректность формата телефонного номера в России.
     *
     * Форматы, которые поддерживаются:
     * - 8xxxxxxxxxx
     * - +7xxxxxxxxxx
     * - xxx-xxx-xxxx
     * - xxx xxx xxxx
     * - (xxx)xxx-xxxx
     * - (xxx) xxx-xxxx
     *
     */
    @Pattern(regexp="^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "The number invalid")
    private String number;

    public Telephone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return  number;
    }
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "telephone")
    @JsonBackReference
    private QuickPurchase quickPurchase;

}
