package di.repository.ticket;

import di.model.entity.ticket.AbstractTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<AbstractTicket, Long> {
    @Query("SELECT t FROM   AbstractTicket t WHERE t.uniqueTicketId = :uniqueCode")
    Optional<AbstractTicket> getTicketByUniqueCode(@Param("uniqueCode") UUID uniqueCode);
}
