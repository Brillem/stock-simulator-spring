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

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value= "/register", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> registerUser(@RequestBody UserRequestDTO user) {
        System.out.println(user.getFirstName() + " " + user.getLastName());
        if (userService.userExistsByUsername(user.getUsername())) {
            System.out.println("existo");
            MessageResponseDTO message = new MessageResponseDTO(1, "Usuario ya existe");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            User createdUser = userService.mapUserRequestDTOToUser(user);
            userService.createUser(createdUser);
            MessageResponseDTO message = new MessageResponseDTO(0, "Usuario creado exitosamente");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
    }
}
