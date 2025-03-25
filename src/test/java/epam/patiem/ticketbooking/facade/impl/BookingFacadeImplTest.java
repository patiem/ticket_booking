package epam.patiem.ticketbooking.facade.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import epam.patiem.ticketbooking.model.Category;
import epam.patiem.ticketbooking.model.Event;
import epam.patiem.ticketbooking.model.Ticket;
import epam.patiem.ticketbooking.model.User;
import epam.patiem.ticketbooking.model.UserAccount;
import epam.patiem.ticketbooking.repository.UserAccountRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Sql(value = {"classpath:sql/clear-database.sql", "classpath:sql/insert-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:sql/clear-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookingFacadeImplTest {

    @Autowired
    private BookingFacadeImpl bookingFacade;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void createUserThenCreateEventThenBookTicketForThisEventForUserAndThenCancelItShouldBeOk() {
        User user = new User("Andrii", "andrii@gmail.com");
        Event event = new Event("Integration Event", new Date(System.currentTimeMillis()), BigDecimal.valueOf(250));
        int place = 10;

        user = bookingFacade.createUser(user);

        assertNotNull(bookingFacade.getUserById(user.getId().toString()));

        event = bookingFacade.createEvent(event);

        assertNotNull(bookingFacade.getEventById(event.getId().toString()));

        UserAccount userAccount = bookingFacade.refillUserAccount(user.getId().toString(), BigDecimal.valueOf(500));

        assertEquals(BigDecimal.valueOf(500), userAccount.getMoney());

        Ticket ticket = bookingFacade.bookTicket(user.getId().toString(), event.getId().toString(), place, Category.STANDARD);

        assertEquals(250, userAccountRepository.findById(user.getId()).get().getMoney().intValue());

        List<Ticket> bookedTicketsByUserBeforeCanceling = bookingFacade.getBookedTickets(user, 1, 1);
        List<Ticket> bookedTicketsByEventBeforeCanceling = bookingFacade.getBookedTickets(event, 1, 1);

        assertTrue(bookedTicketsByUserBeforeCanceling.contains(ticket));
        assertTrue(bookedTicketsByEventBeforeCanceling.contains(ticket));

        bookingFacade.cancelTicket(ticket.getId().toString());

        List<Ticket> bookedTicketsByUserAfterCanceling = bookingFacade.getBookedTickets(user, 1, 1);
        List<Ticket> bookedTicketsByEventAfterCanceling = bookingFacade.getBookedTickets(event, 1, 1);

        assertTrue(bookedTicketsByUserAfterCanceling.isEmpty());
        assertTrue(bookedTicketsByEventAfterCanceling.isEmpty());
    }

    @Test
    public void refillUserAccountAndBookTicketWithNotExistingUserAccountShouldBeOk() {
        String userId = "abc";
        String eventId = "def";
        int place = 5;
        Category category = Category.BAR;
        BigDecimal money = BigDecimal.valueOf(5000);

        UserAccount userAccount = bookingFacade.refillUserAccount(userId, money);

        assertEquals(userId, userAccount.getUser().getId());
        assertEquals(money, userAccount.getMoney());

        Ticket ticket = bookingFacade.bookTicket(userId, eventId, place, category);

        assertNotNull(ticket);
        assertEquals(Long.valueOf(userId), ticket.getUser().getId());

        User userById = bookingFacade.getUserById(userId);

        assertEquals(userAccount.getMoney().subtract(ticket.getEvent().getTicketPrice()),
                userById.getUserAccount().getMoney());
    }

}