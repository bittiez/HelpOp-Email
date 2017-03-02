package US.bittiez.HelpOpEmail;

import org.bukkit.craftbukkit.libs.jline.internal.Log;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by bitti on 4/3/2016.
 */
public class sendMail implements Runnable {
    public String[] to;
    public String from;
    public String message;
    public String subject = "HelpOp Email Support Request";
    public String host = "localhost";
    public String userName;
    public String password;
    public int port;
    public boolean useSSL;
    public boolean smtpAuth;

    @Override
    public void run() {
        if(to.length > 0){ // We have emails to send it to!
            if(from != null && !from.isEmpty()){ // We have a sender!
                if(message != null && !message.isEmpty()){// We have a message!
                    if(host != null && !host.isEmpty()){

                        Properties emailProperties = new Properties();
                        emailProperties.setProperty("mail.smtp.host", host);
                        emailProperties.setProperty("mail.smtp.port", String.valueOf(port));
                        emailProperties.setProperty("mail.stmp.from", from);
                        emailProperties.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
                        emailProperties.setProperty("mail.smtp.connectiontimeout", "7000");
                        emailProperties.setProperty("mail.transport.protocol", "smtp");

                        if(userName != null && !userName.isEmpty())
                            emailProperties.setProperty("mail.smtp.user", userName);
                        if(password != null && !password.isEmpty()) {
                            emailProperties.setProperty("mail.smtp.password", password);
                        }
                        if(useSSL) {
                            emailProperties.setProperty("mail.smtp.ssl.enable", "true");
                            emailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        }
                        if(smtpAuth)
                            emailProperties.setProperty("mail.smtp.auth", "true");

                        Session session = Session.getInstance(emailProperties);

                        try{
                            // Create a default MimeMessage object.
                            Message message = new MimeMessage(session);

                            // Set From: header field of the header.
                            message.setFrom(new InternetAddress(from));

                            // Set To: header field of the header.
                            for (String sendTo : to){
                                message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
                            }

                            // Set Subject: header field
                            message.setSubject(subject);

                            Multipart mp = new MimeMultipart();
                            MimeBodyPart body = new MimeBodyPart();
                            body.setContent(this.message, "text/html");
                            mp.addBodyPart(body);

                            // Send the actual HTML message, as big as you like
                            message.setContent(mp);

                            // Send message
                            Transport.send(message);
                            Log.info("HelpOp Email has been sent");
                        } catch (Exception mex) {
                            Log.error("An error occurred trying to the a HelpOp email. Error details:");
                            Log.error(String.format("Connection info: [%s:%s] [%s] [%s]",
                                    host,
                                    port,
                                    from,
                                    userName));


                            mex.printStackTrace();
                        }

                    } else {
                        Log.error("We need an email server to send this email from! Check your configuration.");
                    }
                } else {
                    Log.error("It appears there was no message content!");
                }
            } else {
                Log.error("We don't know who this email should've been sent from!");
            }
        } else {
            Log.error("There are no emails set up in the config for HelpOp Email");
        }
    }
}
