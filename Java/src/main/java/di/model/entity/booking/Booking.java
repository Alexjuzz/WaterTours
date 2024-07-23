package di.model.entity.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import di.enums.BookingTime;
import di.model.entity.seats.Seat;
import di.model.entity.telephone.Telephone;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @JsonBackReference
    private Seat seat;
    @Enumerated(EnumType.STRING)
    private BookingTime bookingTime;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telephone_id")
    private Telephone telephone;

}
