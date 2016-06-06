package pl.edu.pw.ee.spacegame.server.security;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by Michał on 2016-06-05.
 */
public class AES {

    public static class EncryptionData {

        private String encryptedPassword;
        private String salt;

        public EncryptionData(String encryptedPassword, String salt) {
            this.encryptedPassword = encryptedPassword;
            this.salt = salt;
        }

        public String getEncryptedPassword() {
            return encryptedPassword;
        }

        public String getSalt() {
            return salt;
        }

    }

    public static EncryptionData encrypt(String rawPassword) {
        String salt = KeyGenerators.string().generateKey();
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder(salt);
        String encryptedPassword = standardPasswordEncoder.encode(rawPassword);
        return new EncryptionData(encryptedPassword, salt);
    }

    public static Boolean isPasswordCorrect(String password, EncryptionData encryptionData) {
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder(encryptionData.getSalt());
        return standardPasswordEncoder.matches(password, encryptionData.getEncryptedPassword());
    }
}
