package capstone.luis.devbyte.posts.Dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateCommentDto {
    @NotNull
    @Length(min = 1)
    private String comment;
    @NotNull
    @Min(1)
    private long postId;

    public CreateCommentDto() {
    }

    public String getComment() {
        return comment.trim();
    }

    public void setComment(String comment) {
        this.comment = comment.trim();
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
