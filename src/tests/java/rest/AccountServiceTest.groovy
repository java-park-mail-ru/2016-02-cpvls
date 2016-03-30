package rest

import junit.framework.Test
import main.AccountServiceMapImpl
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.test.JerseyTest
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import main.AccountService

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.ws.rs.core.Application
import java.util.concurrent.atomic.AtomicLong

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when

/**
 * Created by polina on 14.03.16.
 */
class AccountServiceTest extends JerseyTest {
//
//    @Inject
//    private Context context;

    @Override
    protected Application configure() {
        final Context context = new Context();

        context.put(AccountService.class, new AccountServiceMapImpl());
        context.put(SessionService.class, new SessionService());

        final HashSet<Class<?>> objects = new HashSet<>();
        objects.add(Users.class);
        objects.add(Session.class);

        final ResourceConfig config = new ResourceConfig(objects);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
            }
        });
        return config;
    }




//
//    private AccountService accountService;
//    private SessionService sessionService;

    @Before
    public void setUp() {

        AccountService accountService = context.get(AccountService.class);
//        final AccountService accountService = new AccountServiceMapImpl();
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
