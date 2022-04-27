package capstone.luis.devbyte.messages.repositories;

import capstone.luis.devbyte.messages.models.MessageRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    Page<MessageRoom> findAllByUser1IdOrUser2Id(long user1Id, long user2Id, Pageable page);
    MessageRoom findByUser1IdAndUser2Id(long user1, long user2);
}
