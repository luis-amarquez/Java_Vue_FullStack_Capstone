package capstone.luis.devbyte.messages.repositories;

import capstone.luis.devbyte.messages.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByMessageRoomId(long messageRoomId, Pageable page);
}
