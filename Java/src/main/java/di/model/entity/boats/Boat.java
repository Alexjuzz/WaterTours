package di.model.entity.boats;

import di.enums.TripType;

import di.model.entity.seats.Seat;
import di.model.entity.trips.Trip;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BoatLadoga")
public class Boat extends AbstractBoat {


    @Override
    public TripType getTripType() {
        return super.getTripType();
    }

    @Override
    public void setTripType(TripType tripType) {
        super.setTripType(tripType);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public List<Seat> getPlaces() {
        return super.getPlaces();
    }


    @Override
    public Trip getTrip() {
        return super.getTrip();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setTrip(Trip trip) {
        super.setTrip(trip);
    }


    public Boat(){
        super();
    }
}
