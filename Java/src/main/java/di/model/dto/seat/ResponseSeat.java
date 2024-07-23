package di.model.dto.seat;

import di.model.entity.boats.AbstractBoat;
import di.model.entity.booking.Booking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseSeat {
    private Long id;
    private AbstractBoat boat;
    private List<Booking> bookings = new ArrayList<>();
}
