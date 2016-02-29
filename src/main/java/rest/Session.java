package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String login, @FormParam("password") String password, @Context HttpServletRequest request) {
        String ans = password;
        final UserProfile user = accountService.getUser(login);

        boolean userFound = false;
        if ( user != null ) {
            if ( user.getPassword().equals(password) ) {
                userFound = true;
            }
        }

        if( !userFound ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }

        String sessionId = request.getSession().getId();
        currentSessions.put(sessionId, user);

        return Response.status(Response.Status.OK).entity(user.getId()).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthorized(@Context HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        UserProfile currentUser = currentSessions.get(sessionId);

        if ( currentUser == null ) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("").build();
        }

        return Response.status(Response.Status.OK).entity(currentUser).build();
    }


    @DELETE
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        currentSessions.remove(sessionId);
        return Response.status(Response.Status.OK).build();
    }


}
