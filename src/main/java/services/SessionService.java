package services;

import entities.UserProfile;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class SessionService {
    private final Map<String, UserProfile> currentSessions = new HashMap<>();

    public void openSession(String id, UserProfile user) {
        currentSessions.put(id, user);
    }

    public void closeSession(String id) {
        currentSessions.remove(id);
    }

    public UserProfile getSessionData(String id) {
        return currentSessions.get(id);
    }
}
