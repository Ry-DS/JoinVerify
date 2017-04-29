package me.SimplyBallistic.JoinVerify.inventory;

import java.util.Collections;
import java.util.List;
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
	public final String answerBlock;
	private String pickName,itemName;
	private Random rand=new Random();
	public Tester(Player p, Plugin plugin, Runnable onPass,Runnable onWrong,List<String> sBlocks,String pickName,String itemName,String guiName) {
		this.pickName=pickName;
		this.itemName=itemName;
		answer=rand.nextInt(choices);
		Collections.shuffle(sBlocks);
		
		ItemStack[] blocks=new ItemStack[choices];
		for(int i=0;i<blocks.length;i++)
			try{
				blocks[i]=new ItemStack(Material.valueOf(sBlocks.get(i).replaceAll(" ", "_").toUpperCase()));
			}
		
		catch(Exception e){
			blocks[i]=new ItemStack(Material.values()[rand.nextInt(Material.values().length)]);}
		answerBlock=blocks[answer].getType().toString();
		//11-17 is slot range
		
		testMenu=new IconMenu(ChatColor.translateAlternateColorCodes('&', guiName.replaceAll("%item%",blocks[answer].getType().toString().replaceAll("_", " ") )), 27, event->{
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
				menu.setOption(i, blocks[answers], ChatColor.translateAlternateColorCodes('&', itemName).replaceAll("%item%", blocks[answers].getType().toString()), 
						answers==answer ? ChatColor.translateAlternateColorCodes('&',pickName ):"");//this is temp
				answers++;
				
			}
		}
	}
	public IconMenu getInventory(){
		return testMenu;
	}

}
