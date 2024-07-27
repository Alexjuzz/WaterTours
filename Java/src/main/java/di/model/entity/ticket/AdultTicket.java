package di.model.entity.ticket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Adult")
public class AdultTicket extends AbstractTicket {
    {
       setPrice(1200);
    }
}
