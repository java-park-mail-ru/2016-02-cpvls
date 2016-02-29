package rest;

import main.AccountService;
import org.json.JSONObject;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Singleton
@Path("/session")
public class Session {
    private AccountService accountService;
    private HashMap<String, UserProfile> currentSessions;

    public Session(AccountService accountService) {
        this.accountService = accountService;
        currentSessions = new HashMap<String, UserProfile>();
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
            currentSessions.put(sessionId, currentUser);

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
        UserProfile currentUser = currentSessions.get(sessionId);

        JSONObject answer = new JSONObject();

        if ( currentUser == null ) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(answer).build();
        }

        answer.put("id", currentUser.getId());
        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

    @DELETE
    @Path("")
    @Produces("application/json")
    public Response logout(@Context HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        currentSessions.remove(sessionId);

        JSONObject answer = new JSONObject();

        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }

}
