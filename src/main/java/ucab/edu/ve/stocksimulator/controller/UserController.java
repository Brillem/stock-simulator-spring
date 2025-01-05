package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.edu.ve.stocksimulator.dto.request.ConfirmUserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.service.EmailSenderService;
import ucab.edu.ve.stocksimulator.service.UserService;
import util.PasswordUtil;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public UserController(UserService userService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping(value= "/register", produces = "application/json")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequestDTO user) {
        System.out.println(user.getFirstName() + " " + user.getLastName());
        if (userService.userExistsByUsername(user.getUsername())) {
            MessageResponseDTO message = new MessageResponseDTO(1, "User already exists");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else if (userService.userExistsByEmail(user.getEmail())) {
            MessageResponseDTO message = new MessageResponseDTO(2, "Email already exists");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else {
            String email = user.getEmail();
            String code = PasswordUtil.generateRandomCode();
            this.emailSenderService.sendConfirmationEmail(email, code);
            User createdUser = userService.mapUserRequestDTOToUser(user);
            createdUser.setConfirmationCode(code);
            createdUser.setVerified(false);
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

    @PostMapping(value = "/confirm", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> confirmUser(@RequestBody ConfirmUserRequestDTO confirmUser) {
        User user = userService.findUserByUsername(confirmUser.getUsername());
        MessageResponseDTO message;
        if (user.getConfirmationCode().equals(confirmUser.getConfirmationCode())) {
            user.setVerified(true);
            user.setConfirmationCode(null);
            userService.updateUser(user);
            message = new MessageResponseDTO(0, "User confirmed Successfully");
        }
        else {
            message = new MessageResponseDTO(1, "Incorrect confirmation code");
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> usersResponse = userService.mapUserListToUserResponseDTOList(users);
        return ResponseEntity.status(HttpStatus.OK).body(usersResponse);
    }
    @PostMapping(value = "/edit", produces = "application/json")
    public ResponseEntity<Object> editUser(User user) {
        User old_user = userService.findUserById(user.getId());
        if (userService.userExistsByUsername(user.getUsername()) && !user.getUsername().equals(old_user.getUsername())) {
            MessageResponseDTO message = new MessageResponseDTO(1, "Username already taken");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else if (userService.userExistsByEmail(user.getEmail()) && !user.getEmail().equals(old_user.getEmail())) {
            MessageResponseDTO message = new MessageResponseDTO(2, "Email already used");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        if (userService.userExistsByUsername(user.getUsername()) && user.getUsername().equals(old_user.getUsername())) {
            MessageResponseDTO message = new MessageResponseDTO(3, "Cannot use the same username");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else if (userService.userExistsByEmail(user.getEmail()) && user.getEmail().equals(old_user.getEmail())) {
            MessageResponseDTO message = new MessageResponseDTO(4, "Cannot use the same email");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else {
            userService.updateUser(user);
            MessageResponseDTO message = new MessageResponseDTO(0, "User updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
    }
}
