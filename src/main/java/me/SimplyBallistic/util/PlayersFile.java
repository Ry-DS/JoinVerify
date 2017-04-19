package me.SimplyBallistic.util;

import java.util.UUID;

public interface PlayersFile {
	public void saveConfig();
	public void addPlayer(UUID id);
	public boolean containsPlayer(UUID id);
	public void reload(Object p);

}
