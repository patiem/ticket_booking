package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.Category;
import epam.patiem.ticketbooking.model.Event;
import epam.patiem.ticketbooking.model.Ticket;
import epam.patiem.ticketbooking.model.User;

import java.util.List;


public interface TicketService {

    Ticket bookTicket(String userId, String eventId, int place, Category category);
    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);
    boolean cancelTicket(String ticketId);
}