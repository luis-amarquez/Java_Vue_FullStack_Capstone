package capstone.luis.devbyte.messages;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateMessageDto {
    @NotNull
    @Min(1)
    private long roomId;
    @NotNull
    @Length(min = 1)
    private String message;

    public CreateMessageDto() {
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message.trim();
    }

    public void setMessage(String message) {
        this.message = message.trim();
    }
}
