package main;

import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AccountService {
    private Map<Long, UserProfile> users = new HashMap<>();
    private HashSet<String> busyLogins = new HashSet<String>();

    public AccountService() {
        addUser(new UserProfile("admin", "12345", "admin@mail.ru"));
        addUser(new UserProfile("guest", "12345", "guest@mail.ru"));
    }

    public boolean isLoginBusy(String login) {
        return busyLogins.contains(login);
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(UserProfile userProfile) {
        if ( isLoginBusy(userProfile.getLogin()) )
            return false;

        userProfile.setId( userProfile.generateId() );
        users.put(userProfile.getId(), userProfile);

        busyLogins.add(userProfile.getLogin());

        return true;
    }

    public UserProfile getUser(long id) {
        return users.get(id);
    }
}
