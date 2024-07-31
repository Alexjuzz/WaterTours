package di.model.dto.booking;


import di.enums.BookingTime;
import di.model.entity.seats.Seat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseBooking {

    private  Long id;
    private Seat seat;
    private BookingTime bookingTime;
    private LocalDate date;

}