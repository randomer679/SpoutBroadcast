package me.randomer679.SpoutBroadcast;

import java.io.File;
import java.util.logging.Logger;

import me.randomer679.SpoutBroadcast.Config.Config;
import me.randomer679.SpoutBroadcast.Config.Messages;
import me.randomer679.SpoutBroadcast.Listeners.SpoutBroadcastSpoutListener;
import me.randomer679.SpoutBroadcast.extra.Errors;
import me.randomer679.SpoutBroadcast.schedules.BroadcastSchedule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.getspout.spoutapi.gui.Color;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SpoutBroadcast extends JavaPlugin {

	public Messages messagesClass = new Messages(this);
	public Config configOptions = new Config(this);
	public SpoutFeatures spoutFeatures = new SpoutFeatures(this);
	private SpoutBroadcastSpoutListener spoutListener = new SpoutBroadcastSpoutListener(this);
	private BroadcastSchedule broadcastSchedule = new BroadcastSchedule(this);
	private FileConfiguration messages;
	private PluginManager pm;
	private PermissionManager permissions = null;
	private Errors errors = new Errors();
	public BukkitScheduler scheduler;
	public Logger log = Logger.getLogger("SpoutBroadcast");
	public SpoutBroadcast instance;
	public String prefix = "Spoutbroadcast - ";
	public File folder;

	public SpoutBroadcast() {
		this.instance = this;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player playerTemp = (Player) sender;
		if(permissions == null && !playerTemp.isOp()){
			errors.error(1);
			return true;
		}else if(permissions != null || playerTemp.isOp()){
		if (args.length < 2) {
				String error = errors.error(0);
				sender.sendMessage(error);
				return false;
			}
			String arg1 = args[0];
			String arg2 = args[1];
			if(arg1.equalsIgnoreCase("say")){
				if(permissions != null){
					if(permissions.has(playerTemp, "spoutbroadcast.say") || playerTemp.isOp()){
						spoutFeatures.say(arg1, arg2, args);
					}else{
						errors.error(1);
						return true;
					}
				}else{
					if(playerTemp.isOp()){
						spoutFeatures.say(arg1, arg2, args);
						return true;
					}else{
						errors.error(1);
						return true;
					}
				}
			}else if(arg1.equalsIgnoreCase("player")){
				if(permissions != null){
					if(permissions.has(playerTemp, "spoutbroadcast.player") || playerTemp.isOp()){
						spoutFeatures.player(arg1, arg2, args, sender);
						return true;
					}else{
						errors.error(1);
						return true;
					}
				}else{
					if(playerTemp.isOp()){
						spoutFeatures.player(arg1, arg2, args, sender);
						return true;
					}else{
						errors.error(1);
						return true;
					}
				}
			}	
		}
		return true;
	}
	
	@Override
	public void onDisable() {
		scheduler.cancelTasks(this);
		log.info("SpoutBroadcast Disabled");
	}

	@Override
	public void onEnable() {
		log.info("Enabling SpoutBroadcast");
		pm = getServer().getPluginManager();
		folder = getDataFolder();
		log.info(prefix + "Checking for SpoutBroadcast Folder.");
		if (!folder.exists()) {
			log.info(prefix + "Folder Not Found, Creating Folder.");
			folder.mkdir();
		}
		File configFile = new File(folder, "config.yml");
		File messagesFile = new File(folder, "messages.yml");
		log.info(prefix + "Attempting to load config.");
		if (!configFile.exists()) {
			log.info(prefix + "Config file not found, creating one now.");
			configOptions.makeConfig(configFile);
			log.info(prefix + "Config file created.");
		} else if (configFile.exists()) {
			configOptions.loadConfig(configFile);
			log.info(prefix + "Config loaded.");
		}
		log.info(prefix + "Attempting to load messages.");
		if (!messagesFile.exists()) {
			log.info("Messages file not found, creating one now.");
			messagesClass.messagesWrite(messages, messagesFile);
			log.info(prefix + "Messages file created.");
		} else if (messagesFile.exists()) {
			messagesClass.messagesLoad(messages, messagesFile);
			log.info(prefix + "Messages loaded.");
		}
		log.info(prefix+"Checking whether to use permissions.");
		if(this.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			permissions = PermissionsEx.getPermissionManager();
		}
		log.info(prefix + "Registering Events.");
		pm.registerEvent(Type.CUSTOM_EVENT, spoutListener, Priority.Monitor,
				this);

		scheduler = getServer().getScheduler();
		scheduler.scheduleAsyncRepeatingTask(this, broadcastSchedule, 20,
				configOptions.broadcastInterval * 20);
		log.info("SpoutBroadcast " + getDescription().getVersion()
				+ " Is Enabled.");
		if (getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
			permissions = PermissionsEx.getPermissionManager();
		}
	}
	
	/**
	 * Broadcasts a message to all the players on the server. All params required. 
	 * @param message The message that is to be displayed.
	 * @param red A number between 0 and 255. Sets the amount of red in the broadcast colour.
	 * @param green A number between 0 and 255. Sets the amount of green in the broadcast colour.
	 * @param blue A number between 0 and 255. Sets the amount of blue in the broadcast colour.
	 */
	public void globalBroadcast(String message, int red, int green, int blue){
		Player[] playerList = this.getServer().getOnlinePlayers();
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