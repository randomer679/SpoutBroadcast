package me.randomer679.SpoutBroadcast.API;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.Color;

import me.randomer679.SpoutBroadcast.SpoutBroadcast;
import me.randomer679.SpoutBroadcast.SpoutFeatures;

public class API {

	private SpoutFeatures spoutFeatures = new SpoutFeatures();
	private SpoutBroadcast spoutBroadcast = new SpoutBroadcast();
	
	/**
	 * Broadcasts a message to all the players on the server. All params required. 
	 * @param message The message that is to be displayed.
	 * @param red A number between 0 and 255. Sets the amount of red in the broadcast colour.
	 * @param green A number between 0 and 255. Sets the amount of green in the broadcast colour.
	 * @param blue A number between 0 and 255. Sets the amount of blue in the broadcast colour.
	 */
	public void globalBroadcast(String message, int red, int green, int blue){
		Player[] playerList = spoutBroadcast.getServer().getOnlinePlayers();
		Color colour = new Color(new Float(red) / 255, new Float(green) / 255, new Float(blue) / 255);
		spoutFeatures.globalOverride("none", colour, message, playerList);
	}
	
	/**
	 * Creates a pop-up on the players screen with the message and the senderName. All params required.
	 * @param player The player who will receive the message.
	 * @param message The message to be sent
	 * @param senderName The name to be displayed as the sender. Preferably your plugin name.
	 * @param red A number between 0 and 255. Sets the amount of red in the broadcast colour.
	 * @param green A number between 0 and 255. Sets the amount of green in the broadcast colour.
	 * @param blue A number between 0 and 255. Sets the amount of blue in the broadcast colour.
	 */
	public void playerBroadcast(Player player, String message, String senderName, int red, int green, int blue){
		Color colour = new Color(new Float(red) / 255, new Float(green) / 255, new Float(blue) / 255);
		spoutFeatures.playerOverride("none", message, player, null, senderName, colour);
	}
	
}
