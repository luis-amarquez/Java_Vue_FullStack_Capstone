package capstone.luis.devbyte.posts.Dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreatePostDto {
    @Length(min = 1, max = 100)
    private String title;
    @NotNull
    @Length(min = 1)
    private String content;
    @NotNull
    @Pattern(regexp = "post|question|article|snippet|fun", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String category;

    public CreatePostDto() {
    }

    public String getTitle() {
        return title.trim();
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public String getContent() {
        return content.trim();
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //    @Null
//    private long id;
//    private String title;
//    @NotNull
//    private String content;
//    @Null
//    private int likes;
//    @Null
//    private LocalDateTime created;
//    @NotNull
//    @Pattern(regexp = "post|question|article|snippet|fun", flags = Pattern.Flag.CASE_INSENSITIVE)
//    private String category;
//    @Null
//    private long commentCount;
//
//    public CreatePostDto() {
//    }
//
//    @Override
//    public String toString() {
//        return "CreatePostDto{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", likes=" + likes +
//                ", created=" + created +
//                ", category='" + category + '\'' +
//                ", commentCount=" + commentCount +
//                '}';
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
//
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public void setCreated(LocalDateTime created) {
//        this.created = created;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category.toLowerCase();
//    }
//
//    public long getCommentCount() {
//        return commentCount;
//    }
//
//    public void setCommentCount(long commentCount) {
//        this.commentCount = commentCount;
//    }
}
