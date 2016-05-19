package rest;

import cfg.Configs;
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

public class RestAPITest extends JerseyTest {

    @Override
    protected Application configure() {
        final Context context = new Context();

        Configs conf = new Configs();

        context.put(AccountService.class, new AccountServiceMapImplDB(conf));
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

        final UserProfile testData = new UserProfile("admin", "12345", "admin@mail.ru");
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
        final JSONObject loginData = new JSONObject();
        final JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email", "mama@ya.ru");
        rightAnswer.put("id", 1);
        target("session").request().post(Entity.json(loginData.toString()), String.class);
        final String ans = target("session").request().get(String.class);
        System.out.print(ans);
        assertEquals(ans, rightAnswer.toString());
    }

    @Test
    public void testIsAuthorizedFail() {
        final JSONObject loginData = new JSONObject();
        final JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        rightAnswer.put("id", 1);
        final Response ans = target("session").request().get(Response.class);
        assertEquals(ans.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void testLoginSuccess() {
        final JSONObject loginData = new JSONObject();
        final JSONObject rightAnswer = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        rightAnswer.put("id", 1);
        final String ans = target("session").request().post(Entity.json(loginData.toString()), String.class);
        assertEquals(ans, rightAnswer.toString());
    }

    @Test
    public void testLoginFail() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","don't know");
        final Response ans = target("session").request().post(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testLogout() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","don't know");
        final Response ans = target("session").request().delete(Response.class);
        assertEquals(ans.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testLogoutAdmin() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        target("session").request().post(Entity.json(loginData.toString()), String.class);
        final Response ans = target("session").request().delete(Response.class);
        assertEquals(ans.getStatus(), Response.Status.OK.getStatusCode());
    }




    // ============================================   Пользователи   ==============


    @Test
    public void testCreateUserSuccess() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "Brodsky1940@mail.ru");
        final Response ans = target("user").request().post(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testCreateUserFailEmail() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("password","The end of beautiful age");
        final Response ans = target("user").request().post(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void testCreateUserFailLogin() {
        final JSONObject loginData = new JSONObject();
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "Brodsky1940@mail.ru");
        final Response ans = target("user").request().post(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void testCreateUserFailPass() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "Brodsky");
        loginData.put("email", "Brodsky1940@mail.ru");
        final Response ans = target("user").request().post(Entity.json(loginData.toString()), Response.class);
        assertEquals(ans.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }


    @Test
    public void testCreateUserFailLoginBusy() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","The end of beautiful age");
        loginData.put("email", "mama@ya.ru");
        final Response ans = target("user").request().post(Entity.json(loginData.toString()), Response.class);

        assertEquals(ans.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }


    // ============================================   Конкретный пользователь   ==============

    @Test
    public void testGetAdminSuccess() {

        // login
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().post(Entity.json(loginData.toString()), String.class);

        final String ans = target("user").path("0").request().get(String.class);
        final JSONObject rightAnswer = new JSONObject();
        rightAnswer.put("id", 1);
        rightAnswer.put("login", loginData.get("login"));
        rightAnswer.put("email", loginData.get("email"));

        System.out.println(ans);

        assertEquals(rightAnswer.toString(), ans);
    }


    @Test
    public void testGetAdminFail() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");

        final Response ans = target("user").path("1").request().get(Response.class);

        assertEquals(ans.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }



    @Test
    public void testEditAdminSuccess() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().post(Entity.json(loginData.toString()), String.class);

        final JSONObject editData = new JSONObject();
        editData.put("email", "newadmin@mail.ru");
        final Response ans = target("user").path("1").request().put(Entity.json(editData.toString()), Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), Response.Status.OK.getStatusCode());
    }




    @Test
    public void testGetAllUsers() {
        final String ans = target("user").request().get(String.class);
        assertEquals("[{\"email\":\"admin@mail.ru\",\"id\":1,\"login\":\"admin\",\"password\":\"12345\"},{\"email\":\"guest@mail.ru\",\"id\":2,\"login\":\"guest\",\"password\":\"12345\"}]", ans);
    }



    @Test
    public void testDeleteAdminSuccess() {
        final JSONObject loginData = new JSONObject();
        loginData.put("login", "admin");
        loginData.put("password","12345");
        loginData.put("email","admin@mail.ru");
        target("session").request().post(Entity.json(loginData.toString()), String.class);

        final Response ans = target("user").path("1").request().delete(Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), Response.Status.OK.getStatusCode());

    }



    @Test
    public void testDeleteAdminFail() {
        final Response ans = target("user").path("1").request().delete(Response.class);
        System.out.print(ans.getStatusInfo());
        assertEquals(ans.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
    }
}


