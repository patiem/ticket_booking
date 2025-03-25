package epam.patiem.ticketbooking.repository;

import epam.patiem.ticketbooking.model.Category;
import epam.patiem.ticketbooking.model.Ticket;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, ObjectId> {

    Page<Ticket> getAllByUserId(Pageable pageable, ObjectId userId);
    Page<Ticket> getAllByEventId(Pageable pageable, ObjectId eventId);
    Boolean existsByEventIdAndPlaceAndCategory(ObjectId eventId, Integer place, Category category);
}
