package epam.patiem.ticketbooking.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;

@Document(collection = "tickets")
public class Ticket {

    @Id
    private ObjectId id;

    @DBRef
    private User user;

    @DBRef
    private Event event;

    private Integer place;
    private Category category;

    public Ticket() {
    }

    public Ticket(ObjectId id, User user, Event event, int place, Category category) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public Ticket(User user, Event event, int place, Category category) {
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(user, ticket.user) && Objects.equals(event, ticket.event) && Objects.equals(place, ticket.place) && category == ticket.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, event, place, category);
    }

    @Override
    public String toString() {
        return "{" +
                "'id' : " + id +
                ", 'userId' : " + (user != null ? user.getId() : null) +
                ", 'eventId' : " + (user != null ? user.getId() : null) +
                ", 'place' : " + place +
                ", 'category' : '" + category +
                "'}";
    }
}
