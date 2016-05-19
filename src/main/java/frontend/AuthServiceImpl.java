package frontend;

import base.AuthService;

import java.util.HashMap;
import java.util.Map;



public class AuthServiceImpl implements AuthService {
    private Map<String, String> userSessions = new HashMap<>();

    public String getUserName(String sessionId) {
        return userSessions.get(sessionId);
    }

    public void saveUserName(String sessionId, String name) {
        userSessions.put(sessionId, name);
        System.out.print("Seesion = " + userSessions.toString() + "\n");
    }
}
