[ ![Discord Support](https://www.mediafire.com/convkey/1f30/84f194magcxff186g.jpg) ](https://discord.gg/p5DAvc6)
[ ![Bugs, Issues, Feature Requests](https://www.mediafire.com/convkey/3860/99n15b2cbgvnp416g.jpg) ](../../issues)
[ ![Donate](https://www.mediafire.com/convkey/3ac7/eurlt0tntrc95zh6g.jpg) ](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=THXHQ5287TBA8)

# HelpOp-Email
Note: This requires Java 1.8+

Spigot 1.11 plugin to add a better /HelpOp command



# Description
When a player types /helpop \<message\>, the message will be sent to all online staff and also emailed to the email(s) set up in the config file.



# Permissions
https://github.com/bittiez/HelpOp-Email/blob/master/src/plugin.yml



# Installation
Place the jar file in your plugins folder



# Configuration
After you install the plugin, run your server once to create the config.yml file and the emailTemplate.html.

The config should look like this: https://github.com/bittiez/HelpOp-Email/blob/master/src/config.yml

The config should be fairly self explanatory, as of this time the plugin only supports smtp.

The emailTemplate file is a template file used for your emails, the current placeholders available are:

[USERNAME] -> This will be replaced with the players Minecraft username

[MESSAGE]  -> This will be replaced with the players message or question

[LOCATION] -> This will be replaced with the players location and world
