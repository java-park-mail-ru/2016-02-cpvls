package rest

import entities.UserProfile
import org.junit.Before
import org.junit.Test
import services.AccountServiceMapImplDB
import services.SessionService
import services.interfaces.AccountService

/**
 * Created by polina on 14.03.16.
 */
class AccountServiceTest extends GroovyTestCase {

    private AccountService accountService;
    private SessionService sessionService;

    @Before
    public void setUp() {
        accountService = new AccountServiceMapImplDB();

        UserProfile user = new UserProfile("testLogin", "testPass", "test@mail.ru");
        accountService.addUser(user);
    }

    @Test
    void testAddUser() {
        UserProfile user = new UserProfile("testLogin2", "testPass2", "test2@mail.ru");
        accountService.addUser(user);
        assertEquals(true, accountService.isLoginBusy("testLogin2"));
    }

    @Test
    void testIsLoginBusy() {
        assertEquals(false, accountService.isLoginBusy("admin"));
    }

    @Test
    void testIsLoginBusy2() {
        assertEquals(true, accountService.isLoginBusy("testLogin"));
    }

    @Test
    void testGetAllUsers() {
 //       Collection<UserProfile> allUsers = accountService.getAllUsers()
   //     assertEquals(true, allUsers.toArray(new UserProfile[allUsers.size()]));
    }

    @Test
    void testGetUser() {
        UserProfile user = accountService.getUser("testLogin");
        assertEquals("testLogin", user.getLogin());
    }

    @Test
    void testDeleteUser() {
        long id  = accountService.getUser("testLogin").getId();
        accountService.deleteUser(id);
        assertEquals("", accountService.getUser(id).getLogin());
    }
}
