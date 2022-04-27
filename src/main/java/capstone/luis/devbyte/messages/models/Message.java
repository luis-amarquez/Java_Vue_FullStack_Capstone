package capstone.luis.devbyte.messages.models;

import capstone.luis.devbyte.users.models.AppUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(targetEntity = MessageRoom.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MessageRoom messageRoom;
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser sender;
    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime sent;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MessageRoom getMessageRoom() {
        return messageRoom;
    }

    public void setMessageRoom(MessageRoom messageRoom) {
        this.messageRoom = messageRoom;
    }

    public AppUser getSender() {
        return sender;
    }

    public void setSender(AppUser sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
    }
}
