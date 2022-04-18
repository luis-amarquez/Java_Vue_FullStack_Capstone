package capstone.luis.devbyte.posts.repositories;

import capstone.luis.devbyte.posts.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(String category, Pageable pageable);
    Page<Post> findAllByUserId(long userId, Pageable pageable);
}
