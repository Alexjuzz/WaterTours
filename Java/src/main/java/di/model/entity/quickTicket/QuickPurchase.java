package di.model.entity.quickTicket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import di.model.entity.telephone.Telephone;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telephone_id",referencedColumnName = "id",nullable = false)
    @JsonManagedReference
    private Telephone telephone;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name =  "quick_purchase_id")
    @JsonManagedReference
    private List<QuickTicket> ticketList = new ArrayList<>();
}
