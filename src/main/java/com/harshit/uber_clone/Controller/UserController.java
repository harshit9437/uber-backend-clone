package com.harshit.uber_clone.Controller;

import com.harshit.uber_clone.Dto.UserDto;
import com.harshit.uber_clone.Entity.User;
import com.harshit.uber_clone.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService =userService;
    }
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully"
    )
    @Operation(
            summary = "Register User",
            description = "Registers a new user into the Uber platform"
    )
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {

        User user = userService.createUser(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getuser(id);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }
    @PutMapping("/{id}")
    public User updateUser(@Valid@PathVariable Long id,@RequestBody UserDto userDto){
        return userService.updateUser(id,userDto);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
         userService.deleteUser(id);
    }

    @GetMapping("/me")
    public UserDto getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH = " + authentication);

        String email = authentication.getName();

        System.out.println("EMAIL FROM CONTROLLER = " + email);

        return userService.getUserByEmail(email);
    }
}
