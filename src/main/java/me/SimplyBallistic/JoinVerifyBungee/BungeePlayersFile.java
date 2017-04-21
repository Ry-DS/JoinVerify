package me.SimplyBallistic.JoinVerifyBungee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.SimplyBallistic.util.PlayersFile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeePlayersFile implements PlayersFile{
	private File file;
	private Configuration config;
	private JoinVerifyBungee plugin=JoinVerifyBungee.instance;
	public BungeePlayersFile() {
		
		
		file=new File(plugin.getDataFolder(), "verified.yml");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			
		
		try {
			config=ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			plugin.getLogger().info("Player file successfully loaded!");
			
		} catch (Exception e) {
			plugin.getLogger().info("Player file not found/corrupted!\n Generating new file...");
			
			try {
				file.delete();
				file.createNewFile();
				config=ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			} catch (IOException e1) {
				plugin.getLogger().info("Failed generating file: "+e1.getMessage()+"! All players will be verified");
				JoinVerifyBungee.verifyAll=true;
				
			}
			
			
		}
		if(!JoinVerifyBungee.verifyAll
				&&config.getStringList("verified-players")==null){
			config.set("verified-players", new ArrayList<>());
			saveConfig();
		}
	}
	@Override
	public void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			plugin.getLogger().info("Failed in saving player file! "+e.getMessage());
		}
	}
	@Override
	public void addPlayer(UUID p){
		List<String> lst=config.getStringList("verified-players");
		if(lst.contains(p.toString()))
			return;
		lst.add(p.toString());
		config.set("verified-players", lst);
		saveConfig();
	}
	@Override
	public boolean containsPlayer(UUID p){
		if(config.getStringList("verified-players").contains(p.toString()))
			return true;
		return false;
	}
	@Override
	public void reload(Object ob) {
		CommandSender p=(CommandSender)ob;
		try {
			
			config=ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			
		} catch (Exception e) {
			
			try {
				file.delete();
				file.createNewFile();
				config=ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				
			} catch (Exception e1) {
				p.sendMessage(new TextComponent("Failed in reloading players config!"));
			}
			
		}

	}
	
}
