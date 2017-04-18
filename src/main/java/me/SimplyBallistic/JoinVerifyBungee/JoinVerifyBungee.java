package me.SimplyBallistic.JoinVerifyBungee;

import me.dommi2212.BungeeBridge.CustomPacketRecieveEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class JoinVerifyBungee extends Plugin implements Listener{
	public static JoinVerifyBungee instance;
	public static boolean verifyAll;
	@Override
	public void onEnable() {
		getLogger().info("Staring...");
		
		if(getProxy().getPluginManager().getPlugin("BungeeBridgeS")==null){
			getLogger().severe("Bungee Bridge isn't installed! Shutting Down...");
			return;
		}
		instance=this;
		getProxy().getPluginManager().registerListener(this, this);
	}
	@EventHandler
	public void onPacketRecieve(CustomPacketRecieveEvent e){
		
		
		
	}
}
