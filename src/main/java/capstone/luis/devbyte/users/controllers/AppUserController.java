package capstone.luis.devbyte.users.controllers;

import capstone.luis.devbyte.DTO.Pagination;
import capstone.luis.devbyte.errors.exceptions.InvalidDataSubmissionException;
import capstone.luis.devbyte.posts.models.Comment;
import capstone.luis.devbyte.posts.models.Post;
import capstone.luis.devbyte.posts.services.CommentService;
import capstone.luis.devbyte.posts.services.PostService;
import capstone.luis.devbyte.users.CreateUserDTO;
import capstone.luis.devbyte.users.models.AppUser;
import capstone.luis.devbyte.users.models.Role;
import capstone.luis.devbyte.users.repositories.RoleRepository;
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
import java.util.HashSet;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private final AppUserService appUserService;
    private final PostService postService;
    private final CommentService commentService;
    private final RoleRepository roleRepository;

    @Autowired
    public AppUserController(AppUserService appUserService, PostService postService, CommentService commentService, RoleRepository roleRepository) {
        this.appUserService = appUserService;
        this.postService = postService;
        this.commentService = commentService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/account")
    public ResponseEntity<AppUser> getAccountInfo(Principal principal) {
        return ResponseEntity.ok().body(appUserService.getUserByUsername(principal.getName()));
    }

    @PostMapping("/account")
    public ResponseEntity<AppUser> registerUser(@RequestBody @Valid CreateUserDTO newUser, BindingResult bindingResult) {
        System.out.println(newUser.toString());
        if (bindingResult.hasErrors() || !Objects.equals(newUser.getPassword(), newUser.getConfirmPassword())) {
            System.out.println(bindingResult.getFieldErrors());
            throw new InvalidDataSubmissionException("Ensure all fields are valid");
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/account").toUriString());
        Role role = roleRepository.findByName("ROLE_USER");
        AppUser user = new AppUser();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setRoles(new HashSet<>());
        user.getRoles().add(role);

        return ResponseEntity.created(uri).body(appUserService.registerAppUser(user));
    }

    @GetMapping("/account/comments")
    public ResponseEntity<Page<Comment>> getAccountComments(@Valid Pagination pagination, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }

        AppUser user = appUserService.getUserByUsername(principal.getName());
        return ResponseEntity.ok().body(commentService.getAllUserComments(pagination.getPage(), pagination.getCount(), user.getId()));
    }

    @DeleteMapping("/account/delete")
    public ResponseEntity<?> deleteUserAccount(Principal principal) {
        AppUser user = appUserService.getUserByUsername(principal.getName());
        appUserService.deleteAppUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/posts")
    public ResponseEntity<Page<Post>> getAccountPosts(@Valid Pagination pagination, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            throw new InvalidDataSubmissionException("Pagination parameters are invalid.");
        }

        AppUser user = appUserService.getUserByUsername(principal.getName());
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
