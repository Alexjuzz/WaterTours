package di.controller.boat;


import di.customexceptions.boat.BoatEmptyResultDataException;
import di.customexceptions.boat.BoatNotFoundException;
import di.customexceptions.boat.BoatValidCapacityException;
import di.customexceptions.seat.SeatsWithBoatIsEmpty;
import di.enums.TripType;
import di.model.dto.boats.ResponseBoat;
import di.model.entity.boats.Boat;
import di.model.entity.seats.Seat;
import di.service.boat.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class BoatController implements iBoatController {
    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }


    @Override
    public ResponseEntity<ResponseBoat> createBoat(Boat boat) {
        return ResponseEntity.ok(boatService.createBoat(boat));
    }

    @Override
    public ResponseEntity<List<ResponseBoat>> getAll() {

        try {
            List<ResponseBoat> boats = boatService.getAllBoats();
            if (boats == null || boats.isEmpty()) {
                throw new BoatEmptyResultDataException("No boats found");
            }
            return ResponseEntity.ok(boatService.getAllBoats());
        } catch (BoatEmptyResultDataException ex) {
          throw  new BoatEmptyResultDataException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Seat>> getSeatsById(Long id) {
        try {
            List<Seat> seatList = boatService.getSeatsByBoatId(id);
            return ResponseEntity.ok(seatList);
        }catch (SeatsWithBoatIsEmpty ex){
            throw  new SeatsWithBoatIsEmpty(ex.getMessage());
        }catch (BoatNotFoundException ex){
            throw  new BoatNotFoundException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBoat> getBoatById(Long id) {
        try {
            return ResponseEntity.ok(boatService.getBoatById(id));
        }catch (BoatNotFoundException ex){
            throw  new BoatNotFoundException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBoat> setPlacesToBoat(Long id, Integer capacities) {
        try {
            ResponseBoat responseBoat = boatService.setPlacesToBoat(id,capacities);
            return ResponseEntity.ok(responseBoat);
        }catch (BoatNotFoundException ex){
            throw new BoatNotFoundException(ex.getMessage());
        }catch (BoatValidCapacityException ex){
            throw  new BoatValidCapacityException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBoat> setNameToBoat(Long id, String name) {
        try {
            return ResponseEntity.ok(boatService.setNameToBoat(id, name));
        }catch (BoatNotFoundException ex){
            throw new BoatNotFoundException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseBoat> setTripToBoat(Long id, TripType tripType) {
        return ResponseEntity.ok(boatService.setTripBoat(id, tripType));
    }


}
