package me.SimplyBallistic.JoinVerify.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.SimplyBallistic.JoinVerify.JoinVerify;
import me.dommi2212.BungeeBridge.packets.PacketCustom;


public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(JoinVerify.useBungee&&!JoinVerify.verifyAll){
			Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->{
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

}
