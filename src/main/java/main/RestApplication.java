package main;

import rest.AccountService;
import rest.SessionService;
import rest.Users;
import rest.Session;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationPath("api")
public class RestApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        final HashSet<Object> objects = new HashSet<>();

        final AccountService accountService = new AccountService();
        final SessionService sessionService = new SessionService();

        objects.add(new Users(accountService, sessionService));
        objects.add(new Session(accountService, sessionService));
        return objects;
    }
}
