package di.model.entity.quickTicket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("Adult")

public class QuickAdultTicket extends QuickTicket {
    {
        setPrice(1200);
    }
}
