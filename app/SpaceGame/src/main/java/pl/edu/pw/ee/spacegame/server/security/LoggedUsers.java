package pl.edu.pw.ee.spacegame.server.security;

import java.util.HashMap;

/**
 * Created by Micha³ on 2016-05-19.
 */
public class LoggedUsers {

    private static HashMap<String, String> loggedUsers = new HashMap<>();

    public static AuthenticationData generateTokenAndAddToLoggedUsers(String nickname) {
        String token = AES.encrypt(nickname + System.nanoTime()).getEncryptedPassword();
        AuthenticationData data = new AuthenticationData(nickname, token);
        if (loggedUsers.get(nickname) != null) {
            loggedUsers.replace(nickname, token);
        } else {
            loggedUsers.put(nickname, token);
        }
        return data;
    }

    public static Boolean isLogged(AuthenticationData data) {
        String actualToken = loggedUsers.get(data.getNickname());
        if (actualToken != null) {
            return loggedUsers.get(data.getNickname()).equals(data.getToken());
        } else {
            return false;
        }
    }

    public static void deleteFromLoggedUsers(String nickname) {
        loggedUsers.remove(nickname);
    }

}
