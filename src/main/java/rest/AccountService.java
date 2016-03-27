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
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class AccountService {
    private Map<Long, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> usersByLogins = new HashMap<>();

    private static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public AtomicLong getGenerator() {
        return ID_GENERATOR;
    }

    public void setGenerator(AtomicLong generator) {
        ID_GENERATOR = generator;
    }

    public AccountService() {
        ID_GENERATOR = new AtomicLong(0);
        addUser(new UserProfile("admin", "12345", "admin@mail.ru"));
        addUser(new UserProfile("guest", "12345", "guest@mail.ru"));
    }

    public long generateId() { return ID_GENERATOR.getAndIncrement(); }

    public boolean isLoginBusy(String login) {
        return usersByLogins.containsKey(login);
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(UserProfile userProfile) {
        if ( isLoginBusy(userProfile.getLogin()) )
            return false;

        userProfile.setId( generateId() );
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
