package capstone.luis.devbyte.messages.services;

import capstone.luis.devbyte.messages.models.Message;
import org.springframework.data.domain.Page;

public interface MessageService {
    Message createNewMessage(Message message);
//    Message updateMessage(Message message);
    Page<Message> getAllMessageRoomMessages(long messageRoomId, long userId, int pageNumber, int count);
}
