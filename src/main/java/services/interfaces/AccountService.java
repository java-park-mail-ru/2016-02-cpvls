package services.interfaces;

import entities.UserProfile;

import java.util.Collection;


@SuppressWarnings("unused")
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


