package base;

/**
 * @author polina
 */
public interface AuthService {
    String getUserName(String sessionId);

    void saveUserName(String sessionId, String name);
}
