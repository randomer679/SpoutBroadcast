package me.randomer679.SpoutBroadcast.Listeners;

import me.randomer679.SpoutBroadcast.SpoutBroadcast;
import me.randomer679.SpoutBroadcast.SpoutFeatures;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutBroadcastListener implements Listener{
	
	private SpoutFeatures spoutFeatures;
	
	public SpoutBroadcastListener(SpoutBroadcast spoutBroadcast) {
		this.spoutFeatures = spoutBroadcast.spoutFeatures;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event){
		SpoutPlayer spoutPlayer = event.getPlayer();
		spoutFeatures.setupLabel(spoutPlayer);
	}
}
