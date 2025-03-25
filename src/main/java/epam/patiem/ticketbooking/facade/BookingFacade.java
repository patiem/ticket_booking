package epam.patiem.ticketbooking.facade;

import epam.patiem.ticketbooking.model.Category;
import epam.patiem.ticketbooking.model.Event;
import epam.patiem.ticketbooking.model.Ticket;
import epam.patiem.ticketbooking.model.User;

import java.util.Date;
import java.util.List;

/**
 * Groups together all operations related to tickets booking.
 * Created by maksym_govorischev.
 */
public interface BookingFacade {

    Event getEventById(long eventId);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);

    User getUserById(long userId);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(long userId);

    Ticket bookTicket(long userId, long eventId, int place, Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);

}
