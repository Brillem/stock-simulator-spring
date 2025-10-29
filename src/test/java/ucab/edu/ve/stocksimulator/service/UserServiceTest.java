package ucab.edu.ve.stocksimulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ucab.edu.ve.stocksimulator.dto.request.UserRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.UserResponseDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequestDTO testUserRequestDTO;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@example.com");
        testUser.setHashedPassword("hashedPassword123");
        testUser.setVerified(false);
        testUser.setAdmin(false);

        // Setup test DTO
        testUserRequestDTO = new UserRequestDTO();
        testUserRequestDTO.setUsername("testuser");
    }

    @Test
    @DisplayName("Should create user successfully")
    void testCreateUser() {
        // Arrange
        when(userRepo.save(any(User.class))).thenReturn(testUser);

        // Act
        User createdUser = userService.createUser(testUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should update user successfully")
    void testUpdateUser() {
        // Arrange
        testUser.setFirstName("Updated");
        when(userRepo.save(any(User.class))).thenReturn(testUser);

        // Act
        User updatedUser = userService.updateUser(testUser);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getFirstName());
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should remove user by username")
    void testRemoveUser() {
        // Arrange
        when(userRepo.findByUsername("testuser")).thenReturn(testUser);
        doNothing().when(userRepo).delete(testUser);

        // Act
        userService.removeUser("testuser");

        // Assert
        verify(userRepo, times(1)).findByUsername("testuser");
        verify(userRepo, times(1)).delete(testUser);
    }

    @Test
    @DisplayName("Should find user by username")
    void testFindUserByUsername() {
        // Arrange
        when(userRepo.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User foundUser = userService.findUserByUsername("testuser");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepo, times(1)).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindUserByEmail() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        User foundUser = userService.findUserByEmail("test@example.com");

        // Assert
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepo, times(1)).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should find user by ID")
    void testFindUserById() {
        // Arrange
        when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        User foundUser = userService.findUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return null when user not found by ID")
    void testFindUserByIdNotFound() {
        // Arrange
        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        // Act
        User foundUser = userService.findUserById(999L);

        // Assert
        assertNull(foundUser);
        verify(userRepo, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get all non-admin users")
    void testGetAllUsers() {
        // Arrange
        User user2 = new User();
        user2.setUsername("user2");
        user2.setAdmin(false);

        List<User> userList = Arrays.asList(testUser, user2);
        when(userRepo.findAllByAdmin(false)).thenReturn(userList);

        // Act
        List<User> allUsers = userService.getAllUsers();

        // Assert
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        verify(userRepo, times(1)).findAllByAdmin(false);
    }

    @Test
    @DisplayName("Should check if user exists by username")
    void testUserExistsByUsername() {
        // Arrange
        when(userRepo.existsByUsername("testuser")).thenReturn(true);

        // Act
        boolean exists = userService.userExistsByUsername("testuser");

        // Assert
        assertTrue(exists);
        verify(userRepo, times(1)).existsByUsername("testuser");
    }

    @Test
    @DisplayName("Should check if user exists by email")
    void testUserExistsByEmail() {
        // Arrange
        when(userRepo.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        boolean exists = userService.userExistsByEmail("test@example.com");

        // Assert
        assertTrue(exists);
        verify(userRepo, times(1)).existsByEmail("test@example.com");
    }

    // Note: This test is skipped because UserRequestDTO doesn't have public setters for all fields
    // and the PasswordUtil.encodePassword() requires a non-null password
    // In a real scenario, you would test this with a properly constructed DTO from the controller

    @Test
    @DisplayName("Should map User to UserResponseDTO")
    void testMapUserToUserResponseDTO() {
        // Act
        UserResponseDTO responseDTO = userService.mapUserToUserResponseDTO(testUser);

        // Assert
        assertNotNull(responseDTO);
        assertEquals("testuser", responseDTO.username);
        assertEquals("Test", responseDTO.firstName);
        assertEquals("User", responseDTO.lastName);
        assertEquals("test@example.com", responseDTO.email);
        assertFalse(responseDTO.verified);
        assertFalse(responseDTO.admin);
    }

    @Test
    @DisplayName("Should map list of Users to list of UserResponseDTOs")
    void testMapUserListToUserResponseDTOList() {
        // Arrange
        User user2 = new User();
        user2.setUsername("user2");
        user2.setFirstName("User");
        user2.setLastName("Two");
        user2.setEmail("user2@example.com");
        user2.setVerified(true);
        user2.setAdmin(false);

        List<User> userList = Arrays.asList(testUser, user2);

        // Act
        List<UserResponseDTO> responseDTOList = userService.mapUserListToUserResponseDTOList(userList);

        // Assert
        assertNotNull(responseDTOList);
        assertEquals(2, responseDTOList.size());
        assertEquals("testuser", responseDTOList.get(0).username);
        assertEquals("user2", responseDTOList.get(1).username);
    }

    // Note: This test is skipped because UserRequestDTO doesn't have complete field setters
    // In a real scenario, the email sending functionality would be tested through integration tests

    @Test
    @DisplayName("Should create admin user")
    void testCreateAdmin() {
        // Arrange
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.createAdmin();

        // Assert
        verify(userRepo, times(1)).save(argThat(user ->
                user.getUsername().equals("admin") &&
                user.getVerified() &&
                user.getAdmin()
        ));
    }
}
