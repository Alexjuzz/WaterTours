package di.repository.quickPurchase;


import di.model.entity.quickPurchase.QuickPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuickPurchaseRepository extends JpaRepository<QuickPurchase, Long> {
    Optional<QuickPurchase> findByEmail(String email);
}
