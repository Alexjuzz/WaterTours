package di.model.dto.tickets;

import di.model.entity.quickTicket.QuickAdultTicket;
import di.model.entity.quickTicket.QuickChildTicket;
import di.model.entity.quickTicket.QuickSeniorTicket;
import di.model.entity.quickTicket.QuickTicket;
import di.model.entity.ticket.AbstractTicket;
import di.model.entity.ticket.AdultTicket;
import di.model.entity.ticket.ChildTicket;
import di.model.entity.ticket.SeniorTicket;
import di.repository.quickPurchase.QuickPurchaseRepository;

public final class TicketFactory {
    public static AbstractTicket createTicket(String type) {
        return switch (type.toLowerCase()) {
            case "adult" -> new AdultTicket();
            case "child" -> new ChildTicket();
            case "senior" -> new SeniorTicket();
            default -> throw new IllegalArgumentException("Invalid ticket type: " + type);
        };
    }

    public static QuickTicket createQuickTicket(String type){
        return switch (type.toLowerCase()){
            case "adult" -> new QuickAdultTicket();
            case "child" -> new QuickChildTicket();
            case "senior" -> new QuickSeniorTicket();
            default -> throw  new IllegalArgumentException("Invalid ticket type: " + type);
        };
    }
}
