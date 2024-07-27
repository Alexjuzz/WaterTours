package di.model.entity.ticket;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticket_type", discriminatorType = DiscriminatorType.STRING)

public abstract class AbstractTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double price;
    @Column
    private boolean valid = true;
    @Column
    private String phoneNumber;
    @Column
    private double discount;
    @Column(updatable = false)
    private String dateStamp;
    @Column(updatable = false, nullable = false)
    private final UUID uniqueTicketId = UUID.randomUUID();

    @PrePersist
    protected void onCreate() {
        dateStamp = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss").format(new java.util.Date());
    }
}
