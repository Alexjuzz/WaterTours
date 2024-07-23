package di.model.entity.trips;


import di.enums.TripType;
import di.model.entity.boats.AbstractBoat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Абстрактный класс для будущей реализации разных видов прогулок
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "trip_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "trip")
public abstract class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NameOfTrip")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "startTime")  // Начало регистрации.
    private LocalDateTime startTime;

    /**
     * Связь экскурсии с кораблем, есть два вида, обычный и приватный
     *      в зависимости от этого будет разное бронирование
     */
    @OneToOne(fetch = FetchType.LAZY) // Связь экскурсии с кораблем, есть два вида, обычный и приватный
    @JoinColumn(name = "boatId")
    private AbstractBoat boat;
}
