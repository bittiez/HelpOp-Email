package US.bittiez.HelpOpEmail;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

//To-do: add a reload command, and check for permissions on everything
// add player location to the email

/**
 * Created by bitti on 4/3/2016.
 */
public class main extends JavaPlugin{
    public static String version = "1.0.0";
    public String template = "";
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        createConfig();
        checkTemplate();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(cmd.getName().equalsIgnoreCase("helpop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED
                        + "HelpOp is not available from the console.");
            } else {
                if(args.length > 0){
                    if(sender.hasPermission("HelpOp.ask")) {
                        sendMail mail = new sendMail();
                        mail.from = config.getString("emailFrom");
                        List<String> sendTos = config.getStringList("emailTo");
                        mail.to = sendTos.toArray(new String[0]);
                        mail.subject = config.getString("emailSubject");
                        mail.host = config.getString("emailHost");
                        mail.userName = config.getString("smtpUser");
                        mail.password = config.getString("smtpPassword");

                        StringBuilder fromMessage = new StringBuilder();
                        for (String s : args) {
                            fromMessage.append(s + " ");
                        }

                        String tempTemplate = template;
                        tempTemplate = tempTemplate.replaceAll("(\\[USERNAME\\])", sender.getName());
                        tempTemplate = tempTemplate.replaceAll("(\\[MESSAGE\\])", fromMessage.toString());
                        mail.message = tempTemplate;
                        new Thread(mail).run();

                        for(Player p : Bukkit.getServer().getOnlinePlayers()){
                            if(p.hasPermission("HelpOp.receive")) {
                                p.sendMessage(ChatColor.GREEN + "[HelpOp] [" + sender.getName() + "]" + ChatColor.AQUA + " asked: " + fromMessage);
                                p.sendMessage(ChatColor.GREEN + "[HelpOp]" + ChatColor.AQUA + " This message has also been dispatched to the emails set up in the config.");
                            }
                        }

                        sender.sendMessage(ChatColor.AQUA + "Your support request has been received, we will be in contact shortly!");
                        return true;
                    } else {
                        sender.sendMessage("You do not have the required permissions to use this command!");
                        Log.info(sender.getName() + " tried to use /HelpOp but does not have the permission HelpOp.ask");
                    }
                }
            }
        }
        return false;
    }

    private boolean checkTemplate(){
        File template = new File("plugins/HelpOp_Email/emailTemplate.html");
        if(!template.exists()){
            URL inputUrl = getClass().getResource("/emailTemplate.html");
            try {
                FileUtils.copyURLToFile(inputUrl, template);
            } catch (IOException e) {
                Log.error("[HelpOp_Email] Could not load the email template!");
                e.printStackTrace();
                return false;
            }
        }
        try {
            this.template = new String(Files.readAllBytes(template.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void createConfig() {
        config.options().copyDefaults();
        saveDefaultConfig();
    }

}
