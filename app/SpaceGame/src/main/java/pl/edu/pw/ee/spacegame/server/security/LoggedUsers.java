package pl.edu.pw.ee.spacegame.server.security;

import java.util.HashMap;

/**
 * Created by Michał on 2016-05-19.
 */
public class LoggedUsers {

    private static HashMap<String, String> loggedUsers = new HashMap<>();

    public static AuthenticationData generateTokenAndAddToLoggedUsers(String nickname) {
        String token = AES.encrypt(nickname + System.nanoTime()).getEncryptedPassword();
        AuthenticationData data = new AuthenticationData(nickname, token);
        loggedUsers.put(nickname, token);
        return data;
    }

    public static Boolean isLogged(AuthenticationData data) {
        String actualToken = loggedUsers.get(data.getNickname());
        return actualToken != null && loggedUsers.get(data.getNickname()).equals(data.getToken());
    }

    public static void deleteFromLoggedUsers(String nickname) {
        loggedUsers.remove(nickname);
    }

}
