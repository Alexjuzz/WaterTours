package di.model.dto.tickets;

import di.model.entity.user.User;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseTicket {
    private User user;
    private Map<String, Integer> quantityTickets;
}
