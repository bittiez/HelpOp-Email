package US.bittiez.HelpOpEmail;

import org.bukkit.craftbukkit.libs.jline.internal.Log;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    @Override
    public void run() {
        if(to.length > 0){ // We have emails to send it to!
            if(from != null && !from.isEmpty()){ // We have a sender!
                if(message != null && !message.isEmpty()){// We have a message!
                    if(host != null && !host.isEmpty()){

                        Properties emailProperties = new Properties();
                        emailProperties.setProperty("mail.smtp.host", host);
                        emailProperties.setProperty("mail.smtp.port", String.valueOf(port));

                        if(userName != null && !userName.isEmpty())
                            emailProperties.setProperty("mail.user", userName);
                        if(password != null && !password.isEmpty())
                            emailProperties.setProperty("mail.password", password);

                        Session session = Session.getDefaultInstance(emailProperties);

                        try{
                            // Create a default MimeMessage object.
                            MimeMessage message = new MimeMessage(session);

                            // Set From: header field of the header.
                            message.setFrom(new InternetAddress(from));

                            // Set To: header field of the header.
                            for (String sendTo : to){
                                message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
                            }

                            // Set Subject: header field
                            message.setSubject(subject);

                            // Send the actual HTML message, as big as you like
                            message.setContent(this.message, "text/html" );

                            // Send message
                            Transport.send(message);
                            Log.info("HelpOp Email has been sent");
                        }catch (Exception mex) {
                            Log.error("An error occured trying to the a HelpOp email. Error details:");
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
