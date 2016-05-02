package services;

import dao.UserDataSetDAO;
import datasets.UserDataSet;
import entities.UserProfile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import services.interfaces.AccountService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AccountServiceMapImplDB implements AccountService {
    private final SessionFactory sessionFactory;

    public AccountServiceMapImplDB() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/java1");
        configuration.setProperty("hibernate.connection.username", "superadm");
        configuration.setProperty("hibernate.connection.password", "123456");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public Collection<UserProfile> getAllUsers() {
        final LinkedList<UserProfile> userProfiles = new LinkedList<>();
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            final List<UserDataSet> allUsers = dao.getAllUsers();
            for (UserDataSet user : allUsers) {
                userProfiles.add(new UserProfile(user));
            }
        }
        return userProfiles;
    }

    @Override
    public UserProfile getUser(long userId) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);

            final UserDataSet uds = dao.getUserById(userId);
            if ( uds == null )
                return new UserProfile();

            return new UserProfile(uds);
        }
    }

    @Override
    public UserProfile getUser(String login) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            final UserDataSet uds = dao.getUserByLogin(login);
            if ( uds == null )
                return new UserProfile();

            return new UserProfile(uds);
        }
    }


    @Override
    public boolean isLoginBusy(String login) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            if (dao.getUserByLogin(login) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmailBusy(String email) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            if (dao.getUserByEmail(email) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long addUser(UserProfile userProfile) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSet uds = new UserDataSet(userProfile);
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            if ( isLoginBusy(uds.getLogin()) || isEmailBusy(uds.getEmail()) ){
                return -1;
            } else {
                dao.addUser(uds);
                return dao.getUserByLogin(uds.getLogin()).getId();
            }
        }
    }


    @Override
    public long editUser(long userId, UserProfile userProfile) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            final UserDataSet uds = new UserDataSet(userProfile);
            dao.editUser(userId, uds);
            return userId;
        }
    }

    @Override
    public void deleteUser(long userId) {
        try (Session session = sessionFactory.openSession()) {
            final UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.deleteUser(userId);
        }
    }

    @SuppressWarnings("unused")
    public void shutdown() {
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        final ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}