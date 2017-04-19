package me.SimplyBallistic.util;

import net.md_5.bungee.api.ChatColor;

public class TextComponent extends net.md_5.bungee.api.chat.TextComponent {

public TextComponent(String string) {
	super(string);
	}
public  TextComponent setCol(ChatColor c){
	super.setColor(c);
	return this;
	
}
public  TextComponent setItalic(boolean set){
	super.setItalic(set);
	return this;
	
}
public  TextComponent setBold(boolean set){
	super.setBold(set);
	return this;
	
}

}
