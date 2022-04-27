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
}
