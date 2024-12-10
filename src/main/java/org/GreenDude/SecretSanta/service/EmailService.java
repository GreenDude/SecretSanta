package org.GreenDude.SecretSanta.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Properties;

@Service
public class EmailService {


//    String smtpLogin = "gmosthethird@yahoo.com";
    String smtpLogin = "gheorghiimosin@gmail.com";
    String smtpHost = "smtp.gmail.com";
//    String smtpHost = "smtp.mail.yahoo.com";
    String sender = smtpLogin;
    String smtpPort = "465";

    String password = "";
//    String password = "@uroraR1sing";


    private Session session;

    public EmailService() {
        createNewSession();
    }

    public void test() throws Exception {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("gmosthethird@gmail.com"));
        message.setSubject("Mail Subject");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File("/Users/mosingheorghii/SecretSanta/target/output/matches.txt"));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public void createNewSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth",true);
        prop.put("mail.debug","true");
//        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.host",smtpHost);
        prop.put("mail.smtp.port",smtpPort);
        prop.put("mail.smtp.ssl.trust",smtpHost);
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpLogin, password);

            }
        });
    }


}
