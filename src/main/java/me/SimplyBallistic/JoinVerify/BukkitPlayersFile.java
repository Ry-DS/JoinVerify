package me.SimplyBallistic.JoinVerify;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.SimplyBallistic.util.PlayersFile;

public class BukkitPlayersFile extends YamlConfiguration implements PlayersFile{
	private File file;
	private Plugin plugin=JoinVerify.instance;
	public BukkitPlayersFile() {
		
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
	@Override
	public void saveConfig(){
		try {
			save(file);
		} catch (IOException e) {
			plugin.getLogger().info("Failed in saving player file! "+e.getMessage());
		}
	}
	@Override
	public void addPlayer(UUID p){
		List<String> lst=getStringList("verified-players");
		if(!lst.contains(p.toString()))
		lst.add(p.toString());
		set("verified-players", lst);
		saveConfig();
	}
	@Override
	public boolean containsPlayer(UUID p){
			return getStringList("verified-players").contains(p.toString());
	}
	@Override
	public void reload(Object ob) {
		plugin.reloadConfig();
		CommandSender p=(CommandSender)ob;
		try {
			file=new File(plugin.getDataFolder(), "verified.yml");
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
