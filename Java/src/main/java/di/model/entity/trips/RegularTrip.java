package di.model.entity.trips;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
@DiscriminatorValue("regular")
public class RegularTrip extends  Trip  {

    private  String name = "Regular trip";


}
