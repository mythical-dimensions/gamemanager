package tk.mythicaldimensions.gamemanager.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import tk.mythicaldimensions.gamemanager.GamemanagerPlugin;

/**
 * A utility class with various static methods to provide a clean easy API We
 * are declaring it as final because there are only utility methods and we don't
 * want anyone accidentally extending this class
 * 
 * @author [Empty]
 */
public class Holograms {
	
	/*
	 * Hologram map
	 */
	private HashMap<String, Object[]> HologramsMap = new HashMap<String, Object[]>();
	
	public Holograms() {
		setDefaults();
		loadHolograms();
	}
	
	/*
	 * Get Permissions
	 */
	public void loadHolograms() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (file.length() != 0) {
				Object[] holoKeys = config.getConfigurationSection("Holograms").getKeys(false).toArray();
				String[] holos = Arrays.asList(holoKeys).toArray(new String[holoKeys.length]);
				
				for (String it : holos) {
					Object[] obj = new Object[] {
						config.getString("Holograms." + it + ".Name"), //Name 0
						config.getLocation("Holograms." + it + ".Location"), //Location 1
						
					}; 
					
					HologramsMap.put(it, obj);
				}
			}
		} catch (IOException e) {
			return;
		}
	}
	
	public void setDefaults() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Save Permissions
	 */
	public void saveHolograms() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (file.length() != 0) {
				Object[] holoKeys = config.getConfigurationSection("Holograms").getKeys(false).toArray();
				String[] holos = Arrays.asList(holoKeys).toArray(new String[holoKeys.length]);
				
				for (String it : holos) {
					config.set("Holograms." + it + ".Name", (String) getHologramsMap().get(it)[0]);
					config.set("Holograms." + it + ".Location", (Location) getHologramsMap().get(it)[1]);
				}
				config.save(file);
			}
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Create Hologram
	 */
	public void createHologram(String holoName, Location holoLocation) {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			World world = holoLocation.getWorld();
			
			config.set("Holograms." + holoName + ".Name", holoName);
			config.set("Holograms." + holoName + ".Location", holoLocation);
			
			ArmorStand hologram = (ArmorStand) world.spawnEntity(holoLocation, EntityType.ARMOR_STAND);
			hologram.setVisible(false);
			hologram.setGravity(false);
			hologram.setCustomName(holoName);
			hologram.setCustomNameVisible(true);
			
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Delete Hologram
	 */
	public void deleteHologram(String holoName) {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (file.length() != 0) {
				Location loc = config.getLocation("Holograms." + holoName + ".Location");
				World world = loc.getWorld();
				
				for (Entity it : world.getNearbyEntities(loc, loc.getX(), loc.getY(), loc.getZ())) {
					if (it instanceof ArmorStand) {
						it.remove();
					}
				}
				
				config.set("Holograms." + holoName + ".Name", null);
				config.set("Worlds." + holoName + ".Location", null);
				config.set("Worlds." + holoName, null);
				
				config.save(file);
			}
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Get Hologram
	 */
	public String[] getHolograms() {
		try {
			File file = new File(GamemanagerPlugin.getInstance().getDataFolder()+File.separator+"holograms.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] holoKeys = config.getConfigurationSection("Holograms").getKeys(false).toArray();
			String[] holograms = Arrays.asList(holoKeys).toArray(new String[holoKeys.length]);
			List<String> holo =  new ArrayList<String>();
			for (String it : holograms) {
				holo.add(config.getString("Holograms." + it + ".Name"));
			}
			return holo.toArray(new String[holo.size()]);
		} catch (IOException e) {
			return null;
		}
	}
	
	/*
	 * Reload Worlds
	 */
	public void reloadHolograms() {
		loadHolograms();
	}
	
	/*
	 * Get permissionsMap
	 */
	public HashMap<String, Object[]> getHologramsMap() {
		return HologramsMap;
	}
}
