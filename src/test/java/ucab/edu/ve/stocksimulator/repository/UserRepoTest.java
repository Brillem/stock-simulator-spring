package ucab.edu.ve.stocksimulator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ucab.edu.ve.stocksimulator.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("UserRepo Repository Tests")
class UserRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepo userRepo;

    private User testUser1;
    private User testUser2;
    private User adminUser;

    @BeforeEach
    void setUp() {
        // Setup test user 1
        testUser1 = new User();
        testUser1.setUsername("testuser1");
        testUser1.setFirstName("Test");
        testUser1.setLastName("User1");
        testUser1.setEmail("test1@example.com");
        testUser1.setHashedPassword("hashedPassword1");
        testUser1.setVerified(false);
        testUser1.setAdmin(false);

        // Setup test user 2
        testUser2 = new User();
        testUser2.setUsername("testuser2");
        testUser2.setFirstName("Test");
        testUser2.setLastName("User2");
        testUser2.setEmail("test2@example.com");
        testUser2.setHashedPassword("hashedPassword2");
        testUser2.setVerified(true);
        testUser2.setAdmin(false);

        // Setup admin user
        adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setEmail("admin@example.com");
        adminUser.setHashedPassword("hashedPasswordAdmin");
        adminUser.setVerified(true);
        adminUser.setAdmin(true);

        // Persist test data
        entityManager.persist(testUser1);
        entityManager.persist(testUser2);
        entityManager.persist(adminUser);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should find user by username")
    void testFindByUsername() {
        // Act
        User foundUser = userRepo.findByUsername("testuser1");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser1", foundUser.getUsername());
        assertEquals("test1@example.com", foundUser.getEmail());
        assertEquals("Test", foundUser.getFirstName());
    }

    @Test
    @DisplayName("Should return null when user not found by username")
    void testFindByUsernameNotFound() {
        // Act
        User foundUser = userRepo.findByUsername("nonexistent");

        // Assert
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        // Act
        User foundUser = userRepo.findByEmail("test2@example.com");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser2", foundUser.getUsername());
        assertEquals("test2@example.com", foundUser.getEmail());
        assertTrue(foundUser.getVerified());
    }

    @Test
    @DisplayName("Should return null when user not found by email")
    void testFindByEmailNotFound() {
        // Act
        User foundUser = userRepo.findByEmail("nonexistent@example.com");

        // Assert
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Should check if user exists by username")
    void testExistsByUsername() {
        // Act
        boolean exists = userRepo.existsByUsername("testuser1");
        boolean notExists = userRepo.existsByUsername("nonexistent");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should check if user exists by email")
    void testExistsByEmail() {
        // Act
        boolean exists = userRepo.existsByEmail("test1@example.com");
        boolean notExists = userRepo.existsByEmail("nonexistent@example.com");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should find all non-admin users")
    void testFindAllByAdminFalse() {
        // Act
        List<User> nonAdminUsers = userRepo.findAllByAdmin(false);

        // Assert
        assertNotNull(nonAdminUsers);
        assertEquals(2, nonAdminUsers.size());
        assertTrue(nonAdminUsers.stream().noneMatch(User::getAdmin));
        assertTrue(nonAdminUsers.stream().anyMatch(u -> u.getUsername().equals("testuser1")));
        assertTrue(nonAdminUsers.stream().anyMatch(u -> u.getUsername().equals("testuser2")));
    }

    @Test
    @DisplayName("Should find all admin users")
    void testFindAllByAdminTrue() {
        // Act
        List<User> adminUsers = userRepo.findAllByAdmin(true);

        // Assert
        assertNotNull(adminUsers);
        assertEquals(1, adminUsers.size());
        assertTrue(adminUsers.get(0).getAdmin());
        assertEquals("admin", adminUsers.get(0).getUsername());
    }

    @Test
    @DisplayName("Should save new user")
    void testSaveNewUser() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setEmail("new@example.com");
        newUser.setHashedPassword("hashedPasswordNew");
        newUser.setVerified(false);
        newUser.setAdmin(false);

        // Act
        User savedUser = userRepo.save(newUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("newuser", savedUser.getUsername());

        // Verify it's actually in the database
        User foundUser = userRepo.findByUsername("newuser");
        assertNotNull(foundUser);
        assertEquals("new@example.com", foundUser.getEmail());
    }

    @Test
    @DisplayName("Should update existing user")
    void testUpdateUser() {
        // Arrange
        User existingUser = userRepo.findByUsername("testuser1");
        existingUser.setFirstName("Updated");
        existingUser.setVerified(true);

        // Act
        User updatedUser = userRepo.save(existingUser);

        // Assert
        assertEquals("Updated", updatedUser.getFirstName());
        assertTrue(updatedUser.getVerified());

        // Verify the change persisted
        User foundUser = userRepo.findByUsername("testuser1");
        assertEquals("Updated", foundUser.getFirstName());
        assertTrue(foundUser.getVerified());
    }

    @Test
    @DisplayName("Should delete user")
    void testDeleteUser() {
        // Arrange
        User userToDelete = userRepo.findByUsername("testuser1");
        assertNotNull(userToDelete);

        // Act
        userRepo.delete(userToDelete);
        entityManager.flush();

        // Assert
        User deletedUser = userRepo.findByUsername("testuser1");
        assertNull(deletedUser);
        assertFalse(userRepo.existsByUsername("testuser1"));
    }

    @Test
    @DisplayName("Should find user by ID")
    void testFindById() {
        // Arrange
        User existingUser = userRepo.findByUsername("testuser1");
        Long userId = existingUser.getId();

        // Act
        User foundUser = userRepo.findById(userId).orElse(null);

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser1", foundUser.getUsername());
    }

    @Test
    @DisplayName("Should find all users")
    void testFindAll() {
        // Act
        List<User> allUsers = userRepo.findAll();

        // Assert
        assertNotNull(allUsers);
        assertEquals(3, allUsers.size());
    }
}
