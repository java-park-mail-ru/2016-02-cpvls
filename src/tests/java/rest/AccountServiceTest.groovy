package rest

import junit.framework.Test
import main.AccountServiceMapImpl
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import main.AccountService

import javax.inject.Inject
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by polina on 14.03.16.
 */
class AccountServiceTest extends GroovyTestCase {

    private AccountService accountService;
    private SessionService sessionService;

    @Before
    public void setUp() {
        accountService = new AccountServiceMapImpl();
    }

    @Test
    void testIsLoginBusy() {
        assertEquals(true, accountService.isLoginBusy("admin"));
    }

    @Test
    void testIsLoginBusy2() {
        assertEquals(false, accountService.isLoginBusy("admin5"));
    }

    @Test
    void testGetAllUsers() {
 //       Collection<UserProfile> allUsers = accountService.getAllUsers()
   //     assertEquals(true, allUsers.toArray(new UserProfile[allUsers.size()]));
    }

    @Test
    void testAddUser() {
        UserProfile user = new UserProfile("testLogin", "testPass", "test@mail.ru");
        accountService.addUser(user);
        assertEquals(true, accountService.isLoginBusy("testLogin"));
    }

    @Test
    void testGetUser1() {
        long id  = 0;
        UserProfile user = accountService.getUser(id);
        assertEquals("admin", user.getLogin());
    }

    @Test
    void testGetUser() {
        long id  = 0;
        UserProfile user = accountService.getUser(id);
        assertEquals("admin", user.getLogin());
    }

    @Test
    void testDeleteUser() {
        long id  = 0;
        accountService.deleteUser(id);
        assertEquals(null, accountService.getUser(id));
    }
}
