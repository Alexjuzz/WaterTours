package di.model.dto.tickets;

import lombok.Data;

import java.util.Map;

@Data
public class QuickTicketOrder{
    private String email;
    private Map<String, Integer> quantityTickets;
}
