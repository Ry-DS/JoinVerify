package me.SimplyBallistic.JoinVerify.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.SimplyBallistic.JoinVerify.JoinVerify;


public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(JoinVerify.verifyAll||!JoinVerify.instance.verified.containsPlayer(e.getPlayer().getUniqueId()))
			Bukkit.getScheduler().runTaskLater(JoinVerify.instance, ()->JoinVerify.instance.testPlayer(e.getPlayer()), 20);
			
		else{
			//TODO Bungee and file
		}
		
		
	}

}
