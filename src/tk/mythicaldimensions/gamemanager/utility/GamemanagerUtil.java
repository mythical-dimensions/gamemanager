package tk.mythicaldimensions.gamemanager.utility;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import tk.mythicaldimensions.gamemanager.GamemanagerPlugin;

/**
 * A utility class with various static methods to provide a clean easy API We
 * are declaring it as final because there are only utility methods and we don't
 * want anyone accidentally extending this class
 * 
 * @author [Empty]
 */
public class GamemanagerUtil {
	// Permission string required to use the reload command
	public static final String GAMEMANAGER_RELOAD_PERM = "gamemanager.reload";
	public static final String GAMEMANAGER_SELECT_PERM = "gamemanager.select";

	// List of all registered permissions
	public static final ArrayList<Permission> perms = new ArrayList<>();

	/**
	 * Prevent anyone from initializing this class as it is solely to be used for
	 * static utility
	 */
	private GamemanagerUtil() {
	}
	
	/**
	 * Load worlds in worlds files
	 */
	public static void loadWorlds() {
		if (GamemanagerPlugin.getInternalWorlds().getWorlds() != null) {
			for (String it : GamemanagerPlugin.getInternalWorlds().getWorlds()) {
				if (Bukkit.getWorld(it) == null) {
					Bukkit.createWorld(new WorldCreator(it));
				}
			}
		}
	}
	
	/*
	 * Get current selection
	 */
	public static Location[] getCurrentSelection(String PlayerName) {
		return GamemanagerPlugin.selection.get(PlayerName);
	}
		
	/**
	 * Register all Scpsl permissions
	 */
	public static void registerPermissions() {
		// Create the permissions and store them in a list
		// The list is mainly used internally but a getter could be used to grant other
		// developers access to the list
		// For a permission we only need to have a string representing the key, however
		// it's best to include the description and who has the permission by default
		perms.add(new Permission(GAMEMANAGER_RELOAD_PERM, "Allows players to reload the config", PermissionDefault.OP));
		perms.add(new Permission(GAMEMANAGER_SELECT_PERM, "Allows players to select", PermissionDefault.OP));

		// Loop through the list and add all the permissions we created
		for (Permission perm : perms) {
			Bukkit.getPluginManager().addPermission(perm);

			// Log a message that we added the permission
			GamemanagerPlugin.getInstance().getLogger().fine("Registered Permission: " + perm.getName());
		}
	}

	/**
	 * Unregister all Scpsl permissions
	 */
	public static void unregisterPermissions() {
		// Remove all permissions that we created
		// Mainly used when disabling the plugin to prevent issues if the permissions
		// are changed and the plugin is enabled again (possibly an update?)
		// While using the /reload command is bad practice, many server owners will do
		// so anyway and that can cause issues if we don't clean up properly
		for (Permission perm : perms) {
			Bukkit.getPluginManager().removePermission(perm);

			// Log a message that we removed the permission
			GamemanagerPlugin.getInstance().getLogger().fine("Unregistered Permission: " + perm.getName());
		}

		// Clear the list of permissions incase this method was called but the plugin
		// wasn't disabled
		// If we don't do this then calling registerPermissions() would result in trying
		// to register each permission twice
		perms.clear();
	}
}
