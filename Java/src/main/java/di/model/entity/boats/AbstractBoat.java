package di.model.entity.boats;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.enums.TripType;
import di.model.entity.seats.Seat;
import di.model.entity.trips.Trip;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Абстрактный класс кораблей.
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "boat_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "boat")
public class AbstractBoat  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "boat_name")
    private String name;

    @Column(name = "trip_type")
    @Enumerated(EnumType.STRING)
    private TripType tripType;

    /**
     * Места - у каждого корабля есть свои места(сущность место) которые хранятся в листе
     *
     */
    @OneToMany(mappedBy = "boat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Column(name = "places")
    private List<Seat> places = new ArrayList<>();

    /**
     * Тур - у каждого судна может быть только один выбраный в текущий момент тур.
     * Но при не обходимости его можео будет поменять.
     * В зависимсотри какой тур выбран будет создан нужный обьект тура и будет выбрана нужная логика записи.
     */

    @OneToOne(mappedBy = "boat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trip trip;



    /**
     * Конструктор без параметров, сделан для того чтобы сразу создать свободные места и записать
     * их в лист places. Также проинициализировать поля seat's.
     */

    public AbstractBoat() {
    }

    protected AbstractBoat(String name, int capacity) {
    }
    protected List<Seat> addSeatToPlaces(Seat seat) {
        if (seat.getId() != null && this.places.stream().anyMatch(s -> s.getId().equals(seat.getId()))) {
            System.out.printf("Seat with ID {%s} already exists", seat.getId());
        } else {
            System.out.printf("Adding seat with ID {%s}", seat.getId());
            this.places.add(seat);
        }
        return this.places;
    }


}




