package capstone.luis.devbyte.messages.models;

import capstone.luis.devbyte.users.models.AppUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser user1;
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser user2;

    public MessageRoom() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getUser1() {
        return user1;
    }

    public void setUser1(AppUser user1) {
        this.user1 = user1;
    }

    public AppUser getUser2() {
        return user2;
    }

    public void setUser2(AppUser user2) {
        this.user2 = user2;
    }
}
