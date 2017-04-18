package me.SimplyBallistic.JoinVerify;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author ryan9_000
 *
 */
public class JoinVerify extends JavaPlugin{
	public static JoinVerify instance;
	public PlayersFile verified;
	public static boolean verifyAll;
	@Override
	public void onEnable() {
		getLogger().info("Starting up...");
		
		saveDefaultConfig();
		
		verifyAll=getConfig().getBoolean("verify-all",false);
		instance=this;
		verified=new PlayersFile();
	}
    
}
