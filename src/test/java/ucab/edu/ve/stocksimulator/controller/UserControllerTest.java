package ucab.edu.ve.stocksimulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import ucab.edu.ve.stocksimulator.dto.request.ConfirmUserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.service.ContactFormService;
import ucab.edu.ve.stocksimulator.service.EmailSenderService;
import ucab.edu.ve.stocksimulator.service.TransactionService;
import ucab.edu.ve.stocksimulator.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser
@DisplayName("UserController Integration Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private EmailSenderService emailSenderService;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private ContactFormService contactFormService;

    private UserRequestDTO userRequestDTO;
    private User testUser;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        // Setup test user request DTO
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testuser");

        // Setup test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@example.com");
        testUser.setHashedPassword("hashedPassword123");
        testUser.setVerified(false);
        testUser.setAdmin(false);
        testUser.setConfirmationCode("ABC123");

        // Setup test user response DTO
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testuser");
        userResponseDTO.setFirstName("Test");
        userResponseDTO.setLastName("User");
        userResponseDTO.setEmail("test@example.com");
        userResponseDTO.setVerified(false);
        userResponseDTO.setAdmin(false);
    }

    @Test
    @DisplayName("Should register new user successfully")
    void testRegisterUserSuccess() throws Exception {
        // Arrange
        when(userService.userExistsByUsername(anyString())).thenReturn(false);
        when(userService.userExistsByEmail(anyString())).thenReturn(false);
        when(userService.mapUserRequestDTOToUser(any(UserRequestDTO.class))).thenReturn(testUser);
        when(userService.createUser(any(User.class))).thenReturn(testUser);
        when(userService.mapUserToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        doNothing().when(userService).sendConfirmationEmail(any(UserRequestDTO.class), anyString());

        // Act & Assert
        mockMvc.perform(post("/api/user/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).createUser(any(User.class));
        verify(userService, times(1)).sendConfirmationEmail(any(UserRequestDTO.class), anyString());
    }

    @Test
    @DisplayName("Should return error when username already exists during registration")
    void testRegisterUserUsernameExists() throws Exception {
        // Arrange
        when(userService.userExistsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/user/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("User already exists"));

        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    @DisplayName("Should return error when email already exists during registration")
    void testRegisterUserEmailExists() throws Exception {
        // Arrange
        when(userService.userExistsByUsername(anyString())).thenReturn(false);
        when(userService.userExistsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/user/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.message").value("Email already exists"));

        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    @DisplayName("Should login user successfully")
    void testLoginUserSuccess() throws Exception {
        // Arrange
        when(userService.userExistsByUsername("testuser")).thenReturn(true);
        when(userService.findUserByUsername("testuser")).thenReturn(testUser);
        when(userService.mapUserToUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/user/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).findUserByUsername("testuser");
    }

    @Test
    @DisplayName("Should return error when user doesn't exist during login")
    void testLoginUserNotExists() throws Exception {
        // Arrange
        when(userService.userExistsByUsername("testuser")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/user/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2))
                .andExpect(jsonPath("$.message").value("User doesn't exist"));

        verify(userService, never()).findUserByUsername(anyString());
    }

    @Test
    @DisplayName("Should confirm user successfully")
    void testConfirmUserSuccess() throws Exception {
        // Arrange
        String confirmRequestJson = "{\"username\":\"testuser\",\"confirmationCode\":\"ABC123\"}";

        when(userService.findUserByUsername("testuser")).thenReturn(testUser);
        when(userService.updateUser(any(User.class))).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(post("/api/user/confirm")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(confirmRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("User confirmed Successfully"));

        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    @DisplayName("Should return error when confirmation code is incorrect")
    void testConfirmUserIncorrectCode() throws Exception {
        // Arrange
        String confirmRequestJson = "{\"username\":\"testuser\",\"confirmationCode\":\"WRONG123\"}";

        when(userService.findUserByUsername("testuser")).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(post("/api/user/confirm")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(confirmRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Incorrect confirmation code"));

        verify(userService, never()).updateUser(any(User.class));
    }

    @Test
    @DisplayName("Should get all users")
    void testGetAllUsers() throws Exception {
        // Arrange
        UserResponseDTO user2 = new UserResponseDTO();
        user2.setUsername("user2");
        user2.setFirstName("User");
        user2.setLastName("Two");
        user2.setEmail("user2@example.com");
        user2.setVerified(true);
        user2.setAdmin(false);

        List<User> users = Arrays.asList(testUser, new User());
        List<UserResponseDTO> userResponses = Arrays.asList(userResponseDTO, user2);

        when(userService.getAllUsers()).thenReturn(users);
        when(userService.mapUserListToUserResponseDTOList(users)).thenReturn(userResponses);

        // Act & Assert
        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[1].username").value("user2"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Should delete user successfully")
    void testRemoveUser() throws Exception {
        // Arrange
        doNothing().when(transactionService).deleteUserInTransactions("testuser");
        doNothing().when(contactFormService).deleteAllUserForms("testuser");
        doNothing().when(userService).removeUser("testuser");

        // Act & Assert
        mockMvc.perform(post("/api/user/delete")
                .with(csrf())
                .param("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("User removed successfully"));

        verify(transactionService, times(1)).deleteUserInTransactions("testuser");
        verify(contactFormService, times(1)).deleteAllUserForms("testuser");
        verify(userService, times(1)).removeUser("testuser");
    }
}
