package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.sql.User;

import java.util.List;

public interface UserService {
    User getUserById(long userId);
    User getUserByEmail(String email);
    List<User> getUsersByName(String name, int pageSize, int pageNum);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(long userId);
}
