package me.randomer679.SpoutBroadcast.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public long broadcastInterval;
	public long globalInterval;
	public int broadcastDefaultRed;
	public int broadcastDefaultGreen;
	public int broadcastDefaultBlue;
	public int playerDefaultRed;
	public int playerDefaultGreen;
	public int playerDefaultBlue;
	public int globalDefaultRed;
	public int globalDefaultGreen;
	public int globalDefaultBlue;
	public int labelLocationX;
	public int labelLocationY;
	public boolean useDefaultLocation;
	
	public void makeConfig(FileConfiguration config, File configFile){
		config = new YamlConfiguration();
		config.set("useDefaultLabelLocation", false);
		config.set("labelLocation.x", 5);
		config.set("labelLocation.y", 5);
		config.set("broadcast.interval", 60);
		config.set("broadcast.defaultColour.red", 0);
		config.set("broadcast.defaultColour.green", 0);
		config.set("broadcast.defaultColour.blue", 255);
		config.set("player.defaultColour.red", 255);
		config.set("player.defaultColour.green", 0);
		config.set("player.defaultColour.blue", 0);
		config.set("global.interval", 60);
		config.set("global.defaultColour.red", 0);
		config.set("global.defaultColour.green", 0);
		config.set("global.defaultColour.blue", 255);
		config.set("colours.blue.r", 0);
		config.set("colours.blue.g", 0);
		config.set("colours.blue.b", 255);
		config.set("colours.red.r", 255);
		config.set("colours.red.g", 0);
		config.set("colours.red.b", 0);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadConfig(config, configFile);
	}
	
	public void loadConfig(FileConfiguration config, File configFile){
		config = YamlConfiguration.loadConfiguration(configFile);
		useDefaultLocation = config.getBoolean("useDefaultLabelLocation", false);
		labelLocationX = config.getInt("labelLocation.x", 5);
		labelLocationY = config.getInt("labelLocation,y", 5);
		broadcastInterval = config.getInt("broadcast.interval", 60);
		broadcastDefaultRed = config.getInt("broadcast.defaultColour.red", 0);
		broadcastDefaultGreen = config.getInt("broadcast.defaultColour.green", 0);
		broadcastDefaultBlue = config.getInt("broadcast.defaultColour.blue", 255);
		playerDefaultRed = config.getInt("player.defaultColour.red", 0);
		playerDefaultGreen = config.getInt("player.defaultColour.green", 0);
		playerDefaultBlue = config.getInt("player.defaultColour.blue", 255);
		globalInterval = config.getInt("global.interval", 60);
		globalDefaultRed = config.getInt("global.defaultColour.red", 0);
		globalDefaultGreen = config.getInt("global.defaultColour.green", 0);
		globalDefaultBlue = config.getInt("global.defaultColour.blue", 255);
		
	}
	
}
