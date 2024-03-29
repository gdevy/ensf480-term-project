package email;

import entity.socket.EmailInfo;

import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *  Following jar are required:
 *  1) mail-1.4.7.jar from http://central.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
 *  2) activation-1.1.1.jar from http://central.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
 *
 */
public class Test {

    public static void sendEmailTo( EmailInfo info, String recip ) {
        String username = "renterapp480";
        String password = "ensf480!";
        String to = recip;
        String subject = info.subject;
        String email_body = info.body;
        Test test = new Test();
        test.doSendMail(username, password, to, subject, email_body);
    }
    // sends mail
    public void doSendMail(final String username, final String password, String to, String subject, String email_body) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(email_body);
            Transport.send(message);
            System.out.println("message sent");
            //JOptionPane.showMessageDialog(null, "Message Sent!", "Sent", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}