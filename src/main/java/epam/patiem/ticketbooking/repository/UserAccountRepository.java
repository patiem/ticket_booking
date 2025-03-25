package epam.patiem.ticketbooking.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import epam.patiem.ticketbooking.model.UserAccount;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, ObjectId> {

    Optional<UserAccount> findByUserId(ObjectId userId);
}
