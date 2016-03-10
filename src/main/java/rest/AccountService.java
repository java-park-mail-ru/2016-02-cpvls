package rest;

import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.UserProfile;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Singleton
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

    public UserProfile getUser(String login) {
        return usersByLogins.get(login);
    }

    public void deleteUser(long id) {
        users.remove(id);
    }

}
