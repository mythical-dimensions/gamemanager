package tk.mythicaldimensions.gamemanager.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tk.mythicaldimensions.gamemanager.GamemanagerPlugin;

/**
 * A utility class with various static methods to provide a clean easy API We
 * are declaring it as final because there are only utility methods and we don't
 * want anyone accidentally extending this class
 * 
 * @author [Empty]
 */
public class Worlds {
	
	/*
	 * Worlds map
	 */
	private HashMap<String, Object[]> worldsMap = new HashMap<String, Object[]>();
	
	public Worlds() {
		setDefaults();
		loadWorlds();
	}
	
	/*
	 * Get Permissions
	 */
	public void loadWorlds() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] worldKeys = config.getConfigurationSection("Worlds").getKeys(false).toArray();
			String[] worlds = Arrays.asList(worldKeys).toArray(new String[worldKeys.length]);
			
			for (String it : worlds) {
				Object[] obj = new Object[] {
					config.getString("Worlds." + it + ".Name"), //Name 0
					config.getString("Worlds." + it + ".Type"), //Type 1
					config.getBoolean("Worlds." + it + ".Default"), //Default 2
				}; 
				
				worldsMap.put(it, obj);
			}
			
			
		} catch (IOException e) {
			return;
		}
	}
	
	public void setDefaults() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
			for (World it : Bukkit.getServer().getWorlds()) {
				if (config.getString("Worlds." + it.getName() + ".Name") != null) {
					config.addDefault("Worlds." + it.getName() + ".Name", it.getName());
					config.addDefault("Worlds." + it.getName() + ".Type", it.getWorldType().toString());
					config.addDefault("Worlds." + it.getName() + ".Default", false);
				}
			}
	
			config.options().copyDefaults(true);
	
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Save Permissions
	 */
	public void saveWorlds() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] worldKeys = config.getConfigurationSection("Worlds").getKeys(false).toArray();
			String[] worlds = Arrays.asList(worldKeys).toArray(new String[worldKeys.length]);
			
			for (String it : worlds) {
				config.set("Worlds." + it + ".Name", (String) getWorldsMap().get(it)[0]);
				config.set("Worlds." + it + ".Type", (String) getWorldsMap().get(it)[1]);
				config.set("Worlds." + it + ".Default", (boolean) getWorldsMap().get(it)[2]);
			}
			config.save(file);
			
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Import new world
	 */
	public void importNewWorld(String worldName, String worldType) {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.set("Worlds." + worldName + ".Name", worldName);
			config.set("Worlds." + worldName + ".Type", worldType);
			config.set("Worlds." + worldName + ".Default", false);
			
			if (Bukkit.getWorld(worldName) == null) {
				Bukkit.createWorld(new WorldCreator(worldName));
			}
			
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Delete world
	 */
	public void deleteWorld(String worldName) {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.set("Worlds." + worldName + ".Name", null);
			config.set("Worlds." + worldName + ".Type", null);
			config.set("Worlds." + worldName + ".Default", null);
			config.set("Worlds." + worldName, null);
			
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Get default world
	 */
	public String getDefaultWorld() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] worldKeys = config.getConfigurationSection("Worlds").getKeys(false).toArray();
			String[] worlds = Arrays.asList(worldKeys).toArray(new String[worldKeys.length]);
			String world = "";
			
			for (String it : worlds) {
				if (config.getBoolean("World." + it + ".Default")) {
					world = it;
					break;
				}
			}
			return world;
		} catch (IOException e) {
			return null;
		}
	}
	
	/*
	 * Get Worlds
	 */
	public List<String> getWorlds() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"worlds.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] worldKeys = config.getConfigurationSection("Worlds").getKeys(false).toArray();
			String[] worlds = Arrays.asList(worldKeys).toArray(new String[worldKeys.length]);
			List<String> world =  new ArrayList<String>();
			for (String it : worlds) {
				world.add(config.getString("Worlds." + it + ".Name"));
			}
			return world;
		} catch (IOException e) {
			return null;
		}
	}
	
	/*
	 * Reload Worlds
	 */
	public void reloadWorlds() {
		loadWorlds();
	}
	
	/*
	 * Get permissionsMap
	 */
	public HashMap<String, Object[]> getWorldsMap() {
		return worldsMap;
	}
}
