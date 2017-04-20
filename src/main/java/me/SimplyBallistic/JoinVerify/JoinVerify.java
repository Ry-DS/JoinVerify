package me.SimplyBallistic.JoinVerify;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

/**
 * 
 * @author ryan9_000
 *
 */
public class JoinVerify extends JavaPlugin implements PluginMessageListener{
	public static JoinVerify instance;
	public BukkitPlayersFile verified;
	public static boolean verifyAll;
	public static boolean useBungee;
	@Override
	public void onEnable() {
		getLogger().info("Starting up...");
		
		saveDefaultConfig();
		
		verifyAll=getConfig().getBoolean("verify-all",false);
		useBungee=getConfig().getBoolean("use-bungee", false);
		
		
		
		if(useBungee&&!Bukkit.getPluginManager().isPluginEnabled("BungeeBridgeC")){
			getLogger().warning("Use bungee was set to true in the config and BungeeBridge is not installed! Disabling...");
			getPluginLoader().disablePlugin(this);
			return;
		}
		instance=this;
		verified=new BukkitPlayersFile();
		if(useBungee){
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	    
		}
	}
	
	
	 @Override
	  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (!channel.equals("BungeeCord")) {
	      return;
	    }
	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String subchannel = in.readUTF();
	    if (subchannel.equals("JoinVerify")) {
	    	short len = in.readShort();
	    	byte[] msgbytes = new byte[len];
	    	in.readFully(msgbytes);

	    	DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
	    	try {
				String data = msgin.readUTF();
				//short somenumber = msgin.readShort();
				System.out.println(data);
				///<----------------------------------------DATA HERE IN THIS MESS----------------------------
			} catch (IOException e) {
				getLogger().warning("Failed in reading data from bungee! "+e.getMessage());
			}finally{try {
				msgin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
	    	
	    }
	  }
    
}
