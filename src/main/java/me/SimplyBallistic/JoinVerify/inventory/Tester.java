package me.SimplyBallistic.JoinVerify.inventory;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.SimplyBallistic.util.IconMenu;

public class Tester {
	private IconMenu testMenu;
	private int choices=7;
	public final int answer;
	private Random rand=new Random();
	public Tester(Player p, Plugin plugin, Runnable onPass,Runnable onWrong) {
		answer=rand.nextInt(choices);
		ItemStack[] blocks=new ItemStack[choices];
		for(int i=0;i<blocks.length;i++)
			blocks[i]=new ItemStack(Material.values()[rand.nextInt(Material.values().length)]);
		//11-17 is slot range
		testMenu=new IconMenu(ChatColor.GOLD+"Click on the "+ChatColor.GREEN+ChatColor.BOLD+blocks[answer].getType().toString().replaceAll("_", " "), 27, event->{
			if(event.getPosition()<10||event.getPosition()>16)
				event.setWillClose(false);
			else if(event.getPosition()-10==answer){
				event.setWillClose(true);
				event.setWillDestroy(true);
				onPass.run();
				
			}else{
				event.setWillClose(false);
				onWrong.run();}
			
		}, plugin);
		initMenu(testMenu,blocks);
	}
	private void initMenu(IconMenu menu,ItemStack[]blocks){
		int answers=0;
		ItemStack glassPane=new ItemStack(Material.STAINED_GLASS_PANE,1,(short)rand.nextInt(14));
		for(int i=0;i<27;i++){
			if(i<10||i>16){ 
				
				menu.setOption(i, glassPane, "", "");
				
				
			}
			else {
				menu.setOption(i, blocks[answers], ChatColor.GREEN.toString()+ChatColor.BOLD.toString()+blocks[answers].getType().toString(), 
						answers==answer ? "Pick me!":"");//this is temp
				answers++;
				
			}
		}
	}
	public IconMenu getInventory(){
		return testMenu;
	}

}
