package entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    void testCreate_ValidInputs() {
        // Arrange
        Integer userId = 1;
        String username = "testuser";
        String email = "test@email.com";
        String passwordHash = "hashedpassword";

        // Act
        User user = userFactory.create(userId, username, email, passwordHash);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertNotNull(user.getSavedRecipes());
        assertTrue(user.getSavedRecipes().isEmpty());
        assertNotNull(user.getCustomTags());
        assertTrue(user.getCustomTags().isEmpty());
        assertNotNull(user.getMealPlan());
        assertTrue(user.getMealPlan().isEmpty());
    }

    @Test
    void testCreate_NullEmail() {
        // Arrange
        Integer userId = 1;
        String username = "testuser";
        String email = null;
        String passwordHash = "hashedpassword";

        // Act
        User user = userFactory.create(userId, username, email, passwordHash);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertNull(user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void testCreate_EmptyEmail() {
        // Arrange
        Integer userId = 1;
        String username = "testuser";
        String email = "";
        String passwordHash = "hashedpassword";

        // Act
        User user = userFactory.create(userId, username, email, passwordHash);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void testCreate_NullUserId() {
        // Arrange
        Integer userId = null;
        String username = "testuser";
        String email = "test@email.com";
        String passwordHash = "hashedpassword";

        // Act
        User user = userFactory.create(userId, username, email, passwordHash);

        // Assert
        assertNotNull(user);
        assertNull(user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void testCreate_InvalidUsername_ThrowsException() {
        // Arrange
        Integer userId = 1;
        String username = "";
        String email = "test@email.com";
        String passwordHash = "hashedpassword";

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userFactory.create(userId, username, email, passwordHash);
        });

        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void testCreate_InvalidPassword_ThrowsException() {
        // Arrange
        Integer userId = 1;
        String username = "testuser";
        String email = "test@email.com";
        String passwordHash = "";

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userFactory.create(userId, username, email, passwordHash);
        });

        assertEquals("Password cannot be empty", thrown.getMessage());
    }
}
