package rest;

import org.json.JSONObject;
import org.json.JSONString;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Iterator;

@Singleton
@Path("/session")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Session {
    private AccountService accountService;
    private SessionService sessionService;

    public Session(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @PUT
    public Response login(String userInput, @Context HttpServletRequest request) {

        JSONObject answer = new JSONObject();
        JSONObject inp = new JSONObject(userInput);

        String login = inp.optString("login");
        String password = inp.optString("password");

        UserProfile foundUser = accountService.getUser(login);

        if ( foundUser != null ) {
            if ( foundUser.getPassword().equals(password) ) {
                String sessionId = request.getSession().getId();
                sessionService.openSession(sessionId, foundUser);

                answer.put("id", foundUser.getId());
                return Response.status(Response.Status.OK).entity(answer.toString()).build();
            }
        }

        answer.put("message", "Incorrect pair login/password");
        return Response.status(Response.Status.BAD_REQUEST).entity(answer.toString()).build();
    }

    @GET
    public Response isAuthorized(@Context HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        UserProfile currentUser = sessionService.getSessionData(sessionId);

        JSONObject answer = new JSONObject();

        if ( currentUser == null ) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(answer.toString()).build();
        }

        answer.put("id", currentUser.getId());
        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

    @DELETE
    public Response logout(@Context HttpServletRequest request) {
        JSONObject answer = new JSONObject();

        String sessionId = request.getSession().getId();
        sessionService.closeSession(sessionId);

        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

}
