package US.bittiez.HelpOpEmail.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.twilio.Twilio;

public class SendMessage {
    public static void Send(String ACCOUNT_SID, String AUTH_TOKEN, String Body, String From, String To) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber("+" + To), new PhoneNumber("+" + From), Body).create();
    }
}
