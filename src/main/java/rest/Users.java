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
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("name") String name) {
        final UserProfile user = accountService.getUser(name);
        if(user == null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }else {
            return Response.status(Response.Status.OK).entity(user).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@FormParam("login") String login, @FormParam("password") String password, @FormParam("email") String email){

        UserProfile user = new UserProfile(login, password, email);
        boolean user_is_possible = true;
        boolean info_is_correct = true;
        info_is_correct = !(login.isEmpty() || password.isEmpty() || email.isEmpty());
        user_is_possible = info_is_correct && (accountService.getUser(login) == null);

        JSONArray answer = new JSONArray();

        if ( !user_is_possible ) {
            return Response.status(Response.Status.FORBIDDEN).entity(answer).build();
        }

        if(accountService.addUser(user.getLogin(), user)){
            answer.put("xep");
            return Response.status(Response.Status.OK).entity(answer).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity(answer).build();
        }
    }
}
