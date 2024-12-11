package org.GreenDude.SecretSanta.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {


    //    String smtpLogin = "gmosthethird@yahoo.com";
    String smtpLogin = "gheorghiimosin@gmail.com";
    String smtpHost = "smtp.gmail.com";
    //    String smtpHost = "smtp.mail.yahoo.com";
    String sender = smtpLogin;
    String smtpPort = "465";

    String password = "mmet nqkt sqxu mmcm";
//    String password = "@uroraR1sing";


    private Session session;

    public void sendMessage(String receiver, String subject, String text, List<File> attachments) {

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=utf-8");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            for (File attachment : attachments) {
                attachmentBodyPart.attachFile(attachment);
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewSession(String login, String pass) {
        smtpLogin = login;
        password = pass;

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.host", smtpHost);
        prop.put("mail.smtp.port", smtpPort);
        prop.put("mail.smtp.ssl.trust", smtpHost);
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpLogin, password);

            }
        });
    }


}
