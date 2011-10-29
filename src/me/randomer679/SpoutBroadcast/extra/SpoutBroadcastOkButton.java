package me.randomer679.SpoutBroadcast.extra;

import me.randomer679.SpoutBroadcast.SpoutFeatures;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.screen.*;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutBroadcastOkButton extends GenericButton{

	private SpoutFeatures spoutFeatures = new SpoutFeatures();
	
	public SpoutBroadcastOkButton() {
        super();
        setText("Ok");
    }
	
	public void onButtonClick(ButtonClickEvent event){
		SpoutPlayer spoutPlayer = event.getPlayer();
		spoutPlayer.getMainScreen().closePopup();
		if(spoutFeatures.notifyMe.containsKey(spoutPlayer)){
			Player player = spoutFeatures.notifyMe.get(spoutPlayer);
			spoutFeatures.notifyMe.remove(spoutPlayer);
			player.sendMessage("SBroad - "+spoutPlayer.getDisplayName()+" has acknowledged your message!");
		}
	}
}
