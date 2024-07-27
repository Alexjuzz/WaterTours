package di.model.entity.ticket;

import di.model.entity.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Senior")
public class SeniorTicket extends AbstractTicket {

}
