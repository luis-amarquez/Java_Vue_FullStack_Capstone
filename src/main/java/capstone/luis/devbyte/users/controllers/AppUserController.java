package capstone.luis.devbyte.users.controllers;

import capstone.luis.devbyte.DTO.Pagination;
import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService appUserService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AppUserController(AppUserService appUserService, PostService postService, CommentService commentService) {
        this.appUserService = appUserService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/account/comments")
    public ResponseEntity<Page<Comment>> getAccountComments(@Valid Pagination pagination, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }

        // TODO: use principal
        AppUser user = appUserService.getUserByUsername("username");
        return ResponseEntity.ok().body(commentService.getAllUserComments(pagination.getPage(), pagination.getCount(), user.getId()));
    }

    @DeleteMapping("/account/delete")
    public ResponseEntity<?> deleteUserAccount() {
        // TODO: use principal to get logged in user
        AppUser user = appUserService.getUserByUsername("username");
        appUserService.deleteAppUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/posts")
    public ResponseEntity<Page<Post>> getAccountPosts(@Valid Pagination pagination, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }

        // TODO: use principal
        AppUser user = appUserService.getUserByUsername("username");
        return ResponseEntity.ok().body(postService.getPostsByUserId(user.getId(), pagination.getPage(), pagination.getCount()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok().body(appUserService.getUserByUsername(username));
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<Page<Post>> getUserPosts(@PathVariable(name = "username") String username,
                                                   Pagination page) {
        AppUser user = appUserService.getUserByUsername(username);
        return ResponseEntity.ok().body(postService.getPostsByUserId(user.getId(), page.getPage(), page.getCount()));
    }
}
