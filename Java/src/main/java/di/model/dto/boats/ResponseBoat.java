package di.model.dto.boats;

import di.enums.TripType;
import di.model.entity.seats.Seat;
import di.model.entity.trips.Trip;
import lombok.Data;

import java.util.List;

@Data
public class ResponseBoat {

    private Long id;
    private String name;
    private TripType tripType;
    private List<Seat> places;
    private Trip trip;
}
