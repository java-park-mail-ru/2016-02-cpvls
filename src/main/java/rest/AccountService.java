package rest;

import org.eclipse.jetty.server.Authentication;
import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AccountService {
    private Map<Long, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> usersByLogins = new HashMap<>();

    public AccountService() {
        addUser(new UserProfile("admin", "12345", "admin@mail.ru"));
        addUser(new UserProfile("guest", "12345", "guest@mail.ru"));
    }

    public boolean isLoginBusy(String login) {
        return usersByLogins.containsKey(login);
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(UserProfile userProfile) {
        if ( isLoginBusy(userProfile.getLogin()) )
            return false;

        userProfile.setId( userProfile.generateId() );
        users.put(userProfile.getId(), userProfile);
        usersByLogins.put(userProfile.getLogin(), userProfile);

        return true;
    }

    public UserProfile getUser(long id) {
        return users.get(id);
    }

    public void deleteUser(long id) {
        users.remove(id);
    }
}
