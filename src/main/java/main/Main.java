package main;

import cfg.configs;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.Session;
import rest.Users;
import services.AccountServiceMapImplDB;
import services.SessionService;
import services.interfaces.AccountService;

import java.util.HashSet;


public class Main {
    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {

        configs conf = new configs();
        final String port = conf.server.getProperty("port");

        System.out.append("Starting at port: ").append(port).append('\n');
        final Server server = new Server(Integer.valueOf(port));

        final ServletContextHandler contextHandler = new ServletContextHandler(server, "/api/", ServletContextHandler.SESSIONS);

        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceMapImplDB());
        context.put(SessionService.class, new SessionService());


        final HashSet<Class<?>> objects = new HashSet<>();
        objects.add(Users.class);
        objects.add(Session.class);

        final ResourceConfig config = new ResourceConfig(objects);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
            }
        });
        final ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));

        contextHandler.addServlet(servletHolder, "/*");
        server.start();
        server.join();
    }
}



