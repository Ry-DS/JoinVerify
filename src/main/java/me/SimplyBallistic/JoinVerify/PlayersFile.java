package me.SimplyBallistic.JoinVerify;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayersFile extends YamlConfiguration{
	private File file;
	private JoinVerify plugin=JoinVerify.instance;
	public PlayersFile() {
		
		file=new File(plugin.getDataFolder(), "verified.yml");
		try {
			load(file);
			plugin.getLogger().info("Player file successfully loaded!");
			
		} catch (Exception e) {
			plugin.getLogger().info("Player file not found/corrupted!\n Generating new file...");
			
			try {
				file.delete();
				file.createNewFile();
				load(file);
			} catch (IOException | InvalidConfigurationException e1) {
				plugin.getLogger().info("Failed generating file: "+e1.getMessage()+"! All players will be verified");
				JoinVerify.verifyAll=true;
				
			}
			
			
		}
	}
	public void saveConfig(){
		try {
			save(file);
		} catch (IOException e) {
			plugin.getLogger().info("Failed in saving player file! "+e.getMessage());
		}
	}
	public void addPlayer(UUID p){
		List<String> lst=getStringList("verified-players");
		lst.add(p.toString());
		set("verified-players", lst);
		saveConfig();
	}
	public boolean containsPlayer(UUID p){
		if(getStringList("verified-players").contains(p.toString()))
			return true;
		return false;
	}
	public void reload(CommandSender p) {
		plugin.reloadConfig();
		try {
			
			load(file);
			
		} catch (Exception e) {
			
			try {
				file.delete();
				file.createNewFile();
				load(file);
				
			} catch (Exception e1) {
				p.sendMessage(ChatColor.RED+"Failed to reload the config! "+e1.getMessage());
			}
			
		}

	}
}
