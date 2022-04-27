package capstone.luis.devbyte.messages.services;

import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.errors.exceptions.ModelEntityNotFoundException;
import capstone.luis.devbyte.errors.exceptions.UnauthorizedRequestException;
import capstone.luis.devbyte.messages.models.MessageRoom;
import capstone.luis.devbyte.messages.repositories.MessageRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MessageRoomServiceImpl implements MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;

    @Autowired
    public MessageRoomServiceImpl(MessageRoomRepository messageRoomRepository) {
        this.messageRoomRepository = messageRoomRepository;
    }

    @Override
    public MessageRoom checkMessageRoomExists(long user1Id, long user2Id) {
        MessageRoom messageRoom = messageRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (messageRoom == null) {
            messageRoom = messageRoomRepository.findByUser1IdAndUser2Id(user2Id, user1Id);
        }
        return messageRoom;
    }

    @Override
    public MessageRoom getMessageRoomById(long messageRoomId, long userId) {
        try {
            MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();
            if (messageRoom.getUser1().getId() != userId && messageRoom.getUser2().getId() != userId) {
                throw new UnauthorizedRequestException("You do not have permission to view these messages.");
            }
            return messageRoom;
        } catch (NoSuchElementException e) {
            throw new ModelEntityNotFoundException(String.format("No message room was found for id: %d", messageRoomId), e);
        }
    }

    @Override
    public MessageRoom createNewMessageRoom(MessageRoom messageRoom) {
        return messageRoomRepository.save(messageRoom);
    }

//    @Override
//    public MessageRoom updateMessageRoom(MessageRoom messageRoom) {
//        return messageRoomRepository.save(messageRoom);
//    }

    @Override
    public void deleteMessageRoomById(long messageRoomId, long userId) {
        MessageRoom messageRoom = getMessageRoomById(messageRoomId, userId);
        messageRoomRepository.delete(messageRoom);
    }

    @Override
    public Page<MessageRoom> getAllUserMessageRooms(long userId, int pageNumber, int count) {
        Pageable page = PageRequest.of(pageNumber, count);
        return messageRoomRepository.findAllByUser1IdOrUser2Id(userId, userId, page);
    }
}
