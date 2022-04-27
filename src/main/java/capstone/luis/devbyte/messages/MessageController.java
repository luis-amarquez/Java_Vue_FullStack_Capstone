package capstone.luis.devbyte.messages;

import capstone.luis.devbyte.DTO.Pagination;
import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.messages.models.Message;
import capstone.luis.devbyte.messages.models.MessageRoom;
import capstone.luis.devbyte.messages.services.MessageRoomService;
import capstone.luis.devbyte.messages.services.MessageService;
import capstone.luis.devbyte.users.models.AppUser;
import capstone.luis.devbyte.users.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    public final AppUserService appUserService;
    public final MessageRoomService messageRoomService;
    public final MessageService messageService;

    @Autowired
    public MessageController(AppUserService appUserService, MessageRoomService messageRoomService, MessageService messageService) {
        this.appUserService = appUserService;
        this.messageRoomService = messageRoomService;
        this.messageService = messageService;
    }

    @GetMapping("/message_rooms")
    public ResponseEntity<Page<MessageRoom>> getUsersMessageRooms(@Valid Pagination pagination, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }

        AppUser user = appUserService.getUserByUsername(principal.getName());
        return ResponseEntity.ok().body(messageRoomService.getAllUserMessageRooms(user.getId(), pagination.getPage(), pagination.getCount()));
    }

    @GetMapping("/message_rooms/user/{userId}")
    public ResponseEntity<MessageRoom> getMessageRoomByUser(@PathVariable(name = "userId") long id, Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        return ResponseEntity.ok().body(messageRoomService.checkMessageRoomExists(user.getId(), id));
    }

    @GetMapping("/message_room/{id}")
    public ResponseEntity<MessageRoom> getMessageRoomById(@PathVariable(name = "id") long id, Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        return ResponseEntity.ok().body(messageRoomService.getMessageRoomById(id, user.getId()));
    }

    @DeleteMapping("/message_room/{id}")
    public ResponseEntity<?> deleteMessageRoom(@PathVariable(name = "id") long id, Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        messageRoomService.deleteMessageRoomById(id, user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/message_room/{userId}")
    public ResponseEntity<MessageRoom> createNewMessageRoom(@PathVariable(name = "userId") long userId, Principal principal) {
        AppUser user1 = appUserService.getUserByUsername(principal.getName());
        AppUser user2 = appUserService.getUserById(userId);
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.setUser1(user1);
        messageRoom.setUser2(user2);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/message/message_room").toUriString());
        return ResponseEntity.created(uri).body(messageRoomService.createNewMessageRoom(messageRoom));
    }

    @PostMapping("/")
    public ResponseEntity<Message> createNewMessage(@RequestBody @Valid CreateMessageDto newMessage, Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        MessageRoom messageRoom = messageRoomService.getMessageRoomById(newMessage.getRoomId(), user.getId());
        Message message = new Message();
        message.setMessage(newMessage.getMessage());
        message.setSender(user);
        message.setMessageRoom(messageRoom);
        message.setSent(LocalDateTime.now());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/message").toUriString());
        return ResponseEntity.created(uri).body(messageService.createNewMessage(message));
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<Page<Message>> getRoomMessages(@Valid Pagination pagination, BindingResult bindingResult, @PathVariable(name = "roomId") long roomId, Principal principal) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }
        AppUser user = appUserService.getUserByUsername(principal.getName());
        return ResponseEntity.ok().body(messageService.getAllMessageRoomMessages(roomId, user.getId(), pagination.getPage(), pagination.getCount()));
    }
}
