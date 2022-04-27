package capstone.luis.devbyte.messages.services;

import capstone.luis.devbyte.errors.exceptions.UnauthorizedRequestException;
import capstone.luis.devbyte.messages.models.Message;
import capstone.luis.devbyte.messages.models.MessageRoom;
import capstone.luis.devbyte.messages.repositories.MessageRepository;
import capstone.luis.devbyte.messages.repositories.MessageRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageRoomRepository messageRoomRepository) {
        this.messageRepository = messageRepository;
        this.messageRoomRepository = messageRoomRepository;
    }

    @Override
    public Message createNewMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> getAllMessageRoomMessages(long messageRoomId, long userId, int pageNumber, int count) {
        Pageable page = PageRequest.of(pageNumber, count);
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();
        if (messageRoom.getUser1().getId() != userId && messageRoom.getUser2().getId() != userId){
            throw new UnauthorizedRequestException("You do not have permission to perform this action");
        }
        return messageRepository.findAllByMessageRoomId(messageRoomId, page);
    }
}
