package tk.mythicaldimensions.gamemanager;

import java.util.HashMap;
/**
 * The main class that extends JavaPlugin This is where we register any event
 * listeners or take any actions that are required on startup or shutdown
 * 
 * @author [Empty]
 */
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import tk.mythicaldimensions.essentials.utility.Npc;
//import tk.mythicaldimensions.essentials.utility.Npc;
import tk.mythicaldimensions.gamemanager.commands.GamemagerCommands;
import tk.mythicaldimensions.gamemanager.config.GamemagerConfig;
import tk.mythicaldimensions.gamemanager.events.GamemangerEvents;
import tk.mythicaldimensions.gamemanager.utility.GamemanagerUtil;
import tk.mythicaldimensions.gamemanager.utility.Holograms;
import tk.mythicaldimensions.gamemanager.utility.Worlds;

public class GamemanagerPlugin extends JavaPlugin {
	
	// Skin
	private final String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYwMzEzMTAyNzM1OCwKICAicHJvZmlsZUlkIiA6ICJmNTg1YmU1MTMwNTk0MTdlYWMxZjRhNDhiZDQyODBkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJQdW5rcm9jem9tYmkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlMjMxNGYwZWNlN2Y3NDg3OTU1ODI2ODM1MWEyOTM2YWY2NmU0NThhY2FjNTVmZWRmZjM4MWQ3YjE5YjAyMiIKICAgIH0KICB9Cn0=";
	private final String signature = "vaGj2+e/02j2YGngsW2Q2n9FWg++yeb96aGtSxD3THl31ZlNiH564uJgmRga3hdqsu/E9yvloMLOnlFxizVu/fp7DLJyMm+EaFrGnejMEgLQQS3DkuMBNhPsqTjCstGnLCw9kC+JmiaGKvR2IKUvqtL3xSmTq3nspoD9YMZexCZpwAM00kr1HP57UfR/9/PkmRzmr1+3PQQ1ZYVGAnmDylTXen0B/Pl5TvFIiumxL238TJGEOTOO480493ykuKHGNhcdQWUqtGCm4C4oj5+NLCGVSNVl7ACsQw2FoFoA1Wu91FA6rinJvcmDpNT1BkiQ5Bgvp3A4JQhuq2NjbLKZqpQE2p/OfJHLHusCcMvLRgjzhSMUyW/A8uBc1MOxeT1qyxwx9cdZTojTVIwbGkLgTUyfML3zMnEirsj+XL/WJ9nYGnS2eM0tkXVMaIsrTKFO0O1P/tjcP6yEFhvrUxiH9pQJ2cYWh5hGoH8udNocdR1G32IXSAvryaL3J9xyMA+m6e/VL0vjaL9xk9LC5dDElraTcwgUUqD7KXYNHs1YIwD3OvqPnd+phBWQ/v8Lyi6gNtd1R3vZa3GmFd6Qu8JEEuy9b+gaFG3xqcDIWp/8KW/47TRJrDrtGkzLBtS/oO7fGHHB5F3XAjleSlrEw+AUztoi7HjRLu9W+h1tmmfgQ7w=";
	
	// Logger instance for logging debug messages and information about what the plugin is doing
	private final Logger logger = Logger.getLogger("Gamemanager");
	
	// Our internal config object for storing the configuration options that server
	private static GamemagerConfig config;

	// An instance of this plugin for easy access
	private static GamemanagerPlugin plugin;
	
	// Our internal worlds objects
	private static Worlds worlds;
	
	// Our internal holograms objects
	private static Holograms holo;
	
	// Heart Beat
	public static int beatTime = 0;
	// Delete world
	public static boolean delete = false;
	// World Name
	public static String world = "";
	// Start time
	public static long startTime = System.currentTimeMillis();
	// Debug
	public static boolean debug = false;
	
	// Selection
	public static HashMap<String, Location[]> selection = new HashMap<String, Location[]>();
	// InHub
	public static HashMap<String, Boolean> inHub = new HashMap<String, Boolean>();
	
	/**
	 * Ran when plugin is enabled Set static instance of this class Register event
	 * listeners Create configuration object Set command executor Register
	 * permissions
	 */
	@Override
	public void onEnable() {
		plugin = this;
		// Register Events
		getLogger().info("[GM] Registering Events");
		Bukkit.getPluginManager().registerEvents(new GamemangerEvents(), this);
		// Getting config
		getLogger().info("[GM] Getting Config");
		config = new GamemagerConfig();
		// Loading Worlds
		getLogger().info("[GM] Loading Worlds");
		worlds = new Worlds();
		GamemanagerUtil.loadWorlds();
		// Loading holograms
		getLogger().info("[GM] Loading Holograms");
		holo = new Holograms();
		// Register Commands
		getLogger().info("[GM] Registering Commands");
		this.getCommand("gamemanager").setExecutor(new GamemagerCommands());
		// Register Perms
		getLogger().info("[GM] Registering Permissions");
		GamemanagerUtil.registerPermissions();
		getLogger().info("[GM] Plugin Enabled");
		
		/**
		 * Create NPCs
		 */
		// MinigameHub
		Npc.createNPC(199.5, 4.0, -34.5, 0, 0, "SCPSL", texture, signature, "hub");
		
		/**
		 * Ran constantly aslong the plugin is registered Controls all plugin logic
		 */
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				/** 
				 * Timing
				 */
				long elapsedTime = System.currentTimeMillis() - startTime;
				long elapsedSeconds = elapsedTime / 1000;
				beatTime = (int) elapsedSeconds;
				
				/**
				 *  Debug scripts
				 */
				if (elapsedSeconds % 60 == 0 && debug == true) {
					// Heart Beat
					getLogger().info("[GM] Beep Boop Uptime: [" + elapsedSeconds + "] s");
				}
				
			}
		}, 0L, 20L);
	}
	
	/**
	 * Ran when plugin is disabled Remove permissions to clean
	 * up in case plugin is added again before server restart
	 */
	@Override
	public void onDisable() {
		getLogger().info("[GM] Plugin Disabled");
	}
	/**
	 * Gets the logger for this plugin
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets an instance of this plugin
	 * 
	 * @return The static instance of this plugin
	 */
	public static GamemanagerPlugin getInstance() {
		return plugin;
	}

	/**
	 * Gets the internal config
	 * 
	 * @return The internal config
	 */
	public static GamemagerConfig getInternalConfig() {
		return config;
	}
	
	/**
	 * Gets the internal worlds
	 * 
	 * @return The internal worlds
	 */
	public static Worlds getInternalWorlds() {
		return worlds;
	}
	
	/**
	 * Gets the internal holograms
	 * 
	 * @return The internal holograms
	 */
	public static Holograms getInternalHolograms() {
		return holo;
	}
}
