package epam.patiem.ticketbooking.repository.sql;

import epam.patiem.ticketbooking.model.sql.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserId(Long userId);
}
