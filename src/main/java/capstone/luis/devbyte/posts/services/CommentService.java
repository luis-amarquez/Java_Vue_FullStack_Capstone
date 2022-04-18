package capstone.luis.devbyte.posts.services;

import capstone.luis.devbyte.posts.models.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment getCommentById(long commentId);
    Comment createNewComment(Comment comment);
    // get all the comments of the logged-in user
    Page<Comment> getAllUserComments(int pageNumber, int pageCount, long userId);
    Page<Comment> getAllCommentsForPost(int pageNumber, int pageCount, long postId);
    void deleteComment(long commentId, long userId);
}
