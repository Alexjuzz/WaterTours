package di.repository.quickPurchase;

import di.model.entity.quickTicket.QuickPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuickPurchaseRepository extends JpaRepository<QuickPurchase, Long> {
                Optional<QuickPurchase> findByEmail(@Param("email") String email);
}
