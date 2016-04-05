# HelpOp-Email
Spigot 1.8, 1.9 plugin to add a better /HelpOp command



# Description
When a player types /helpop <message>, the message will be sent to all online staff and also emailed to the email(s) set up in the config file.



# Permissions
https://github.com/bittiez/HelpOp-Email/blob/master/src/plugin.yml



# Installation
Place the jar file in your plugins folder



# Configuration
After you install the plugin, run your server once to create the config.yml file and the emailTemplate.html.
The config should look like this: https://github.com/bittiez/HelpOp-Email/blob/master/src/config.yml
The config should be fairly selft explanatory, as of this time the plugin only support smtp.

The emailTemplate file is a template file used for your emails, the current placeholders available are:
[USERNAME] -> This will be replaced with the players Minecraft username
[MESSAGE]  -> This will be replaced with the players message or question



# Up coming features
- Add the players location to the emails.