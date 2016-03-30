package main;

import main.AccountService;
import rest.Context;
import rest.SessionService;
import rest.Users;
import rest.Session;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationPath("api")
public class RestApplication extends Application {

    @Inject
    private Context context;

    @Override
    public Set<Object> getSingletons() {
        final HashSet<Object> objects = new HashSet<>();

        final AccountService accountService = new AccountServiceMapImpl();
        final SessionService sessionService = new SessionService();

        context.put(AccountService.class, accountService);
        context.put(SessionService.class, sessionService);

        objects.add(new Users());
        objects.add(new Session());
        return objects;
    }
}
