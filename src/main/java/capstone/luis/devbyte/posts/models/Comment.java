package capstone.luis.devbyte.posts.models;

import capstone.luis.devbyte.users.models.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;
    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
    private AppUser user;
    @ManyToOne(targetEntity = Post.class, fetch = FetchType.EAGER)
    private Post post;

    public Comment() {
    }

    public Comment(String comment, LocalDateTime created, AppUser user, Post post) {
        this.id = 0;
        this.comment = comment;
        this.created = created;
        this.user = user;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
