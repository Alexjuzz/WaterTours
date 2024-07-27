package di.model.entity.ticket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import di.model.entity.user.GuestUser;
import di.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.UUID;
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticket_type", discriminatorType = DiscriminatorType.STRING)
@RequiredArgsConstructor
public abstract class AbstractTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column

    private double price = 1500;
    @Column

    private boolean valid = true;
    @Column

    private double discount = 0;
    @Column(updatable = false)
    private String dateStamp;
    @Column(updatable = false, nullable = false)
    private  UUID uniqueTicketId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn( name = "userId", nullable = false)
    @JsonBackReference
      private GuestUser user;

    @PrePersist
    //TODO Убрать публичность и сделать наследование protected.
    public void onCreate() {
        dateStamp = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss").format(new java.util.Date());
        if (this.uniqueTicketId == null) {
            this.uniqueTicketId = UUID.randomUUID();
        }
    }
}
