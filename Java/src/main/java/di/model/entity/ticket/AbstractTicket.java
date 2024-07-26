package di.model.entity.ticket;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticket_type", discriminatorType = DiscriminatorType.STRING)
public  abstract  class AbstractTicket {
    private Long id;
    private double price;
    private String phoneNumber;
    private double discount;


}
