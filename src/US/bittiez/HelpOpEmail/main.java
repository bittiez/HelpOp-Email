package US.bittiez.HelpOpEmail;

import US.bittiez.HelpOpEmail.Twilio.SendMessage;
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


public class main extends JavaPlugin {
    public static String version = "1.2.4";
    public String template = "";
    public FileConfiguration config = getConfig();

    public Boolean emailEnabled = true;
    public Boolean twilioEnabled = false;

    @Override
    public void onEnable() {
        createConfig();
        checkTemplate();
        emailEnabled = !config.getBoolean("disable_email", true);
        twilioEnabled = !config.getBoolean("disable_twilio");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("reloadhelpop")) {
            if (sender.hasPermission("HelpOp.reload")) {
                checkTemplate();
                createConfig();
                this.reloadConfig();
                config = getConfig();
                emailEnabled = !config.getBoolean("disable_email", true);
                twilioEnabled = !config.getBoolean("disable_twilio");
                sender.sendMessage("Config reloaded!");
                return true;
            } else {
                sender.sendMessage("You do not have the required permissions to use this command!");
                Log.info(sender.getName() + " tried to use /ReloadHelpOp but does not have the permission HelpOp.reload");
            }
        }
        if (cmd.getName().equalsIgnoreCase("helpop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED
                        + "HelpOp is not available from the console.");
            } else {
                if (args.length > 0) {
                    if (sender.hasPermission("HelpOp.ask")) {
                        StringBuilder fromMessage = new StringBuilder();
                        Player who = (Player) sender;
                        for (String s : args) {
                            fromMessage.append(s + " ");
                        }

                        if (emailEnabled) {
                            SendMail mail = new SendMail();
                            mail.from = config.getString("emailFrom");
                            List<String> sendTos = config.getStringList("emailTo");
                            mail.to = sendTos.toArray(new String[0]);
                            mail.subject = config.getString("emailSubject");
                            mail.host = config.getString("emailHost");
                            mail.userName = config.getString("smtpUser");
                            mail.password = config.getString("smtpPassword");
                            mail.port = config.getInt("smtpPort");
                            mail.useSSL = config.getBoolean("useSSL");
                            mail.smtpAuth = config.getBoolean("smtpAuth");

                            String tempTemplate = template;
                            tempTemplate = tempTemplate.replaceAll("(\\[USERNAME\\])", sender.getName());
                            tempTemplate = tempTemplate.replaceAll("(\\[MESSAGE\\])", fromMessage.toString());
                            tempTemplate = tempTemplate.replaceAll("(\\[LOCATION\\])", String.format("[X: %s] [Y: %s] [Z: %s] [WORLD: %s]", who.getLocation().getX() + "", who.getLocation().getY() + "", who.getLocation().getZ() + "", who.getWorld().getName()));
                            mail.message = tempTemplate;
                            new Thread(mail).start();

                            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                if (p.hasPermission("HelpOp.receive")) {
                                    p.sendMessage(ChatColor.GREEN + "[HelpOp] [" + sender.getName() + "]" + ChatColor.AQUA + " asked: " + fromMessage);
                                    p.sendMessage(ChatColor.GREEN + "[HelpOp]" + ChatColor.AQUA + " This message has also been dispatched to the email(s) set up in the config.");
                                }
                            }

                            sender.sendMessage(ChatColor.AQUA + "Your support request has been received, we will be in contact shortly!");
                        }
                        if (twilioEnabled) {
                            String msg = config.getString("twilio_text");
                            msg = msg.replaceAll("(\\[USERNAME\\])", sender.getName());
                            msg = msg.replaceAll("(\\[MESSAGE\\])", fromMessage.toString());
                            msg = msg.replaceAll("(\\[LOCATION\\])", String.format("[X: %s] [Y: %s] [Z: %s] [WORLD: %s]", who.getLocation().getX() + "", who.getLocation().getY() + "", who.getLocation().getZ() + "", who.getWorld().getName()));

                            for (String sendTo : config.getStringList("twilio_numbers")) {
                                SendMessage se = new SendMessage(
                                        config.getString("twilio_account_sid", ""),
                                        config.getString("twilio_auth_token", ""),
                                        msg,
                                        config.getString("twilio_phone", "123"),
                                        sendTo
                                );
                                new Thread(se).start();
                            }


                        }

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

    private boolean checkTemplate() {
        File template = new File("plugins/HelpOpEmail/emailTemplate.html");
        if (!template.exists()) {
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
