package di.model.entity.seats;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.enums.BookingTime;
import di.model.entity.boats.AbstractBoat;
import di.model.entity.boats.Boat;
import di.model.entity.booking.Booking;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс места
 */
@Entity
@Table(name = "seat")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seatNumber") // позиция места
    private Integer seatNumber;

    @ManyToOne                          // Ссылка на корабль
    @JoinColumn(name = "boatId", nullable = false)
    @JsonBackReference
    private AbstractBoat boat;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Booking> bookings = new ArrayList<>();
}

