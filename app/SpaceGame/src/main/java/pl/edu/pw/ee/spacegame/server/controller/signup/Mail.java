package pl.edu.pw.ee.spacegame.server.controller.signup;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-06-06.
 */
public class Mail {

    public static void sent(String emailToSend, String activationCode) throws MessagingException {
        String activationLink = String.format(ACTIVATION_LINK, emailToSend, activationCode);
        Properties props = getProperties();
        Session session = getSession(props);
        Message message = getMessage(emailToSend, activationLink, session);
        Transport.send(message);
    }

    private static Message getMessage(String email, String activationLink, Session session) throws MessagingException {
        String contentType = "text/html; charset=\"UTF-8\"";
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(EMAIL_TITLE);
        message.setContent(String.format(EMAIL_CONTENT, activationLink), contentType);
        return message;
    }

    private static Session getSession(Properties props) {
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);
                    }
                });
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.port", SMTP_PORT.toString());
        return props;
    }

}
