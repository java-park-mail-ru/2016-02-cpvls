package rest;

import org.json.JSONObject;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Singleton
@Path("/user")
public class Users {
    private final AccountService accountService;
    private final SessionService sessionService;

    public Users(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        final Collection<UserProfile> allUsers = accountService.getAllUsers();
        return Response.status(Response.Status.OK).entity(allUsers.toArray(new UserProfile[allUsers.size()])).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") long id, @Context HttpServletRequest request) {

        JSONObject answer = new JSONObject();

        String sessionId = request.getSession().getId();
        UserProfile sessionUser = sessionService.getSessionData(sessionId);

        if( sessionUser == null ){
            return Response.status(Response.Status.UNAUTHORIZED).entity(answer.toString()).build();
        }
        
        answer.put("id", sessionUser.getId());
        answer.put("login", sessionUser.getLogin());
        answer.put("email", sessionUser.getEmail());
        return Response.status(Response.Status.OK).entity(answer.toString()).build();

    }

    @PUT
    @Produces("application/json")
    public Response createUser(@FormParam("login") String login, @FormParam("password") String password, @FormParam("email") String email){

        JSONObject answer = new JSONObject();

        UserProfile user = new UserProfile(login, password, email);
        boolean isCorrectInfo = !(login.isEmpty() || password.isEmpty() || email.isEmpty());
        boolean isUserPossible = isCorrectInfo && !(accountService.isLoginBusy(login));

        if ( !isUserPossible ) {
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        if(accountService.addUser(user)){
            answer.put("id", user.getId());
            return Response.status(Response.Status.OK).entity(answer.toString()).build();
        } else {
            answer.put("error", "Can't add user");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

    }


    @POST
    @Path("{id}")
    @Produces("application/json")
    public Response editUser(@PathParam("id") Long id, @FormParam("login") String login, @FormParam("password") String password, @FormParam("email") String email, @Context HttpServletRequest request){

        JSONObject answer = new JSONObject();

        UserProfile user = accountService.getUser(id);
        if ( user == null ) {
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        boolean isCorrectInfo = !(login.isEmpty() || password.isEmpty() || email.isEmpty());
        boolean isUserPossible = isCorrectInfo && accountService.isLoginBusy(login);

        if ( !isUserPossible ) {
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        String sessionId = request.getSession().getId();
        UserProfile sessionUser = sessionService.getSessionData(sessionId);
        if ( sessionUser == null || sessionUser.getId() != id ) {
            answer.put("status", 403);
            answer.put("message", "Чужой юзер");
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        answer.put("id", user.getId());
        return Response.status(Response.Status.OK).entity(answer.toString()).build();

    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteUser(@PathParam("id") Long id, @Context HttpServletRequest request){

        JSONObject answer = new JSONObject();

        String sessionId = request.getSession().getId();
        UserProfile sessionUser = sessionService.getSessionData(sessionId);

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
