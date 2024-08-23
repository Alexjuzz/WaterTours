package di.controller.facadecontroller;

import di.enums.BookingTime;
import di.model.dto.boats.ResponseBoat;
import di.model.dto.booking.ResponseBooking;
import di.model.dto.user.ResponseUser;
import di.model.entity.seats.Seat;
import di.model.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
@RequestMapping("/api")
 interface iFacadecontroller {

    //region USER API REGION
    ResponseEntity<List<ResponseUser>> getAllUsers();

    ResponseEntity<ResponseUser> getUserByPhoneNumber(String number);

    ResponseEntity<ResponseUser> createUser(User user);
    //endregion

    //region BOOKING API REGION
    ResponseEntity<List<ResponseBooking>> getBookingReservationBySeatId(Long seatId);

    ResponseEntity<ResponseBooking> setBookingToPlace(Long seatId, BookingTime bookingTime, String number);

    ResponseEntity<ResponseBooking> changeReservedBookingTime(Long seatId, BookingTime oldTime, BookingTime newTime);

    ResponseEntity<String> cancelReservation(Long seatId, BookingTime bookingTime, String number);
    //endregion

    //region BOAT API REGION
    ResponseEntity<List<ResponseBoat>> getAllBoats();

    ResponseEntity<ResponseBoat> getBoatById(Long boatId);

    ResponseEntity<List<Seat>> getSeatByBoatId(Long boatId);
    //endregion


    ResponseEntity<String> checkAvailableTicket(UUID ticket);
    //endregion
}
