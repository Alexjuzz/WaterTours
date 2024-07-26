package di.model.entity.ticket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Senior")
public class SeniorTicket extends AbstractTicket {
}
