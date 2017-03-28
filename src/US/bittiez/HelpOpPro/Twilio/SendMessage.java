package US.bittiez.HelpOpPro.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.twilio.Twilio;

public class SendMessage implements Runnable {
    private String ACCOUNT_SID, AUTH_TOKEN, Body, From, To;

    public SendMessage(String ACCOUNT_SID, String AUTH_TOKEN, String Body, String From, String To) {
        this.ACCOUNT_SID = ACCOUNT_SID;
        this.AUTH_TOKEN = AUTH_TOKEN;
        this.Body = Body;
        this.From = From;
        this.To = To;
    }

    @Override
    public void run() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber("+" + To), new PhoneNumber("+" + From), Body).create();
    }
}
