package di.repository.quickPurchase;


import di.model.entity.quickTicket.QuickTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuickTicketRepository extends JpaRepository<QuickTicket, Long> {

}
