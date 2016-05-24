package main;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import cfg.Configs;
import frontend.AuthServiceImpl;
import frontend.GameServlet;
import frontend.WebSocketGameServlet;
import frontend.WebSocketServiceImpl;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.Highscore;
import rest.Session;
import rest.Users;
import services.AccountServiceMapImplDB;
import services.SessionService;
import services.interfaces.AccountService;
import org.eclipse.jetty.server.Handler;

import java.util.HashSet;


public class Main {
    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {

        Configs conf = new Configs();
        final int port = conf.getServerPort();

        //System.out.append("Starting at port: ").append(port).append('\n');
        final Server server = new Server(port);

        final ServletContextHandler contextHandler = new ServletContextHandler(server, "/api/",
                ServletContextHandler.SESSIONS);
//       ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);


        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceMapImplDB(conf));
        context.put(SessionService.class, new SessionService());

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AuthService authService = new AuthServiceImpl();


        //for game example
        contextHandler.addServlet(new ServletHolder(new WebSocketGameServlet(authService, gameMechanics,
                webSocketService)), "/gameplay");
        contextHandler.addServlet(new ServletHolder(new GameServlet(gameMechanics, authService)), "/game.html");

        final HashSet<Class<?>> objects = new HashSet<>();
        objects.add(Users.class);
        objects.add(Session.class);
        objects.add(Highscore.class);

        final ResourceConfig config = new ResourceConfig(objects);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
            }
        });
        final ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));

        contextHandler.addServlet(servletHolder, "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, contextHandler});
        server.setHandler(handlers);


        server.start();
        server.join();

     //run GM in main thread
        gameMechanics.run();
    }
}

//
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//        Server server = new Server(8080);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//
//        WebSocketService webSocketService = new WebSocketServiceImpl();
//        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
//        AuthService authService = new AuthServiceImpl();
//
//        //for chat example
//        // context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
//
//        //for game example
//        context.addServlet(new ServletHolder(new WebSocketGameServlet(authService, gameMechanics, webSocketService)), "/gameplay");
//        context.addServlet(new ServletHolder(new GameServlet(gameMechanics, authService)), "/game.html");
//
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setDirectoriesListed(true);
//        resource_handler.setResourceBase("static");
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[]{resource_handler, context});
//        server.setHandler(handlers);
//
//        server.setHandler(handlers);
//
//        server.start();
//
//        //run GM in main thread
//        gameMechanics.run();
//    }
//}
