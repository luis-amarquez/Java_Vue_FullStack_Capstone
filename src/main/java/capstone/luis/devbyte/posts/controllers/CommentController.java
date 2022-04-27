package capstone.luis.devbyte.posts.controllers;

import capstone.luis.devbyte.DTO.Pagination;
import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.posts.Dto.CreateCommentDto;
import capstone.luis.devbyte.posts.models.Comment;
import capstone.luis.devbyte.posts.models.Post;
import capstone.luis.devbyte.posts.services.CommentService;
import capstone.luis.devbyte.posts.services.PostService;
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
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final AppUserService appUserService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, AppUserService appUserService) {
        this.commentService = commentService;
        this.postService = postService;
        this.appUserService = appUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createNewComment(@RequestBody @Valid CreateCommentDto commentDto,
                                                    BindingResult bindingResult,
                                                    Principal principal){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Unable to create new comment. Please ensure all fields are correct.");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/comment/create").toUriString());
        AppUser user = appUserService.getUserByUsername(principal.getName());

        Post post = postService.getPostById(commentDto.getPostId());
        Comment comment = new Comment(commentDto.getComment(), LocalDateTime.now(), user, post);
        return ResponseEntity.created(uri).body(commentService.createNewComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable(name = "id") long commentId, Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Page<Comment>> getAllCommentsForPost(@PathVariable(name = "id") long postId,
                                                               @Valid Pagination pagination,
                                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Unable to perform request. Please ensure pagination parameters are valid.");
        }
        return ResponseEntity.ok().body(commentService.getAllCommentsForPost(pagination.getPage(), pagination.getCount(), postId));
    }
}
