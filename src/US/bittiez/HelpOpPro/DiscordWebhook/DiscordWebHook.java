package US.bittiez.HelpOpPro.DiscordWebhook;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class DiscordWebHook implements Runnable {
    private String webhookUrl;
    private String message;

    public DiscordWebHook(String webhookUrl, String message) {

        this.webhookUrl = webhookUrl;
        this.message = message;
    }

    @Override
    public void run() {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost thePost = new HttpPost(webhookUrl);
            StringEntity params = new StringEntity("{\"content\": \"" + JSONObject.escape(message) + "\"}");
            thePost.addHeader("content-type", "application/json");
            thePost.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            thePost.setEntity(params);
            Logger.getGlobal().info(httpClient.execute(thePost).toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
