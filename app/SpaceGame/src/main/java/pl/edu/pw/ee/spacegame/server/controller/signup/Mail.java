package pl.edu.pw.ee.spacegame.server.controller.signup;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Michał on 2016-06-06.
 */
public class Mail {

    public static void sent(String email, String activationCode) throws MessagingException {
        final String username = "space.game.noreply@gmail.com";
        final String password = "spacegamenoreply";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("space.game.noreply@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
        
        //TODO: nie zapinać się na localhost
        String activationLink = "http://localhost:8080/activation?email=" + email + "&activationCode=" + activationCode;

        //TODO: zaprojektować ładniejszą wiadomość
        message.setSubject("Link aktywacyjny do konta w SpaceGame");
        message.setContent("<a href=\"" + activationLink + "\">Kliknij aby aktywować swoje konto w SpaceGame</a>", "text/html; charset=\"UTF-8\"");

        Transport.send(message);
    }

}
