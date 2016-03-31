package services.interfaces;

import entities.UserProfile;

import java.util.Collection;

/**
 * Created by e.shubin on 24.03.2016.
 */
public interface AccountService {
    Collection<UserProfile> getAllUsers();

    boolean isLoginBusy(String login);
    boolean isEmailBusy(String email);

    long addUser(UserProfile userProfile);
    long editUser(long id, UserProfile userProfile);
    void deleteUser(long id);

    UserProfile getUser(String login);
    UserProfile getUser(long id);
}


