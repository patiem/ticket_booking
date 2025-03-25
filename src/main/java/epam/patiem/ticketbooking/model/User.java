package epam.patiem.ticketbooking.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    @DBRef
    private UserAccount userAccount;

    private String name;
    private String email;

    private List<Ticket> tickets;


    public User() {
        tickets = new ArrayList<>();
    }

    public User(String name, String email) {
        this();
        this.name = name;
        this.email = email;
    }

    public User(ObjectId id, String name, String email) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userAccount=" + userAccount +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}

