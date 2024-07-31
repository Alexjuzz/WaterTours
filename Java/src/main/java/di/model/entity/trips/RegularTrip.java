package di.model.entity.trips;

import di.model.interfaces.InterfaceTrip;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
@DiscriminatorValue("regular")
public class RegularTrip extends  Trip implements InterfaceTrip {

    private  String name = "Regular trip";


}
