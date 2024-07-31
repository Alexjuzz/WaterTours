package di.controller.booking;

import di.customexceptions.boat.BoatNotFoundException;
import di.customexceptions.booking.BookingNotFoundException;
import di.customexceptions.booking.BookingReservationIsEmpty;
import di.customexceptions.seat.SeatAlreadyBookedException;
import di.customexceptions.seat.SeatNotFoundException;
import di.customexceptions.telephone.InvalidPhoneNumberException;
import di.customexceptions.telephone.TelephoneNotFoundException;
import di.enums.BookingTime;
import di.model.dto.booking.ResponseBooking;
import di.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookingController implements iBookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public ResponseEntity<List<ResponseBooking>> getBookingReservationBySeatId(Long id) {
        try {
           return  ResponseEntity.ok(bookingService.getBookingReservationBySeatId(id));
        }catch (BookingReservationIsEmpty ex){
            throw new BookingReservationIsEmpty(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBooking> setBookingToPlace(Long seatId, BookingTime bookingTime, String number) {
        try {
            return ResponseEntity.ok(bookingService.setBookingToPlace(seatId, bookingTime, number));
        } catch (TelephoneNotFoundException ex) {
            throw new TelephoneNotFoundException(ex.getMessage());
        } catch (SeatNotFoundException ex) {
            throw new SeatNotFoundException(ex.getMessage());
        } catch (SeatAlreadyBookedException ex){
            throw new SeatAlreadyBookedException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBooking> changeReservedBookingTime(Long seatId, BookingTime oldTime, BookingTime newTime) {
        return ResponseEntity.ok(bookingService.changeReservedBookingTime(seatId, oldTime, newTime));
    }

    @Override
    public ResponseEntity<String> cancelReservation(Long seatId, BookingTime bookingTime, String phoneNumber) {
        try {
                return ResponseEntity.ok("Reservation is canceling - " + bookingService.cancelReservation(seatId,bookingTime,phoneNumber));
        }catch (TelephoneNotFoundException ex){
            throw new TelephoneNotFoundException(ex.getMessage());

        }catch (InvalidPhoneNumberException ex){
            throw new InvalidPhoneNumberException(ex.getMessage());
        }
    }


}
