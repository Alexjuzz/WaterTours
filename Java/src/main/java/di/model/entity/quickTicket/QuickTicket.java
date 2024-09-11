package di.model.entity.quickTicket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name  = "quickTickets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticket_type", discriminatorType = DiscriminatorType.STRING)
@RequiredArgsConstructor
public abstract  class QuickTicket {
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
    @Getter
    @Column(updatable = false, nullable = false)
    private UUID uniqueTicketId ;

    @ManyToOne
    @JoinColumn(name = "quick_purchase_id")
    @JsonBackReference
    private QuickPurchase quickPurchase;  // Связь с быстрой покупкой

    @PrePersist
    public void onCreate() {
        dateStamp = new SimpleDateFormat("dd MM yyyy, HH:mm:ss").format(new java.util.Date());
        if (this.uniqueTicketId == null) {
            this.uniqueTicketId = UUID.randomUUID();
        }
    }
    public boolean changeAvailableTicket(){
        if(isValid()){
            valid = false;
            return true;
        }
        return false;
    }
}
