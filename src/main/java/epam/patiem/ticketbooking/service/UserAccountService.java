package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.UserAccount;

import java.math.BigDecimal;

public interface UserAccountService {

    UserAccount refillAccount(String userId, BigDecimal money);
}
