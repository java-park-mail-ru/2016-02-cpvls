//package main;
//
//import rest.UserProfile;
//
//import javax.ws.rs.core.Context;
//import java.util.Collection;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author esin88
// */
//public class AccountServiceMapImplDB implements AccountService {
//
//    @Context
//    private rest.Context context;
//
//    public AccountServiceMapImplDB() {
//
//    }
//
//    @Override
//    public Collection<UserProfile> getAllUsers() {
//        return users.values();
//    }
//
//    @Override
//    public boolean addUser(String userName, UserProfile userProfile) {
//        if (users.containsKey(userName))
//            return false;
//        users.put(userName, userProfile);
//        return true;
//    }
//
//    @Override
//    public UserProfile getUser(String userName) {
//        return users.get(userName);
//    }
//}
