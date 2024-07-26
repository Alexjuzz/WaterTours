package di.model.entity.ticket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Child")
public class ChildTicket extends AbstractTicket{

}
