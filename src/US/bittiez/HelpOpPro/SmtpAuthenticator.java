package US.bittiez.HelpOpPro;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by gamer on 3/1/2017.
 */
public class SmtpAuthenticator extends Authenticator {
    public String username;
    public String password;

    public SmtpAuthenticator() {
        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}
