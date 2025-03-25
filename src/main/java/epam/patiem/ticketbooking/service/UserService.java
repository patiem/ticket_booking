package epam.patiem.ticketbooking.service;

import epam.patiem.ticketbooking.model.User;
import java.lang.String;

import java.util.List;

public interface UserService {
    User getUserById(String userId);
    User getUserByEmail(String email);
    List<User> getUsersByName(String name, int pageSize, int pageNum);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(String userId);
}
