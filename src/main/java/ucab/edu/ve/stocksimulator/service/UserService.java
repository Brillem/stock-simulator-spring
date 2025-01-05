package ucab.edu.ve.stocksimulator.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.UserRepo;
import util.PasswordUtil;

import java.util.ArrayList;
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

    public User findUserByEmail(String email) { return userRepo.findByEmail(email); }

    public User findUserById(Long id) { return userRepo.findById(id).orElse(null); }

    public List<User> getAllUsers() { return userRepo.findAllByAdmin(false); }

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
        userResponseDTO.setAdmin(user.getAdmin());
        return userResponseDTO;
    }
    public List<UserResponseDTO> mapUserListToUserResponseDTOList(List<User> userList) {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user : userList) {
            userResponseDTOList.add(mapUserToUserResponseDTO(user));
        }
        return userResponseDTOList;
    }

    public void sendConfirmationEmail(UserRequestDTO userRequestDTO, String confirmationCode) {
        String subject = "Confirmación de Usuario " + userRequestDTO.getUsername() + " | Stock Simulator";
        String body = "Gracias " + userRequestDTO.getFirstName() + " por unirte a Stock Simulator. El código de confirmación para su usuario " + userRequestDTO.getUsername() + " es "
               + confirmationCode + ". Ingresa al ícono con sus iniciales en la parte superior derecha de la plataforma y haz click en 'Verificar Usuario'.\n\n\nAtentamente, Stock Simulator." ;
        this.emailSenderService.sendEmail(userRequestDTO.getEmail(), subject, body);
    }

    public void createAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setFirstName("Administrador");
        user.setLastName("");
        user.setConfirmationCode(null);
        user.setVerified(true);
        user.setAdmin(true);
        user.setHashedPassword(PasswordUtil.encodePassword("admin12345"));
        user.setEmail("contactstocksimulator@gmail.com");
        userRepo.save(user);
    }
}
