package di.model.entity.quickTicket;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quick_purchase")
@Data
public class QuickPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        return "QuickPurchase{" +
                "id=" + id +
                ", email='" + email + '\'' +
                               '}';
    }

    @Column(name = "email", nullable = false)
    private String email;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name =  "quick_purchase_id")
    @JsonManagedReference
    private List<QuickTicket> ticketList = new ArrayList<>();
}
