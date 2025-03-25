package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.sql.Category;
import epam.patiem.ticketbooking.model.sql.Event;
import epam.patiem.ticketbooking.model.sql.Ticket;
import epam.patiem.ticketbooking.model.sql.User;

import java.util.List;


public interface TicketService {

    Ticket bookTicket(long userId, long eventId, int place, Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);
}