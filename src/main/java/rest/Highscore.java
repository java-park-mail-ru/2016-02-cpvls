package rest;

import entities.UserProfile;
import org.json.JSONArray;
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

/**
 * Created by polina on 24.05.16.
 */


@Singleton
@Path("/highscores")
@Produces(MediaType.APPLICATION_JSON)
public class Highscore {

    @Inject
    private main.Context context;


    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response get_score( @Context HttpServletRequest request) {
        final SessionService sessionService = context.get(SessionService.class);

        final AccountService accountService = context.get(AccountService.class);
        final Collection<UserProfile> allUsers = accountService.getAllUsers();
        final String sessionId = request.getSession().getId();
        System.out.println("get score");
        System.out.println(allUsers);
        JSONArray answer = new JSONArray();

        for (UserProfile user: allUsers) {
            JSONObject row = new JSONObject();
            row.put("id", user.getId());
            row.put("login", user.getLogin());
            row.put("highscore", user.getHighscore());
            answer.put(row);
        }

        return Response.status(Response.Status.OK).entity(answer.toString()).build();
    }
}


