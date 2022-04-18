package capstone.luis.devbyte.posts.controllers;

import capstone.luis.devbyte.DTO.Pagination;
import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.posts.Dto.CreatePostDto;
import capstone.luis.devbyte.posts.models.Post;
import capstone.luis.devbyte.posts.services.PostService;
import capstone.luis.devbyte.users.models.AppUser;
import capstone.luis.devbyte.users.services.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService, AppUserService appUserService, ModelMapper modelMapper) {
        this.postService = postService;
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    // create new post
    @PostMapping("/create")
    public ResponseEntity<Post> createNewPost(@RequestBody @Valid CreatePostDto createPostDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new InvalidDataSubmissionException("Unable to perform request. Pleas ensure all fields are valid.");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/posts/create").toUriString());

        // TODO get the logged in user instead of a hard coded one
        AppUser user = appUserService.getUserByUsername("username");

        Post post = convertToEntity(createPostDto, user);
        return ResponseEntity.created(uri).body(postService.createNewPost(post));
    }

    // get posts for home page based on user authentication status
    @GetMapping("/home")
    public ResponseEntity<Page<Post>> getPosts(@Valid Pagination pagination, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }
        // TODO: check if request is by anonymous or user and use respective service

        return ResponseEntity.ok().body(postService.getPostsForAnonymous(pagination.getPage(), pagination.getCount()));
    }

    // get random posts from category
    @GetMapping("/explore/{type}")
    public ResponseEntity<Page<Post>> getPostsToExploreByType(@PathVariable(name = "type") String type,
                                                              @Valid Pagination pagination,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }
        return ResponseEntity.ok().body(postService.getPostsByCategory(type, pagination.getPage(), pagination.getCount()));
    }

    // get post with specified id
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable(name = "id", required = true) long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    // delete existing post
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id", required = true) long postId) {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/posts/delete").toUriString());
        // TODO: load logged in user and pass user id instead of the hardcoded "1"
        AppUser user = appUserService.getUserByUsername("username");

        postService.deletePost(postId, user.getId());
        return ResponseEntity.ok().build();
    }

    // helper method to convert dto to post entity
    private Post convertToEntity(CreatePostDto createPostDto, AppUser user) {
        Post post = modelMapper.map(createPostDto, Post.class);
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        return post;
    }
}
