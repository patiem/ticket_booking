package epam.patiem.ticketbooking.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "user_accounts")
public class UserAccount {

    @Id
    private ObjectId id;

    @DBRef
    private User user;

    private BigDecimal money;

    public UserAccount() {
    }

    public UserAccount(User user, BigDecimal money) {
        this.user = user;
        this.money = money;
    }

    public UserAccount(ObjectId id, User user, BigDecimal money) {
        this.id = id;
        this.user = user;
        this.money = money;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMoney() {
        return money;
    }
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, money);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", user=" + user +
                ", money=" + money +
                '}';
    }
}
