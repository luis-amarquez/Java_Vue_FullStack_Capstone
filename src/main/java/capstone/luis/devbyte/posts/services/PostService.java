package capstone.luis.devbyte.posts.services;

import capstone.luis.devbyte.posts.models.Post;

import org.springframework.data.domain.Page;

// TODO: figure out how to efficiently get posts from users one follows
public interface PostService {
    // used to explore posts in home view when anonymous.
    Page<Post> getPostsForAnonymous(int pageNumber, int count);
    // used to explore posts in home view when authenticated.
    Page<Post> getPostsForAuthenticated(int pageNumber, int count);
    // get posts to explore for specific category
    Page<Post> getPostsByCategory(String category, int pageNumber, int count);
    // fetch all the posts of the user page, after checking user exists
    Page<Post> getPostsByUserId(long userid, int pageNumber, int count);
    Post getPostById(long postId);
    Post getPostByIdAndUsername(long postId, String username);
    // create post. automatically create date in backend, only create no update
    Post createNewPost(Post post);
    void deletePost(long postId, long userId);
}
