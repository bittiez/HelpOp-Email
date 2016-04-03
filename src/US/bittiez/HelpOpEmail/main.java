package US.bittiez.HelpOpEmail;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

//To-do: add a reload command, and check for permissions on everything

/**
 * Created by bitti on 4/3/2016.
 */
public class main extends JavaPlugin{
    public static String version = "1.0.0";
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        createConfig();
        Log.info("Enabled HelpOp Email v"+version);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(cmd.getName().toLowerCase().equals("helpop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED
                        + "HelpOp is not available from the console.");
            } else {
                if(args.length > 0){
                    sendMail mail = new sendMail();
                    mail.from = config.getString("emailFrom");
                    List<String> sendTos = config.getStringList("emailTo");
                    mail.to = sendTos.toArray(new String[0]);
                    mail.subject = config.getString("emailSubject");
                    mail.host = config.getString("emailHost");
                    mail.userName = config.getString("smtpUser");
                    mail.password = config.getString("smtpPassword");

                    StringBuilder fromMessage = new StringBuilder();
                    for(String s : args) {
                        fromMessage.append(s + " ");
                    }

                    mail.message = sender.getName() + " asked " + fromMessage;

                    new Thread(mail).run();
                    sender.sendMessage("Email sent!");
                    return true;
                }
            }
        }
        return false;
    }

    private void createConfig() {
//        config.addDefault("emailFrom", "abc@gmail.com");
//        config.addDefault("emailTo", new String[] {"abcd@gmail.com", "abcde@gmail.com"});
//        config.addDefault("emailSubject", "HelpOp Email Support Request");
//        config.addDefault("emailHost", "localhost");
//        config.addDefault("smtpUser", "");
//        config.addDefault("smtpPassword", "");
        config.options().copyDefaults();
        saveDefaultConfig();
    }

}
