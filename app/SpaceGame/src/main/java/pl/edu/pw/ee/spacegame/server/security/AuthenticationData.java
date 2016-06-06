package pl.edu.pw.ee.spacegame.server.security;

import java.io.Serializable;

/**
 * Created by Michał on 2016-05-19.
 */
public class AuthenticationData implements Serializable {
    private String token;
    private String nickname;

    public AuthenticationData(String nickname, String token) {
        this.token = token;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }
}
