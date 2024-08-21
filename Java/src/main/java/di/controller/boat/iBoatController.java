package di.controller.boat;

import di.enums.TripType;
import di.model.dto.boats.ResponseBoat;
import di.model.entity.boats.Boat;
import di.model.entity.seats.Seat;
import jakarta.persistence.Enumerated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boat")
public interface iBoatController {

    @PostMapping("")
    public ResponseEntity<ResponseBoat> createBoat(@RequestBody Boat boat);

    @GetMapping("/all")
    ResponseEntity<List<ResponseBoat>> getAll();

    @GetMapping("/getSeats/{id}")
    ResponseEntity<List<Seat>> getSeatsById(@PathVariable("id") Long id);

    @GetMapping("/getBoatById/{id}")
    ResponseEntity<ResponseBoat> getBoatById(@PathVariable("id") Long id);

    @PostMapping("/setPlaces/{id}/{capacities}")
    ResponseEntity<ResponseBoat> setPlacesToBoat(@PathVariable("id") Long id,@PathVariable Integer capacities);

    @PostMapping("/setName/{id}/{name}")
    ResponseEntity<ResponseBoat> setNameToBoat(@PathVariable("id") Long id,@PathVariable String name);

    @PostMapping("/setTrip/{id}/{tripType}")
    ResponseEntity<ResponseBoat> setTripToBoat(@PathVariable("id") Long id, @PathVariable("tripType") TripType tripType);


}
