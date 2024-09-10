package di.model.entity.quickTicket;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "quick_purchase")
@Data
public class QuickPurchase {
    @Id
    @Column
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name =  "quick_purchase_id")
    private List<QuickTicket> ticketList;
}
