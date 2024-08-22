package di.model.dto.tickets;

import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;

public class TicketFactory {
    public static AbstractTicket createTicket(String type) {
        return switch (type.toLowerCase()) {
            case "adult" -> new AdultTicket();
            case "child" -> new ChildTicket();
            case "senior" -> new SeniorTicket();
            default -> throw new IllegalArgumentException("Invalid ticket type: " + type);
        };
    }
}
