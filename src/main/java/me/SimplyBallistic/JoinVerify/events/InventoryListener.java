package me.SimplyBallistic.JoinVerify.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.SimplyBallistic.JoinVerify.JoinVerify;

public class InventoryListener implements Listener {
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().getName().startsWith(ChatColor.GOLD+"Click on the")&&JoinVerify.instance.verifying.contains(e.getPlayer())&&e.getPlayer() instanceof Player){
			Inventory i=e.getInventory();
			Player p=(Player)e.getPlayer();
			Bukkit.getScheduler().scheduleSyncDelayedTask(JoinVerify.instance, ()->p.openInventory(i), 20);
			
		}
			
		
	}
}
