package di.web.boat;

import di.enums.BookingTime;
import di.model.dto.boats.ResponseBoat;

import di.model.dto.seat.ResponseSeat;
import di.web.booking.WebBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebBoatController {

    private final WebBoatService service;
    private final WebBookingService bookingService;
    @Autowired
    public WebBoatController(WebBoatService service, WebBookingService bookingService) {
        this.bookingService = bookingService;
        this.service = service;
    }


    @GetMapping("/boats/selection")
    public String showSelectionForm(Model model) {
        model.addAttribute("boats", service.getAllBoats());
        return "select-boat";
    }
    @GetMapping("/boats/process-selection")
    public String   processSelection (@RequestParam Long boatId,@RequestParam String time, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("boatId", boatId);
        redirectAttributes.addAttribute("time", time);
        return "redirect:/boats/details/{boatId}";
    }
//    @GetMapping("/boats/details/{boatId}")
//    public String showBoatDetails(@PathVariable Long boatId, @RequestParam(required = false) String time, Model model) {
//        ResponseBoat boat = service.getBoatById(boatId);
//
//        BookingTime getTime = BookingTime.fromString(time);
//        model.addAttribute("boat", boat);
//        model.addAttribute("avalible", bookingService.getAvailableSeats(boatId,time) );
//        if (time != null) {
//            model.addAttribute("selectedTime", getTime);
//        }
//        return "boat";
//    }
@GetMapping("/boats/details/{boatId}")
public String showBoatDetails(@PathVariable Long boatId,
                              @RequestParam(required = false) String time,
                              Model model) {
    ResponseBoat boat = service.getBoatById(boatId);
    List<ResponseSeat> availableSeats = bookingService.getAvailableSeats(boatId, time);

    model.addAttribute("boat", boat);
    model.addAttribute("availableSeats", availableSeats); // Исправлено на availableSeats
    if (time != null) {
        model.addAttribute("selectedTime", BookingTime.fromString(time));
    }
    return "boat";
}




}
