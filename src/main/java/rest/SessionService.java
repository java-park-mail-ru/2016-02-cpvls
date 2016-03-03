package rest;

import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SessionService {
    private Map<String, UserProfile> currentSessions = new HashMap<>();

    public SessionService() {

    }

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
