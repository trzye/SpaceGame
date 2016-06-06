package pl.edu.pw.ee.spacegame.server.security;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Michał on 2016-06-05.
 */
public class LoggedUsersTest {

    @Test
    public void test() {
        String nickname = "nickname";
        String fakeToken = "fakeToken";

        AuthenticationData authenticationData = LoggedUsers.generateTokenAndAddToLoggedUsers(nickname);
        Assert.assertTrue(LoggedUsers.isLogged(authenticationData));

        AuthenticationData fakeAuthenticationData = new AuthenticationData(nickname, fakeToken);
        Assert.assertFalse(LoggedUsers.isLogged(fakeAuthenticationData));

        LoggedUsers.deleteFromLoggedUsers(nickname);
        Assert.assertFalse(LoggedUsers.isLogged(authenticationData));
    }

}