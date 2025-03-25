package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.sql.UserAccount;

import java.math.BigDecimal;

public interface UserAccountService {

    UserAccount refillAccount(long userId, BigDecimal money);
}
