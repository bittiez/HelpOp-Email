[ ![Discord Support](https://www.mediafire.com/convkey/510b/iw2k26exg0qlf076g.jpg) ](https://discord.gg/p5DAvc6)
[ ![Bugs, Issues, Feature Requests](https://www.mediafire.com/convkey/2320/x80qtabf3auhhjr6g.jpg) ](../../issues)
[ ![Donate](https://www.mediafire.com/convkey/910d/z8160kkzvezi4km6g.jpg) ](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=THXHQ5287TBA8)

# HelpOp-Pro
Note: This requires Java 1.8+

Spigot 1.11 plugin to add a better /HelpOp command



# Description
When a player types /helpop \<message\>, the message will be sent to:
- All online staff with the permission `HelpOp.receive`
- All emails (if enabled) in the config file
- All phone numbers(if enabled, via text) in the config file
- A discord channel via WebHooks


# Permissions
[View permissions here](../../blob/master/src/plugin.yml)



# Installation
Place the jar file in your plugins folder



# Configuration
After you install the plugin, run your server once to create the config.yml file and the emailTemplate.html.  
The config should look like this: [View default configuration file here](../../blob/master/src/config.yml)

The config should be fairly self explanatory, if you have any questions hop on my discord support server(Link at the top)

The emailTemplate file is a template file used for your emails, the current placeholders available are:

`[USERNAME]` -> This will be replaced with the players Minecraft username  
`[MESSAGE]`  -> This will be replaced with the players message or question  
`[LOCATION]` -> This will be replaced with the players location and world  
These same placeholders can be used in the `twilio_text` config option
