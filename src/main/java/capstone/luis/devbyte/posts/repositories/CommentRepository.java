package capstone.luis.devbyte.posts.repositories;

import capstone.luis.devbyte.posts.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByUserId(long userId, Pageable pageable);
    Page<Comment> findAllByPostId(long postId, Pageable pageable);
}
