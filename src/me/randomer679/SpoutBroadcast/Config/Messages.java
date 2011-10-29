package me.randomer679.SpoutBroadcast.Config;

import java.io.File;
import java.io.IOException;

import me.randomer679.SpoutBroadcast.SpoutBroadcast;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
	
	private SpoutBroadcast spoutBroadcast = new SpoutBroadcast();
	public FileConfiguration message;

	public void messagesWrite(FileConfiguration messages, File messagesFile){
		messages = new YamlConfiguration();
		messages.set("1.message", "This is a message.");
		messages.set("2.message", "This is another message.");
		messages.set("3.message", "This is a message with a colour override.");
		messages.set("3.colour.r", 123);
		messages.set("3.colour.g", 123);
		messages.set("3.colour.b", 123);
		try {
			messages.save(messagesFile);
		} catch (IOException e) {
			spoutBroadcast.log.info(spoutBroadcast.prefix+"");
		}
		messagesLoad(messages, messagesFile);
	}
		
	public void messagesLoad(FileConfiguration messages, File messagesFile){
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		message = messages;
	}
}
