package di.model.entity.quickTicket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Child")
public class QuickChildTicket extends QuickTicket{
    {
        setPrice(900);
    }

}
