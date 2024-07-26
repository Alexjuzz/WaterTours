package di.controller.booking;

import di.enums.BookingTime;
import di.model.dto.booking.ResponseBooking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO : 1) Обработать возможные исключения и методы ответа.

@RestController
@RequestMapping("/booking")
public interface iBookingController {

    @GetMapping
    @RequestMapping("/getBookingReservationBySeatId")
    ResponseEntity<List<ResponseBooking>> getBookingReservationBySeatId(@PathVariable Long id);

    @PostMapping
    @RequestMapping("/setBookingToPlace")
    ResponseEntity<ResponseBooking> setBookingToPlace(@RequestParam("seatId") Long seatId,
                                                      @RequestParam("bookingTime") BookingTime bookingTime,
                                                      @RequestParam("number") String number);

    @PostMapping
    @RequestMapping("/changeBookingTime")
    ResponseEntity<ResponseBooking> changeReservedBookingTime(@RequestParam("seatId") Long seatId,
                                                              @RequestParam("oldTime") BookingTime oldTime,
                                                              @RequestParam("newTime") BookingTime newTime
    );

    @PostMapping
    @RequestMapping("/cancelReservation")
    ResponseEntity<String> cancelReservation(@RequestParam("seatId") Long seatId,
                                             @RequestParam("bookingTime") BookingTime bookingTime,
                                             @RequestParam("phoneNumber") String phoneNumber);

}
