package me.SimplyBallistic.JoinVerify.inventory;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;
import org.bukkit.plugin.Plugin;

import me.SimplyBallistic.util.IconMenu;

public class Tester {
	IconMenu testMenu;
	int choices=7;
	Random rand=new Random();
	public Tester(Player p, Plugin plugin, Runnable onPass) {
		int answer=rand.nextInt(choices);
		ItemStack[] blocks=new ItemStack[choices];
		for(int i=0;i<blocks.length;i++)
			blocks[i]=new ItemStack(Material.values()[rand.nextInt(Material.values().length)]);
		//11-17 is slot range
		testMenu=new IconMenu(ChatColor.GOLD+"Click on the "+ChatColor.GREEN+ChatColor.BOLD+blocks[answer].getType().toString().replaceAll("_", " "), 27, event->{
			if(event.getPosition()<11||event.getPosition()>17)
				event.setWillClose(false);
			else if(event.getPosition()-11==answer){
				event.setWillClose(true);
				event.setWillDestroy(true);
				onPass.run();
				
			}else
				event.setWillClose(false);
			
		}, plugin);
		initMenu(testMenu,blocks);
	}
	private void initMenu(IconMenu menu,ItemStack[]blocks){
		int answers=0;
		for(int i=1;i<=27;i++){
			if(i<11||i>17){ 
				ItemStack glassPane=new ItemStack(Material.STAINED_GLASS,1,(short)rand.nextInt(15));
				menu.setOption(i, glassPane, "", "");
				
				
			}
			else {
				menu.setOption(i-11, blocks[answers], ChatColor.GREEN.toString()+ChatColor.BOLD.toString()+blocks[answers].getType().toString(), true?"":"");//this is temp
				
			}
		}
	}

}
