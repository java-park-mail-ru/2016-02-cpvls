package rest;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.atomic.AtomicLong;

public class UserProfile {
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private long id;

    private static final AtomicLong ID_GENETATOR = new AtomicLong(0);

    public UserProfile() {
        login = "";
        password = "";
        email = "";
        id = ID_GENETATOR.getAndIncrement();
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
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

    @NotNull
    public long getId() { return id; }

    public void setId(@NotNull long id) { this.id = id; }
}
