package capstone.luis.devbyte.messages.services;

import capstone.luis.devbyte.messages.models.MessageRoom;
import org.springframework.data.domain.Page;

public interface MessageRoomService {
    MessageRoom checkMessageRoomExists(long user1Id, long user2Id);
    MessageRoom getMessageRoomById(long messageRoomId, long userId);
    MessageRoom createNewMessageRoom(MessageRoom messageRoom);
//    MessageRoom updateMessageRoom(MessageRoom messageRoom);
    void deleteMessageRoomById(long messageRoomId, long userId);

    Page<MessageRoom> getAllUserMessageRooms(long userId, int pageNumber, int count);
}
