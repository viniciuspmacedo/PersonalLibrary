package dev.viniciusmacedo.PersonalLibrary.user;

import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return userService.findUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest request) {
        UserModel user = UserMapper.toUserModel(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserResponse(userService.saveUser(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        UserModel user = UserMapper.toUserModel(request);
        return userService.updateUser(id, user)
                .map(userModel -> ResponseEntity.ok(UserMapper.toUserResponse(userModel)))
                .orElse(ResponseEntity.notFound().build());
    }
}
