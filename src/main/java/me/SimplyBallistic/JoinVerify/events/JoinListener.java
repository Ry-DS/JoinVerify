package me.SimplyBallistic.JoinVerify.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.SimplyBallistic.JoinVerify.JoinVerify;
import me.dommi2212.BungeeBridge.packets.PacketCustom;


@SuppressWarnings("deprecation")
public class JoinListener implements Listener {
	private JoinVerify plugin=JoinVerify.instance;
	@EventHandler(priority=EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e){
		if(e.getPlayer().hasPermission("joinverify.bypass")){
			plugin.getLogger().info(e.getPlayer().getName()+" has logged in with bypass permission");
			return;}
		if(JoinVerify.useBungee&&!JoinVerify.verifyAll){
			Bukkit.getScheduler().runTaskLaterAsynchronously(JoinVerify.instance, ()->{
				PacketCustom packet = new PacketCustom("JoinVerify", "isVerified:"+e.getPlayer().getUniqueId()); 
				boolean answer = (boolean) packet.send(); 
				plugin.getLogger().info("Got response: "+answer);
				if(!answer){
					plugin.getLogger().info(e.getPlayer().getName()+" has been marked for testing by bungee");
					Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->JoinVerify.instance.testPlayer(e.getPlayer()), 10);}
				
			}, 5);
			
			
			
			return;
		}
		if(JoinVerify.verifyAll||!JoinVerify.instance.verified.containsPlayer(e.getPlayer().getUniqueId())){
			plugin.getLogger().info(e.getPlayer().getName()+" has been marked for testing");
			Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->JoinVerify.instance.testPlayer(e.getPlayer()), 20);
			}
			
		
		
		
		
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(plugin.verifying.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	@EventHandler
	public void onChat(PlayerChatEvent e){ 
		if(plugin.verifying.contains(e.getPlayer())){
			e.setCancelled(true);
		e.getPlayer().sendMessage(plugin.getConfig().getString("messages.deny"));
		if(plugin.getConfig().getBoolean("kick-on-fail"))
			Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->e.getPlayer().kickPlayer(plugin.getConfig().getString("messages.kick")), 5);
		plugin.getLogger().info(e.getPlayer().getName()+"attempted to say: "+e.getMessage());}
		
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(plugin.verifying.contains(e.getPlayer())){
			e.setCancelled(true);
	    if(plugin.getConfig().getBoolean("kick-on-fail"))
				Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->e.getPlayer().kickPlayer(plugin.getConfig().getString("messages.kick")), 5);
		e.getPlayer().sendMessage(ChatColor.RED+"Pass the Test in order to run commands!");
		plugin.getLogger().info(e.getPlayer().getName()+"attempted to run cmd: "+e.getMessage());}
	}
	

}
