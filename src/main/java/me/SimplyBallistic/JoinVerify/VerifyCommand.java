package me.SimplyBallistic.JoinVerify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VerifyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length!=1)
		return false;
		String name=args[0];
		if(name.equals("@all"))
			Bukkit.getOnlinePlayers().forEach(p->{
				JoinVerify.instance.testPlayer(p);
				sender.sendMessage("Running test on "+p.getName());
				
				
			});
		else{
			if(Bukkit.getPlayer(name)==null){
				sender.sendMessage(ChatColor.RED+"You need to give a valid player!");
				return true;
			}
			JoinVerify.instance.testPlayer(Bukkit.getPlayer(name));
			
		}
		return true;
	}
	
}
