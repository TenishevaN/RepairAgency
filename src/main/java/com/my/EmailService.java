package com.my;

import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * {@ code EmailService} class represents the imlamantation of the sending email.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class EmailService {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "servicemailtest2021@gmail.com";
    private static final String PASSWORD = "serviceMailTest";

    private static final Logger log = LogManager.getLogger(EmailService.class);


    public EmailService(User user, String locale) {
        sendMail(user, locale);
    }

    private void sendMail(User user, String locale) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.ssl.trust", HOST);
        prop.put("mail.smtp.tsl.trust", HOST);
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject(ServiceUtil.getKey("notification", locale));
            String msg = ServiceUtil.getKey("email_register_message", locale);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
           log.debug("email registration exception {}", e.getMessage());
        }
    }

}