//package di.web.booking;
//
//import di.customexceptions.user.UserNotFoundException;
//import di.enums.BookingTime;
//import di.model.dto.booking.ResponseBooking;
//
//import di.model.entity.user.User;
//import di.web.user.WebUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//
//@Controller
//public class WebBookingController {
//
//    private final WebUserService service;
//    private final WebBookingService bookingService;
//
//    @Autowired
//    public WebBookingController(WebUserService service, WebBookingService bookingService) {
//        this.service = service;
//        this.bookingService = bookingService;
//    }
//
//    @GetMapping("/booking/process-booking")
//    public String processSelection(@RequestParam Long seatId, @RequestParam String time, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addAttribute("seatId", seatId);
//        redirectAttributes.addAttribute("time", time);
//
//        return "redirect:/booking/form";
//    }
//
//    @PostMapping("/booking/{seatId}")
//    public String setBookingToSeat(@RequestParam Long seatId, @RequestParam("time") BookingTime time, String name, String email, String phone, Model model) {
//       User user = service.findByPhone(phone);
//        if (user == null) {
//            throw new UserNotFoundException("user not found");
//        }
//        ResponseBooking responseBooking = bookingService.makeBooking(seatId, time, user);
//        model.addAttribute("booking", responseBooking);
//        model.addAttribute("telephone", phone);
//        model.addAttribute("time", time);
//        return "booking";
//    }
//    @GetMapping("/booking/form")
//    public String showBookingForm(@RequestParam("seatId") Long seatId, @RequestParam("time") BookingTime time, Model model) {
//        model.addAttribute("seatId", seatId);
//        model.addAttribute("time", time.toString());
//        return "makeBooking";
//    }
//
//}
