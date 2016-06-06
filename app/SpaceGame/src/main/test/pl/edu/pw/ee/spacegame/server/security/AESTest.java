package pl.edu.pw.ee.spacegame.server.security;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Michał on 2016-06-05.
 */
public class AESTest {

    @Test
    public void test() {
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        AES.EncryptionData encryptionData = AES.encrypt(correctPassword);
        Assert.assertTrue(AES.isPasswordCorrect(correctPassword, encryptionData));
        Assert.assertFalse(AES.isPasswordCorrect(wrongPassword, encryptionData));
    }

}