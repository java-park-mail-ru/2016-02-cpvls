package rest;

import org.json.JSONObject;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Iterator;

@Singleton
@Path("/session")
public class Session {
    private AccountService accountService;
    private SessionService sessionService;

    public Session(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @PUT
    @Path("")
    @Produces("application/json")
    public Response login(@FormParam("login") String login, @FormParam("password") String password, @Context HttpServletRequest request) {

        boolean userFound = false;
        UserProfile currentUser = new UserProfile();

        Iterator<UserProfile> it = accountService.getAllUsers().iterator();
        while ( it.hasNext() && !userFound ) {
            currentUser = it.next();
            if ( currentUser.getLogin().equals(login) && currentUser.getPassword().equals(password) ) {
                userFound = true;
            }
        }

        JSONObject answer = new JSONObject();
        if ( userFound ) {
            String sessionId = request.getSession().getId();
            sessionService.openSession(sessionId, currentUser);

            answer.put("id", currentUser.getId());
            return Response.status(Response.Status.OK).entity(answer.toString()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(answer.toString()).build();
    }

    @GET
    @Path("")
    @Produces("application/json")
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
    @Path("")
    @Produces("application/json")
    public Response logout(@Context HttpServletRequest request) {
        JSONObject answer = new JSONObject();

        String sessionId = request.getSession().getId();
        sessionService.closeSession(sessionId);

        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

}
