package entities;

import datasets.UserDataSet;
import org.jetbrains.annotations.NotNull;

public class UserProfile {

    @NotNull
    private String login = "";
    @NotNull
    private String password = "";
    @NotNull
    private String email = "";
    @NotNull
    private int highscore = 0;

    private long id;

    public UserProfile() {
        login = "";
        password = "";
        email = "";
        highscore = 0;
    }

    public UserProfile(UserDataSet uds) {
        login = uds.getLogin();
        password = uds.getPassword();
        email = uds.getEmail();
        highscore = uds.getHighscore();
        id = uds.getId();
    }


    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.highscore = 0;
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email, @NotNull int highscore) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.highscore = highscore;
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email, @NotNull int highscore,
                       @NotNull Long id) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.highscore = highscore;
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


    @NotNull
    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(@NotNull int highscore) {
        this.highscore = highscore;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }
}
