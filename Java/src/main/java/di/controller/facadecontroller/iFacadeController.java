package di.controller.facadecontroller;

import di.enums.BookingTime;
import di.model.dto.boats.ResponseBoat;
import di.model.dto.booking.ResponseBooking;
import di.model.dto.user.ResponseUser;
import di.model.entity.seats.Seat;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface iFacadeController {

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

    //region Fast reservation API REGION
    ResponseEntity<String> quickPurchase(GuestUser user, String typeTicket);

    ResponseEntity<String> checkAvailableTicket(UUID ticket);
    //endregion
}
