package datasets;

import entities.UserProfile;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * Created by puhovity on 30.03.16.
 */
@Entity
@Table(name = "users")
public class UserDataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    public UserDataSet() {
        setLogin("");
        setPassword("");
        setEmail("");
        setId(-1);
    }

    public UserDataSet(UserProfile profile) {
        setLogin(profile.getLogin());
        setPassword(profile.getPassword());
        setEmail(profile.getEmail());
        setId(profile.getId());
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull String login) {
        this.login = login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @NotNull
    public String getEmail() { return email; }

    public void setEmail(@NotNull String email) { this.email = email; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        UserDataSet other = (UserDataSet) obj;


        return this.id == other.getId() && this.login.equals(other.getLogin()) && this.email.equals(other.getEmail())
                && this.password.equals(other.getPassword());
    }

    @Override
    public int hashCode() {
        return 133*login.hashCode()*email.hashCode();
    }
}
