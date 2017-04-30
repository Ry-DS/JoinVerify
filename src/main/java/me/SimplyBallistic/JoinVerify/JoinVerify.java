package me.SimplyBallistic.JoinVerify;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.SimplyBallistic.JoinVerify.events.InventoryListener;
import me.SimplyBallistic.JoinVerify.events.JoinListener;
import me.SimplyBallistic.JoinVerify.inventory.Tester;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

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
	public List<UUID> verifying;
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
		verifying=new ArrayList<>();
		getCommand("jverify").setExecutor(new VerifyCommand());
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		getLogger().info("Started!");
		reloadConfig();
		
		
	}
	@Override
	public void reloadConfig(){
		saveDefaultConfig();
		super.reloadConfig();
		List<String> blocks = null;
		try{
			blocks=getConfig().getStringList("blocks");
			for(int i=0;i<blocks.size();){
				String s=blocks.get(i);
				try{Material.valueOf(s.replaceAll(" ", "_").toUpperCase());}catch(Exception e){
					getLogger().warning(s+" isn't a valid block! Removing from list...");
					blocks.remove(i);
					continue;
				}
				i++;
			}
			
			}catch(Exception e){
				getLogger().warning("Could not find 'blocks' list in config! Resulting to random blocks...");
			}
		finally{
			getConfig().set("blocks", blocks);
			saveConfig();
		}
	}
	
	 @Override
	 @Deprecated
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
	 public String transCol(String s){
		 return ChatColor.translateAlternateColorCodes('&', s);
	 }
	 public void testPlayer(Player p){
			verifying.add(p.getUniqueId());
			JoinVerify.instance.getLogger().info("Testing "+p.getName()+"...");
			new Tester(p, this, ()->{
				
				verifying.remove(p.getUniqueId());
				if(!useBungee&&!verifyAll)
				verified.addPlayer(p.getUniqueId());
				else if(useBungee){
					//TODO Bungee send code here<-
					PacketCustom packet = new PacketCustom("JoinVerify", "verified:"+p.getUniqueId().toString()); 
					boolean answer = (boolean) packet.send(); 
					System.out.println(answer); 
				}
			
			},()->{
				p.kickPlayer(transCol(
						getConfig().getString("messages.kick")));
			},getConfig().getStringList("blocks"),transCol(getConfig().getString("messages.pickme")),
					transCol(getConfig().getString("messages.item")),transCol(getConfig().getString("messages.title"))).getInventory().open(p);
		}
    
}
