package epam.patiem.ticketbooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import epam.patiem.ticketbooking.model.UserAccount;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserId(Long userId);
}
