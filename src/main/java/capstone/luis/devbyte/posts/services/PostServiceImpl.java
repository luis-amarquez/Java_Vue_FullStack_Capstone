package capstone.luis.devbyte.posts.services;

import capstone.luis.devbyte.errors.exceptions.ModelEntityNotFoundException;
import capstone.luis.devbyte.errors.exceptions.UnauthorizedRequestException;
import capstone.luis.devbyte.posts.models.Post;
import capstone.luis.devbyte.posts.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<Post> getPostsForAnonymous(int pageNumber, int count) {
        Pageable page = PageRequest.of(pageNumber, count);
        return postRepository.findAll(page);
    }

    @Override
    public Page<Post> getPostsForAuthenticated(int pageNumber, int count) {
        return null;
    }

    @Override
    public Page<Post> getPostsByCategory(String category, int pageNumber, int count) {
        Pageable page = PageRequest.of(pageNumber, count);
        return postRepository.findAllByCategory(category, page);
    }

    @Override
    public Page<Post> getPostsByUserId(long userId, int pageNumber, int count) {
        Pageable page = PageRequest.of(pageNumber, count);
        return postRepository.findAllByUserId(userId, page);
    }

    @Override
    public Post getPostById(long postId) {
        try {
            return postRepository.findById(postId).get();
        } catch (NoSuchElementException e) {
            throw new ModelEntityNotFoundException(String.format("No post found with id: %d", postId), e);
        }
    }

    @Override
    public Post getPostByIdAndUsername(long postId, String username) {
        Post post = postRepository.findPostByIdAndUserUsername(postId, username);
        if (post == null) {
            throw new ModelEntityNotFoundException("Post does not exist");
        }
        return post;
    }

    @Override
    public Post createNewPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(long postId, long userId) {
        Post post = getPostById(postId);

        if (post.getUser().getId() == userId){
            postRepository.delete(post);
        } else {
            throw new UnauthorizedRequestException("Can not perform this action.");
        }
    }
}
