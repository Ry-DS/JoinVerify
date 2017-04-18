README-File of BungeeBridge version 1.6.3 by dommi2212

Description:
This is a developer API, which allows you to let your Bungeecord communicate with your Spigot using "packets"!
As server-owner you just have to follow this steps.

Installation-Guide:

1.) Download BungeeBridge.zip from http://www.spigotmc.org/resources/bungeebridge.5820/
2.) Unzip the File and copy BungeeBridge_Client.jar to Spigot/plugins/ and BungeeBridge_Server.jar to Bungeecord/plugins/
3.) Startup your Bungeecord and Spigot
4.) Stop your Bungeecord and Spigot
5.) Look into your plugins folder of Spigot & Bungeecord
6.) Edit the config.yml to your need (See below)
7.) You're done! Now you can look for plugins who use this API!

Configuration-Guide:
This are the default-settings of BungeeBridgeC (Spigot)!
############################################################################
configversion: <Version>          # The version of the plugin after last restart! NEVER TOUCH THIS!
host: localhost                   # URL of the Bungeecord-Instance; 
port: 7331                        # Port of BungeeBridge_Server (Has to be always the same as BungeeBridgeS)
securitymode: 'OFF'               # SecurityMode of the System (See below; Has to be always the same as BungeeBridgeS)
pass: <Generated Password>        # Password
updateinterval: 2                 # Interval to send "PacketKeepAlive"
packetlogger: true                # Whether packets should be logged and be accessible using /PacketManager
notify-bungee:                    #
  chat: true                      # Whether Chat-Events should be forwarded to BungeeBridgeS.
  command: true                   # Whether Command-Events should be forwarded to BungeeBridgeS.
############################################################################

This are the default-settings of BungeeBridgeS (Bungeecord)!
############################################################################
configversion: <Version>          # The version of the plugin after last restart! NEVER TOUCH THIS!
port: 7331                        # Port of BungeeBridge_Server (Has to be always the same as BungeeBridgeC)
securitymode: 'OFF'               # SecurityMode of the System (See below; Has to be always the same as BungeeBridgeC)
pass: <Generated Password>        # Password
############################################################################

SecurityMode:

Possible values: OFF, PASS, CIPHER

OFF:     No security for the packets is granted (You have to use a firewall and block your port!!!!!)
PASS:    Always if a packet is received the system checks if the password is correct!
CIPHER:  All packets are encoded with a password! (Slows down your server...)

My suggestion: Use "PASS" and a firewall ;)

Source & Documentation:
The whole BungeeBridge-Project is open source and can be used under the therms of "MIT License"!
See "https://github.com/dommi2212/BungeeBridge" for Source and documentation!
Of course you are allowed to help my using Issues or Pull-Requests! 

See the wiki for additional information: https://github.com/dommi2212/BungeeBridge/wiki