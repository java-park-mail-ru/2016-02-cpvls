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

    @Column(name="highscore")
    private int highscore;

    public UserDataSet() {
        this.login = "";
        this.password = "";
        this.email = "";
        this.id = -1;
        this.highscore = 0;
    }

    public UserDataSet(UserProfile profile) {
        this.login = profile.getLogin();
        this.password = profile.getPassword();
        this.email = profile.getEmail();
        this.highscore = profile.getHighscore();
        this.id = profile.getId();
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

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(@NotNull int highscore) {
        this.highscore = highscore;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        final UserDataSet other = (UserDataSet) obj;

        return this.id == other.id && this.login.equals(other.login) && this.email.equals(other.email)
                && this.password.equals(other.password);
    }

    @Override
    public int hashCode() {
        return 133*login.hashCode()*email.hashCode();
    }
}
