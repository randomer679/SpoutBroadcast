package me.randomer679.SpoutBroadcast;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.randomer679.SpoutBroadcast.Config.Config;
import me.randomer679.SpoutBroadcast.Config.Messages;
import me.randomer679.SpoutBroadcast.extra.SpoutBroadcastOkButton;
import me.randomer679.SpoutBroadcast.schedules.GlobalSchedule;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutFeatures {

	public Map<SpoutPlayer, Player> notifyMe = new HashMap<SpoutPlayer, Player>();
	public volatile boolean global;
	private Map<SpoutPlayer, UUID> labelUuid = new HashMap<SpoutPlayer, UUID>();
	private int messageNumber = 1;
	private String messageNumberString = "";
	private SpoutBroadcast spoutBroadcast = new SpoutBroadcast();
	private Color globalColour;
	private int r;
	private int g;
	private int b;
	private Color playerColour;
	private Runnable globalSchedule;
	private Color colour;
	private String colourPlayer;
	private Messages messages = new Messages();
	private Color defaultColour;
	private String messageTemp;
	private String colourSay;
	private String message;
	private Config config = new Config();
	private Label senderLabel = new GenericLabel();

	public void globalOverride(String colour, Color color, String message,
			Player[] playerList) {
		if (colour.equalsIgnoreCase("none")) {
			if (color != null) {
				globalColour = color;
			} else {
				globalColour = new Color(
						new Float(config.globalDefaultRed) / 255, new Float(
								config.globalDefaultGreen) / 255, new Float(
								config.globalDefaultBlue) / 255);
			}
		} else {
			r = spoutBroadcast.config.getInt("colours." + colour + ".r", 0);
			g = spoutBroadcast.config.getInt("colours." + colour + ".g", 0);
			b = spoutBroadcast.config.getInt("colours." + colour + ".b", 0);
			globalColour = new Color(new Float(r) / 255, new Float(g) / 255,
					new Float(b) / 255);
		}
		for (Player player : playerList) {
			SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player);
			UUID labelID = labelUuid.get(spoutPlayer);
			if (labelID != null) {
				GenericLabel label = (GenericLabel) spoutPlayer.getMainScreen()
						.getWidget(labelID);
				label.setText(message).setTextColor(globalColour)
						.setDirty(true);
			}
		}
		global = true;
		globalSchedule = new GlobalSchedule();
		spoutBroadcast.scheduler.scheduleAsyncDelayedTask(
				spoutBroadcast.instance, globalSchedule,
				config.globalInterval * 20);
	}

	public void player(String arg1, String arg2, String[] args,
			CommandSender sender) {
		messageTemp = "";
		String arg3 = args[2];
		Player[] players = spoutBroadcast.instance.getServer()
				.getOnlinePlayers();
		if (arg3.startsWith("-")) {
			colourPlayer = arg3.substring(1);
			for (Player player : players) {
				if (player.getName().equalsIgnoreCase(arg2)) {
					String tempLengthCheck = arg1 + " " + arg2 + " " + arg3;
					Integer lengthCheck = tempLengthCheck.length() + 1;
					for (String arg : args) {
						messageTemp += " " + arg;
					}
					message = messageTemp.substring(lengthCheck).trim();
					playerOverride(colourPlayer, message, player,
							(Player) sender, "none", null);
				}
			}
		} else {
			for (Player player : players) {
				if (player.getName().equalsIgnoreCase(arg2)) {
					String tempLengthCheck = arg1 + " " + arg2;
					Integer lengthCheck = tempLengthCheck.length() + 1;
					for (String arg : args) {
						messageTemp += " " + arg;
					}
					message = messageTemp.substring(lengthCheck).trim();
					playerOverride("none", message, player, (Player) sender,
							"none", null);
				}
			}
		}
	}

	public void playerOverride(String colour, String message, Player player,
			Player sender, String senderName, Color color) {
		if (colour.equalsIgnoreCase("none")) {
			if (color != null) {
				playerColour = color;
			} else {
				playerColour = new Color(
						new Float(config.playerDefaultRed) / 255, new Float(
								config.playerDefaultGreen) / 255, new Float(
								config.playerDefaultBlue) / 255);
			}
		} else {
			r = spoutBroadcast.config.getInt("colours." + colour + ".r", 0);
			g = spoutBroadcast.config.getInt("colours." + colour + ".g", 0);
			b = spoutBroadcast.config.getInt("colours." + colour + ".b", 0);
			playerColour = new Color(new Float(r) / 255, new Float(g) / 255,
					new Float(b) / 255);
		}
			SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player);
		if (sender == null) {
			senderLabel.setText("Sent By "+senderName);
			senderLabel.setX(2).setY(5).setAnchor(WidgetAnchor.CENTER_LEFT);
		} else {
			notifyMe.put(spoutPlayer, sender);
		}
			Label label = new GenericLabel();
			label.setText(message).setTextColor(playerColour);
			label.setX(2).setY(0).setAnchor(WidgetAnchor.CENTER_LEFT);
			GenericButton button = new SpoutBroadcastOkButton();
			button.setColor(new Color(1.0F, 1.0F, 0, 1.0F));
			button.setHoverColor(new Color(1.0F, 0, 0, 1.0F));
			button.setX(50).setY(-20).setAnchor(WidgetAnchor.BOTTOM_LEFT);
			button.setWidth(50).setHeight(20);
			PopupScreen popup = new GenericPopup();
			popup.attachWidget(spoutBroadcast.instance, label);
			popup.attachWidget(spoutBroadcast.instance, button);
			popup.attachWidget(spoutBroadcast.instance, senderLabel);
			spoutPlayer.getMainScreen().attachPopupScreen(popup);
	}

	public void say(String arg1, String arg2, String[] args) {
		messageTemp = "";
		if (arg2.startsWith("-")) {
			colourSay = arg2.substring(1);
			String tempLengthCheck = arg1 + " " + arg2;
			Integer lengthCheck = tempLengthCheck.length() + 1;
			for (String arg : args) {
				messageTemp += " " + arg;
			}
			message = messageTemp.substring(lengthCheck).trim();
			Player[] playerList = spoutBroadcast.instance.getServer()
					.getOnlinePlayers();
			globalOverride(colourSay, null, message, playerList);
		} else {
			for (String arg : args) {
				messageTemp += " " + arg;
			}
			message = messageTemp.substring(4).trim();
			Player[] playerList = spoutBroadcast.instance.getServer()
					.getOnlinePlayers();
			globalOverride("none", null, message, playerList);
		}
	}

	public void scheduledBroadcasts() {
		messageNumberString = Integer.toString(messageNumber).trim();
		String message = messages.message.getString(messageNumberString
				+ ".message");
		if (message == null) {
			messageNumber = 1;
			scheduledBroadcasts();
			return;
		} else {
			if (!global) {
				colour = new Color(
						new Float(messages.message.getInt(messageNumberString
								+ ".colour.r", config.broadcastDefaultRed)) / 255,
						new Float(messages.message.getInt(messageNumberString
								+ ".colour.g", config.broadcastDefaultGreen)) / 255,
						new Float(messages.message.getInt(messageNumberString
								+ ".colour.b", config.broadcastDefaultBlue)) / 255);
				Player[] playerList = spoutBroadcast.instance.getServer()
						.getOnlinePlayers();
				for (Player player : playerList) {
					SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player);
					if (spoutPlayer.isSpoutCraftEnabled()) {
						UUID labelID = labelUuid.get(spoutPlayer);
						if (labelID != null) {
							GenericLabel label = (GenericLabel) spoutPlayer
									.getMainScreen().getWidget(labelID);
							label.setText(message).setTextColor(colour)
									.setDirty(true);
						}
					}
				}
			}
		}
		messageNumber++;
	}

	public void setupLabel(SpoutPlayer spoutPlayer) {
		GenericLabel label = new GenericLabel();
		UUID labelID = label.getId();
		labelUuid.put(spoutPlayer, labelID);
		int locationY = 5;
		int locationX = 5;
		if (!config.useDefaultLocation) {
			locationY = config.labelLocationY;
			locationX = config.labelLocationX;
		}
		label.setY(locationY).setX(locationX).setAnchor(WidgetAnchor.TOP_LEFT);
		defaultColour = new Color(new Float(config.broadcastDefaultRed) / 255,
				new Float(config.broadcastDefaultGreen) / 255, new Float(
						config.broadcastDefaultBlue) / 255);
		label.setTextColor(defaultColour);
		spoutPlayer.getMainScreen()
				.attachWidget(spoutBroadcast.instance, label);
	}
	
}
