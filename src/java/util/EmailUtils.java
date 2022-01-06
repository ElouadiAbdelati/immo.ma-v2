/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author soulaimane
 */
public class EmailUtils {
      public static int sendMail(String destination, String objet, String body) {

        String host = "smtp.gmail.com";
        final String user = "madiatechentreprise@gmail.com";
        final String password = "gxmtxietcymkeosh";

        String to = destination;

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        int code = 0;
        try {
            double x = Math.random() * 1000000;
            code = (int) x;

            String content = "<div>\n"
                    + "        <div style=\"margin: auto; justify-content: center; text-align: center; width: 40%; height: auto; background-color:  #eceff1; padding: 20px 30px;\">\n"
                    + "            <div style=\"font-size: 25px;\">\n"
                    + "                <h2 style=\"color: #ff5722;\">IMMO<span style=\"color: #304ffe;\">.ma</span></h2>\n"
                    + "            </div>\n"
                    + "            <div style=\"padding: 0 0 5px 0;\">\n"
                    + "                <p style=\"font-size: 20px; color: #000000; flex-wrap: wrap;\">" + body + "</p>\n"
                    + "            </div>\n"
                    + "            <div style=\"padding: 0 0 3px 0;\">\n"
                    + "                <p style=\"font-size: 20px; color: #000000; flex-wrap: wrap; font-weight: bold;\">Merci pour votre confience.</p>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>";

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(objet);
            message.setContent(content, "text/html");
            //message.setText("This is simple program of sending email using JavaMail API");

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }
}
