package me.randomer679.SpoutBroadcast.Listeners;

import me.randomer679.SpoutBroadcast.SpoutFeatures;

import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutBroadcastSpoutListener extends SpoutListener{
	
	private SpoutFeatures spoutFeatures = new SpoutFeatures();
	
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event){
		SpoutPlayer spoutPlayer = event.getPlayer();
		spoutFeatures.setupLabel(spoutPlayer);
	}
}
