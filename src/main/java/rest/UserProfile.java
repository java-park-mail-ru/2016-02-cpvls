package rest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Null;
import java.util.concurrent.atomic.AtomicLong;

public class UserProfile {

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    private long id;

    public UserProfile() {
        login = "";
        password = "";
        email = "";
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email, @NotNull Long id) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.id = id;
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

    @NotNull
    public String getEmail() { return email; }

    public void setEmail(@NotNull String email) { this.email = email; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }
}
