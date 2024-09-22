package di.model.dto.tickets;

import di.model.entity.telephone.Telephone;
import lombok.Data;

import java.util.Map;

@Data
public class QuickTicketOrder{
    private String email;
    private String telephone;
    private Map<String, Integer> quantityTickets;
}
