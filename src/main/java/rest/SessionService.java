package rest;

import rest.UserProfile;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Singleton
public class SessionService {
    private Map<String, UserProfile> currentSessions = new HashMap<>();

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
