package di.model.dto.tickets;

import di.model.entity.user.GuestUser;
import lombok.Data;

import java.util.Map;

@Data
public class QuickTicketOrder{
    private GuestUser guestUser;
    private Map<String, Integer> quantityTickets;
}
