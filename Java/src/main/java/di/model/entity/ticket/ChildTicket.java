package di.model.entity.ticket;

import di.model.entity.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Child")
public class ChildTicket extends AbstractTicket{
    {
        setPrice(900);
    }

}
