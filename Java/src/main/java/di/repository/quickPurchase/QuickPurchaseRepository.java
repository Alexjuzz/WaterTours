package di.repository.quickPurchase;

import di.model.entity.quickTicket.QuickPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuickPurchaseRepository extends JpaRepository<QuickPurchase, Long> {

}
