package main;

import rest.UserProfile;

import java.util.Collection;

/**
 * Created by e.shubin on 24.03.2016.
 */

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



public interface AccountService {
    Collection<UserProfile> getAllUsers();
    boolean addUser(UserProfile userProfile);
    UserProfile getUser(String login);
    boolean isLoginBusy(String login);
    UserProfile getUser(long id);

    AtomicLong getGenerator();

    void setGenerator(AtomicLong generator);

//    long generateId();

    void deleteUser(long id);




}


