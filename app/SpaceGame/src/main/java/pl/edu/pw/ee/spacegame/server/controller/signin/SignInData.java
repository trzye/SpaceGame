package pl.edu.pw.ee.spacegame.server.controller.signin;

/**
 * Created by Michał on 2016-06-06.
 */
public class SignInData {
    private String rawPassword;
    private String nickname;

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
