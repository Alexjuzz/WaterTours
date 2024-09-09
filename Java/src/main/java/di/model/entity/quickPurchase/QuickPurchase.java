package di.model.entity.quickPurchase;

import di.model.entity.ticket.AbstractTicket;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name  = "quickTickets")
public class QuickPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String email;

    @OneToMany(mappedBy = "quickPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AbstractTicket> abstractTicketList = new ArrayList<>();
}
