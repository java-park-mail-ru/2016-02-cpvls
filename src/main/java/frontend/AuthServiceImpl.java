package frontend;

import base.AuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author polina.artem
 */
@SuppressWarnings("ConstantConditions")
public class AuthServiceImpl implements AuthService {
    private final Map<String, String> userSessions = new HashMap<>();

    @Override
    public String getUserName(String sessionId) {
        return userSessions.get(sessionId);
    }

    @Override
    public void saveUserName(String sessionId, String name) {
        userSessions.put(sessionId, name);
    }
}
