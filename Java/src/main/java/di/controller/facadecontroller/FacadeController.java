package di.controller.facadecontroller;


import di.enums.BookingTime;
import di.model.dto.boats.ResponseBoat;
import di.model.dto.booking.ResponseBooking;
import di.model.dto.seat.ResponseSeat;
import di.model.dto.user.ResponseUser;
import di.model.entity.seats.Seat;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.user.GuestUser;
import di.model.entity.user.RegularUser;
import di.model.entity.user.User;
import di.service.boat.BoatService;
import di.service.booking.BookingService;
import di.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FacadeController {
    private final BookingService bookingService;
    private final UserService userService;
    private final BoatService boatService;

    @Autowired

    public FacadeController(BookingService bookingService, UserService userService,BoatService boatService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.boatService = boatService;
    }

    //region USER API REGION
    @GetMapping
    @RequestMapping("/users")
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    @RequestMapping("/user/{phoneNumber}")
    public ResponseEntity<ResponseUser> getUserByPhoneNumber(@PathVariable("phoneNumber") String number) {
        return ResponseEntity.ok(userService.getUserByPhone(number));
    }

    @PostMapping("/user/create")
    public ResponseEntity<ResponseUser> createUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.createUser(user));
    return null;
    }

    //endregion

    //region BOOKING API REGION
    @GetMapping("/book/{seatId}")
    public ResponseEntity<List<ResponseBooking>> getBookingReservationBySeatId(@PathVariable Long seatId) {
        return ResponseEntity.ok(bookingService.getBookingReservationBySeatId(seatId));
    }

    @PostMapping("/book/set/book-place")
    public ResponseEntity<ResponseBooking> setBookingToPlace(@RequestParam("seatId") Long seatId,
                                                             @RequestParam("bookingTime") BookingTime bookingTime,
                                                             @RequestParam("number") String number) {
        return ResponseEntity.ok(bookingService.setBookingToPlace(seatId, bookingTime, number));
    }

    @PostMapping("/book/change/time")
    public ResponseEntity<ResponseBooking> changeReservedBookingTime(@RequestParam("seatId") Long seatId,
                                                                     @RequestParam("oldTime") BookingTime oldTime,
                                                                     @RequestParam("newTime") BookingTime newTime){
        return ResponseEntity.ok(bookingService.changeReservedBookingTime(seatId,oldTime,newTime));
    }
    @PostMapping("/book/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam("seatId") Long seatId,
                                                    @RequestParam("bookingTime")BookingTime bookingTime,
                                                    @RequestParam("number") String number){
        return ResponseEntity.ok("Reservation is canceling - " + bookingService.cancelReservation(seatId,bookingTime,number));
    }
    //endregion

    //region BOAT API REGION
    @GetMapping("/boats")
    public ResponseEntity<List<ResponseBoat>> getAllBoats(){
        return ResponseEntity.ok(boatService.getAllBoats());
    }
    @GetMapping("/boat/{id}")
    public ResponseEntity<ResponseBoat> getBoatById(@PathVariable Long boatId){
        return ResponseEntity.ok(boatService.getBoatById(boatId));
    }

    @GetMapping("/boat/seats/{boatId}")
    public ResponseEntity<List<Seat>> getSeatByBoatId(@PathVariable Long boatId){
        return ResponseEntity.ok(boatService.getSeatsByBoatId(boatId));
    }
    //endregion

    //region Fast reservation API REGION
        @PostMapping("/purchase/quick")
        public  ResponseEntity<String> quickPurchase(@RequestBody GuestUser user, @RequestParam String typeTicket){
            userService.quickPurchase(user,typeTicket);
        return ResponseEntity.ok("Successful buy");
        }
    //endregion
}

