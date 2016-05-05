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


        UserProfile testData = new UserProfile("admin", "12345", "admin@mail.ru");
        accountService.addUser(testData);

        testData.setLogin("guest");
        testData.setEmail("guest@mail.ru");
        accountService.addUser(testData);

    }

    @Test
    void testIsLoginBusy() {
        assertEquals(true, accountService.isLoginBusy("admin"));
    }

    @Test
    void testIsLoginBusy2() {
        assertEquals(false, accountService.isLoginBusy("guest2"));
    }

    @Test
    void testAddUser() {
        UserProfile user = new UserProfile("testLogin", "testPass", "test@mail.ru");
        accountService.addUser(user);
        assertEquals(true, accountService.isLoginBusy("testLogin"));
    }

    @Test
    void testGetUser1() {
        long id  = 1;
        UserProfile user = accountService.getUser(id);
        assertEquals("admin", user.getLogin());
    }

    @Test
    void testGetUser() {
        long id  = 5;
        UserProfile user = accountService.getUser(id);
        assertEquals("", user.getLogin());
    }

    @Test
    void testDeleteUser() {
        long id  = 1;
        accountService.deleteUser(id);
        assertEquals("", accountService.getUser(id).getLogin());
    }
}
