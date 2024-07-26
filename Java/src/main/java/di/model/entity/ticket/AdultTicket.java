package di.model.entity.ticket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("Adult")
public class AdultTicket extends AbstractTicket{

}
