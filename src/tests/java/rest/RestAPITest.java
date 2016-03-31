package rest;

import entities.UserProfile;
import main.Context;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import services.AccountServiceMapImplDB;
import services.SessionService;
import services.interfaces.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Created by esin on 10.03.2016.
 */
public class RestAPITest extends JerseyTest {

    @Override
    protected Application configure() {
        final Context context = new Context();

        context.put(AccountService.class, new AccountServiceMapImplDB());
        context.put(SessionService.class, new SessionService());

        final HashSet<Class<?>> objects = new HashSet<>();
        objects.add(Users.class);
        objects.add(Session.class);

        final ResourceConfig config = new ResourceConfig(objects);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpSession session = mock(HttpSession.class);
        //noinspection AnonymousInnerClassMayBeStatic
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
                bind(request).to(HttpServletRequest.class);

                bind(session).to(HttpSession.class);
                when(request.getSession()).thenReturn(session);
                when(session.getId()).thenReturn("session");
            }
        });

        UserProfile testData = new UserProfile("admin", "12345", "admin@mail.ru");
        context.get(AccountService.class).addUser(testData);

        testData.setLogin("guest");
        testData.setEmail("guest@mail.ru");
        context.get(AccountService.class).addUser(testData);
        context.get(AccountService.class).addUser(testData);

        return config;
    }

    // ============================================   Авторизация пользователя   ==============

    @Test
    public void testIsAuthorizedSuccess() {
        JSONObject loginData = new JSONObject();
        JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email", "mama@ya.ru");
        rightAnswer.put("id", 1);
        target("session").request().put(Entity.json(loginData.toString()), String.class);
        String ans = target("session").request().get(String.class);
        System.out.print(ans);
        assertEquals(ans, rightAnswer.toString());
    }

    @Test
    public void testIsAuthorizedFail() {
        JSONObject loginData = new JSONObject();
        JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        rightAnswer.put("id", 1);
        Response ans = target("session").request().get(Response.class);
        assertEquals(ans.getStatus(), 401);
    }

    @Test
    public void testLoginSuccess() {
        JSONObject loginData = new JSONObject();
        JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        rightAnswer.put("id", 1);
        final String ans = target("session").request().put(Entity.json(loginData.toString()), String.class);
        assertEquals(ans.toString(), rightAnswer.toString());
    }

    @Test
    public void testLoginFail() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","don't know");
        Response ans = target("session").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 400);
    }

    @Test
    public void testLogout() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","don't know");
        Response ans = target("session").request().delete(Response.class);
        assertEquals(ans.getStatus(), 200);
    }

    @Test
    public void testLogoutAdmin() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        target("session").request().put(Entity.json(loginData.toString()), String.class);
        Response ans = target("session").request().delete(Response.class);
        assertEquals(ans.getStatus(), 200);
    }




    // ============================================   Пользователи   ==============


    @Test
    public void testCreateUserSuccess() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "Brodsky1940@mail.ru");
        Response ans = target("user").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 200);
    }

    @Test
    public void testCreateUserFailEmail() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("password","The end of beautiful age");
        Response ans = target("user").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 403);
    }

    @Test
    public void testCreateUserFailLogin() {
        JSONObject loginData = new JSONObject();
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "Brodsky1940@mail.ru");
        Response ans = target("user").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 403);
    }

    @Test
    public void testCreateUserFailPass() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("email", "Brodsky1940@mail.ru");
        Response ans = target("user").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 403);
    }


    @Test
    public void testCreateUserFailLoginBusy() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "mama@ya.ru");
        Response ans = target("user").request().put(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), 403);
    }


    // ============================================   Конкретный пользователь   ==============

    @Test
    public void testGetAdminSuccess() {

        // login
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().put(Entity.json(loginData.toString()), String.class);

        final String ans = target("user").path("0").request().get(String.class);
        JSONObject rightAnswer = new JSONObject();
        rightAnswer.put("id", 1);
        rightAnswer.put("login", loginData.get("login"));
        rightAnswer.put("email", loginData.get("email"));

        System.out.println(ans);

        assertEquals(rightAnswer.toString(), ans);
    }


    @Test
    public void testGetAdminFail() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");

        Response ans = target("user").path("1").request().get(Response.class);

        assertEquals(ans.getStatus(), 401);
    }



    @Test
    public void testEditAdminSuccess() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().put(Entity.json(loginData.toString()), String.class);

        JSONObject editData = new JSONObject();
        editData.put("email", "newadmin@mail.ru");
        Response ans = target("user").path("1").request().post(Entity.json(editData.toString()), Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), 200);
    }




    @Test
    public void testGetAllUsers() {
        final String ans = target("user").request().get(String.class);
        assertEquals("[{\"email\":\"admin@mail.ru\",\"id\":1,\"login\":\"admin\",\"password\":\"12345\"},{\"email\":\"guest@mail.ru\",\"id\":2,\"login\":\"guest\",\"password\":\"12345\"}]", ans);
    }



    @Test
    public void testDeleteAdminSuccess() {
        JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().put(Entity.json(loginData.toString()), String.class);

        Response ans = target("user").path("1").request().delete(Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), 200);

    }



    @Test
    public void testDeleteAdminFail() {
        Response ans = target("user").path("1").request().delete(Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), 403);
    }
}


