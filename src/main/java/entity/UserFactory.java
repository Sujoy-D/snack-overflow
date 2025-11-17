package entity;

/**
 * Factory for creating User entities.
 */
public class UserFactory {

    public User create(Integer userId, String username, String email, String passwordHash) {
        return new User(userId, username, email, passwordHash);
    }
}
