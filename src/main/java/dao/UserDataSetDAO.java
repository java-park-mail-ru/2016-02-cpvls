package dao;

import datasets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class UserDataSetDAO {
    private final Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public List<UserDataSet> getAllUsers() {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        //noinspection unchecked
        return (List<UserDataSet>) criteria.list();
    }

    public void addUser(UserDataSet user) {
        final Transaction trx = session.beginTransaction();
        session.save(user);
        trx.commit();
    }

    public void editUser(long userId, UserDataSet editedUser) {
        final Transaction trx = session.beginTransaction();
        final UserDataSet uds = session.load(UserDataSet.class, userId);

        if (getUserByLogin(editedUser.getLogin()) == null)
            uds.setLogin(editedUser.getLogin());

        if (getUserByEmail(editedUser.getEmail()) == null)
            uds.setEmail(editedUser.getEmail());

        uds.setPassword(editedUser.getPassword());

        session.save(uds);
        trx.commit();
    }

    public void deleteUser(long userId) {
        final Transaction trx = session.beginTransaction();
        final UserDataSet uds = session.load(UserDataSet.class, userId);
        session.delete(uds);
        trx.commit();
    }

    public UserDataSet getUserById(long id) {
        return session.get(UserDataSet.class, id);
    }

    public UserDataSet getUserByLogin(String login) {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult();
    }

    public UserDataSet getUserByEmail(String email) {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }
}