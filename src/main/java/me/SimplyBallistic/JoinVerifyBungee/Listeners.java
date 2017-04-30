package me.SimplyBallistic.JoinVerifyBungee;

import java.util.UUID;

import me.SimplyBallistic.util.TextComponent;
import me.dommi2212.BungeeBridge.CustomPacketRecieveEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listeners implements Listener {
	private JoinVerifyBungee plugin=JoinVerifyBungee.instance;
	@EventHandler
	public void onCommand(ChatEvent e){
		if(e.getReceiver() instanceof ProxiedPlayer &&
				e.isCommand()&&
				!plugin.file.containsPlayer(((ProxiedPlayer)e.getReceiver()).getUniqueId()))
			e.setCancelled(true);
		
		
	}
	@EventHandler
	public void onPacketRecieve(CustomPacketRecieveEvent e){
		
		if(e.getChannel().equalsIgnoreCase("JoinVerify")){
			
			String request=(String)e.getSubject();
			plugin.getLogger().info("Incoming packet:  "+request);
			if(request.startsWith("isVerified")){
				if(JoinVerifyBungee.verifyAll){
					e.setAnswer(false);
					plugin.getLogger().info("Verifyall was set to true, player set for verifying...");
				}
				else{
					UUID id=UUID.fromString(request.split(":")[1]);
					final boolean verified=plugin.file.containsPlayer(id);
						e.setAnswer(verified);
						plugin.getLogger().info("Player isVerified was "+verified+". Sending to server");
						
				}
			}
			
			
			
			
			else if(request.startsWith("verified")){
				if(JoinVerifyBungee.verifyAll)e.setAnswer(false);
				else{
					UUID id=UUID.fromString(request.split(":")[1]);
					plugin.file.addPlayer(id);
					e.setAnswer(true);
					plugin.getLogger().info("Player was added to list");
					return;
					
					
				}
			}
			e.setAnswer(false);
		}
	}
	@EventHandler
	public void onJoin(ServerConnectEvent e){
		if(!plugin.file.containsPlayer(e.getPlayer().getUniqueId())&&e.getPlayer().getServer()!=null&&!e.getPlayer().hasPermission("joinverify.bypass")&&
				!JoinVerifyBungee.verifyAll){
			e.setCancelled(true);
			
			e.getPlayer().sendMessage(new TextComponent("You cannot join a server until you verify yourself!").setCol(ChatColor.RED));

		}
		
		
	}
	/*@EventHandler
	public void onJoin(ServerConnectedEvent e){
		if(plugin.file.containsPlayer(e.getPlayer().getUniqueId())){
			plugin.getProxy().getScheduler().schedule(plugin, ()->{
				 ByteArrayDataOutput out = ByteStreams.newDataOutput();
				  
				  out.writeUTF("Forward");
				  out.writeUTF(e.getServer().getInfo().getName());
				  out.writeUTF("JoinVerify");
				  
				  ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
				  DataOutputStream msgout = new DataOutputStream(msgbytes);
				  try {
					msgout.writeUTF(e.getPlayer().getUniqueId().toString()+":verified");
				} catch (IOException er) {
					plugin.getLogger().warning("Failed in sending packet to server! Player will be forced to verify. "+er.getMessage());
				} 
				

				  
				  out.write(msgbytes.toByteArray());
				  
				  
				  e.getPlayer().sendData("JoinVerify", out.toByteArray());
				  
				  
				  try {
					msgout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  

				
			}, 1, TimeUnit.SECONDS);
	}
	
	}*/

}
