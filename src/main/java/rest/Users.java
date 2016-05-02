package rest;

import entities.UserProfile;
import org.json.JSONObject;
import services.SessionService;
import services.interfaces.AccountService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Singleton
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class Users {
    @Inject
    private main.Context context;

    @GET
    public Response getAllUsers() {
        final AccountService accountService = context.get(AccountService.class);
        final Collection<UserProfile> allUsers = accountService.getAllUsers();
        return Response.status(Response.Status.OK).entity(allUsers.toArray(new UserProfile[allUsers.size()])).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") long id, @Context HttpServletRequest request) {

        final JSONObject answer = new JSONObject();
        final SessionService sessionService = context.get(SessionService.class);

        final String sessionId = request.getSession().getId();
        final UserProfile sessionUser = sessionService.getSessionData(sessionId);

        if( sessionUser == null ){
            return Response.status(Response.Status.UNAUTHORIZED).entity(answer.toString()).build();
        }

        answer.put("id", sessionUser.getId());
        answer.put("login", sessionUser.getLogin());
        answer.put("email", sessionUser.getEmail());
        return Response.status(Response.Status.OK).entity(answer.toString()).build();

    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response createUser(String userInput, @Context HttpServletRequest request){

        final AccountService accountService = context.get(AccountService.class);
        final JSONObject answer = new JSONObject();
        final JSONObject inp = new JSONObject(userInput);

        final String login = inp.optString("login");
        final String password = inp.optString("password");
        final String email = inp.optString("email");

        if ( login.isEmpty() || password.isEmpty() || email.isEmpty() ) {
            answer.put("error", "Должны быть указаны поля логин, пароль и почта.");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        final UserProfile user = new UserProfile(login, password, email);
        final boolean isUserPossible = !(accountService.isLoginBusy(login));

        if ( !isUserPossible ) {
            answer.put("error", "Логин занят");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        final long userId = accountService.addUser(user);
        if(userId != -1){
            answer.put("id", userId);
            return Response.status(Response.Status.OK).entity(answer.toString()).build();
        } else {
            answer.put("message", "Не удалось создать пользователя.");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }
    }


    @PUT
    @Path("{id}")
    public Response editUser(@PathParam("id") Long id, String userInput, @Context HttpServletRequest request){

        final AccountService accountService = context.get(AccountService.class);
        final SessionService sessionService = context.get(SessionService.class);
        final JSONObject inp = new JSONObject(userInput);
        final JSONObject answer = new JSONObject();

        final UserProfile user = accountService.getUser(id);
        if ( user == null ) {
            answer.put("message", "Необходима авторизация.");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        final String login = inp.optString("login");
        final String password = inp.optString("password");
        final String email = inp.optString("email");

        if( ! login.isEmpty() ) {
            if ( accountService.isLoginBusy(login) ) {
                answer.put("error", "Логин занят");
                return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
            }
        }

        final String sessionId = request.getSession().getId();
        final UserProfile sessionUser = sessionService.getSessionData(sessionId);
        if ( sessionUser == null || sessionUser.getId() != id ) {
            answer.put("status", 403);
            answer.put("message", "Чужой юзер");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        if ( !login.isEmpty() )
            user.setLogin(login);
        if ( !password.isEmpty() )
            user.setPassword(password);
        if ( !email.isEmpty() )
            user.setEmail(email);

        final long resultId = accountService.editUser(id, user);
        answer.put("id", resultId);
        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id, @Context HttpServletRequest request){

        final AccountService accountService = context.get(AccountService.class);
        final SessionService sessionService = context.get(SessionService.class);
        final JSONObject answer = new JSONObject();

        final String sessionId = request.getSession().getId();
        final UserProfile sessionUser = sessionService.getSessionData(sessionId);

        if ( sessionUser == null || sessionUser.getId() != id ) {
            answer.put("status", 403);
            answer.put("message", "Чужой юзер");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        accountService.deleteUser(id);
        sessionService.closeSession(sessionId);

        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

}
