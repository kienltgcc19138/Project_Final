package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Ticket;
import vn.kien.event.eventbe.response.TicketResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 10:51
 */
public class TicketConverter {
    public static TicketResponse convertTicketResponse(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setObjectId(ticket.getObjectId());
        ticketResponse.setObjectType(ticket.getObjectType());
        ticketResponse.setName(ticket.getName());
        ticketResponse.setDescription(ticket.getDescription());
        ticketResponse.setCreatedAt(ticket.getCreatedAt().toString());
        return ticketResponse;
    }

    public static Set<TicketResponse> convertTicketResponses(Set<Ticket> tickets) {
        Set<TicketResponse> ticketResponses = new LinkedHashSet<>();
        for (Ticket ticket : tickets) {
            ticketResponses.add(convertTicketResponse(ticket));
        }
        return ticketResponses;
    }
}
