package ucab.edu.ve.stocksimulator.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.UserRepo;
import util.PasswordUtil;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final EmailSenderService emailSenderService;

    @Autowired
    public UserService(UserRepo userRepo, EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(User user) { return userRepo.save(user); }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean userExistsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean userExistsByEmail(String email) { return userRepo.existsByEmail(email); }


    public User mapUserRequestDTOToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setUsername(userRequestDTO.getUsername());
        user.setHashedPassword(PasswordUtil.encodePassword(userRequestDTO.getPassword()));
        user.setEmail(userRequestDTO.getEmail());
        return user;
    }

    public UserResponseDTO mapUserToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setVerified(user.getVerified());
        return userResponseDTO;
    }

    public void sendConfirmationEmail(UserRequestDTO userRequestDTO, String confirmationCode) {
        String subject = "Confirmación de Usuario " + userRequestDTO.getUsername() + " | Stock Simulator";
        String body = "Gracias " + userRequestDTO.getFirstName() + " por unirte a Stock Simulator. El código de confirmación para ingresar con su usuario " + userRequestDTO.getUsername() + " es "
               + confirmationCode + ".\n\n" ;
        this.emailSenderService.sendEmail(userRequestDTO.getEmail(), subject, body);
    }
}
