package rest;

import main.AccountService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Singleton;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Singleton
@Path("/user")
public class Users {
    private AccountService accountService;

    public Users(AccountService accountService) {
        this.accountService = accountService;
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
    public Response getUserById(@PathParam("id") long id) {
        UserProfile user = accountService.getUser(id);
        JSONObject answer = new JSONObject();
        if(user == null){
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        } else {
            return Response.status(Response.Status.OK).entity(user.toString()).build();
        }
    }

    @PUT
    @Produces("application/json")
    public Response createUser(@FormParam("login") String login, @FormParam("password") String password, @FormParam("email") String email){

        UserProfile user = new UserProfile(login, password, email);
        boolean isCorrectInfo = !(login.isEmpty() || password.isEmpty() || email.isEmpty());
        boolean isUserPossible = isCorrectInfo && !(accountService.isLoginBusy(login));

        JSONObject answer = new JSONObject();
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
    public Response editUser(@FormParam("id") Long id, @FormParam("login") String login, @FormParam("password") String password, @FormParam("email") String email){

        UserProfile user = accountService.getUser(id);

        boolean isCorrectInfo = !(login.isEmpty() || password.isEmpty() || email.isEmpty());
        boolean isUserPossible = isCorrectInfo && accountService.isLoginBusy(login);

        JSONObject answer = new JSONObject();
        if ( !isUserPossible ) {
            return Response.status(Response.Status.FORBIDDEN).entity(answer.toString()).build();
        }

        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
       answer.put("id", user.getId());
       return Response.status(Response.Status.OK).entity(answer.toString()).build();

    }

}
