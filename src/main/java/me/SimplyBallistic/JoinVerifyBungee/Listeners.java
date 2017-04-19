package me.SimplyBallistic.JoinVerifyBungee;

import java.util.concurrent.TimeUnit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.SimplyBallistic.util.TextComponent;
import me.dommi2212.BungeeBridge.CustomPacketRecieveEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listeners implements Listener {
	private JoinVerifyBungee plugin=JoinVerifyBungee.instance;
	@EventHandler
	public void onCommand(ChatEvent e){
		if(e.getReceiver() instanceof ProxiedPlayer &&
				e.isCommand()&&
				!plugin.file.containsPlayer(((ProxiedPlayer)e.getReceiver()).getUniqueId()));
		
		
	}
	@EventHandler
	public void onPacketRecieve(CustomPacketRecieveEvent e){
		
		
	}
	@EventHandler
	public void onJoin(ServerConnectEvent e){
		if(!plugin.file.containsPlayer(e.getPlayer().getUniqueId())&&e.getPlayer().getServer()!=null){
			e.setCancelled(true);
			
			e.getPlayer().sendMessage(new TextComponent("You cannot join a server until you verify yourself!").setCol(ChatColor.RED));

		}
		
		
	}
	@EventHandler
	public void onJoin(ServerConnectedEvent e){
		if(!plugin.file.containsPlayer(e.getPlayer().getUniqueId())){
			plugin.getProxy().getScheduler().schedule(plugin, ()->{
				 ByteArrayDataOutput out = ByteStreams.newDataOutput();
				  
				  out.writeUTF("Forward");
				  out.writeUTF(e.getServer().getInfo().getName());
				  out.writeUTF("JoinVerify");

				
			}, 1, TimeUnit.SECONDS);
	}
	
	}

}
