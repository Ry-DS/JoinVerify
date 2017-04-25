package me.SimplyBallistic.JoinVerify.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.SimplyBallistic.JoinVerify.JoinVerify;
import me.dommi2212.BungeeBridge.packets.PacketCustom;


@SuppressWarnings("deprecation")
public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(e.getPlayer().hasPermission("joinverify.bypass"))return;
		if(JoinVerify.useBungee&&!JoinVerify.verifyAll){
			Bukkit.getScheduler().runTaskLaterAsynchronously(JoinVerify.instance, ()->{
				PacketCustom packet = new PacketCustom("JoinVerify", "isVerified:"+e.getPlayer().getUniqueId()); 
				boolean answer = (boolean) packet.send(); 
				JoinVerify.instance.getLogger().info("Got response: "+answer);
				if(!answer)
					JoinVerify.instance.testPlayer(e.getPlayer());
				
			}, 5);
			
			
			
			return;
		}
		if(JoinVerify.verifyAll||!JoinVerify.instance.verified.containsPlayer(e.getPlayer().getUniqueId()))
			Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->JoinVerify.instance.testPlayer(e.getPlayer()), 20);
			
		
		
		
		
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(JoinVerify.instance.verifying.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	@EventHandler
	public void onChat(PlayerChatEvent e){ 
		if(JoinVerify.instance.verifying.contains(e.getPlayer()))
			e.setCancelled(true);
		e.getPlayer().sendMessage("Pass the test in order to chat!");
		
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(JoinVerify.instance.verifying.contains(e.getPlayer()))
			e.setCancelled(true);
		e.getPlayer().sendMessage("Pass the Test in order to run commands!");
	}
	

}
