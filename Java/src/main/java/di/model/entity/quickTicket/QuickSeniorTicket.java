package di.model.entity.quickTicket;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Senior")
public class QuickSeniorTicket extends QuickTicket {
}
