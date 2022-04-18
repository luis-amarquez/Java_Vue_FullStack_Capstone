package capstone.luis.devbyte.posts.services;

import capstone.luis.devbyte.errors.exceptions.ModelEntityNotFoundException;
import capstone.luis.devbyte.errors.exceptions.UnauthorizedRequestException;
import capstone.luis.devbyte.posts.models.Comment;
import capstone.luis.devbyte.posts.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getCommentById(long commentId) {
        try {
            return commentRepository.findById(commentId).get();
        } catch (NoSuchElementException e) {
            throw new ModelEntityNotFoundException(String.format("Comment was not found for id: %d", commentId), e);
        }
    }

    @Override
    public Comment createNewComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getAllUserComments(int pageNumber, int pageCount, long userId) {
        Pageable page = PageRequest.of(pageNumber, pageCount);
        return commentRepository.findAllByUserId(userId, page);
    }

    @Override
    public Page<Comment> getAllCommentsForPost(int pageNumber, int pageCount, long postId) {
        Pageable page = PageRequest.of(pageNumber, pageCount);
        return commentRepository.findAllByPostId(postId, page);
    }

    @Override
    public void deleteComment(long commentId, long userId) {
        Comment comment = getCommentById(commentId);

        if(comment.getUser().getId() == userId) {
            commentRepository.delete(comment);
        } else {
            throw new UnauthorizedRequestException("Can not perform action");
        }
    }
}
