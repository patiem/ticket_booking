package epam.patiem.ticketbooking.repository.sql;

import epam.patiem.ticketbooking.model.sql.Category;
import epam.patiem.ticketbooking.model.sql.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Page<Ticket> getAllByUserId(Pageable pageable, Long userId);

    Page<Ticket> getAllByEventId(Pageable pageable, Long eventId);

    Boolean existsByEventIdAndPlaceAndCategory(Long eventId, Integer place, Category category);
}
