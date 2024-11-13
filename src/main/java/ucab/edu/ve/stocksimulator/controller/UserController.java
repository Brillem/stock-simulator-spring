package ucab.edu.ve.stocksimulator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.service.UserService;
import util.PasswordUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value= "/register", produces = "application/json")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequestDTO user) {
        System.out.println(user.getFirstName() + " " + user.getLastName());
        if (userService.userExistsByUsername(user.getUsername())) {
            System.out.println("existo");
            MessageResponseDTO message = new MessageResponseDTO(1, "User already exists");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            User createdUser = userService.mapUserRequestDTOToUser(user);
            userService.createUser(createdUser);
            UserResponseDTO userResponse = userService.mapUserToUserResponseDTO(createdUser);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
    }

    @PostMapping(value= "/login", produces = "application/json")
    public ResponseEntity<Object> loginUser(@RequestBody UserRequestDTO user) {
        if (userService.userExistsByUsername(user.getUsername())) {
            User matchedUser = userService.findUserByUsername(user.getUsername());
            if (PasswordUtil.matches(user.getPassword(), matchedUser.getHashedPassword())) {
                UserResponseDTO userResponse = userService.mapUserToUserResponseDTO(matchedUser);
                return ResponseEntity.status(HttpStatus.OK).body(userResponse);
            } else {
                MessageResponseDTO message = new MessageResponseDTO(1, "Incorrect password");
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        }
        else {
            MessageResponseDTO message = new MessageResponseDTO(2, "User doesn't exist");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
    }
}
