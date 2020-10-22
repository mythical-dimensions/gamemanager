package tk.mythicaldimensions.gamemanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.mythicaldimensions.gamemanager.GamemanagerPlugin;
import tk.mythicaldimensions.gamemanager.utility.GamemanagerUtil;

/**
 * A command executor class that is used whenever the Essentials command is run
 * It's best practice to have each command in its own class implementing
 * CommandExecutor It makes our code much more clean and ensures that our
 * onCommand() will only be executed for the command this executor is registered
 * to
 * 
 * @author [Empty]
 */
public class GamemagerCommands implements CommandExecutor {

	/**
	 * Executed when Essentials command is run CommandSender will generally be a Player,
	 * Command Block, or Console but we should always check before doing an action
	 * that not all of them support Command will always be the command that this
	 * executor is registered to: In this case essentials label is in the case that an
	 * alias is used instead of Essentials We don't really need to worry about this but
	 * be aware that it might not be the same as the name of the command args is an
	 * array of all other arguments entered, we always want to check the length of
	 * args in case there aren't as many as you would expect!
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean commandReturn = false;
		
		if (args.length == 4) {
			// Check for import
			if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("import")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// Import world
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&FLoading &6World..."));
					GamemanagerPlugin.getInternalWorlds().importNewWorld(args[2], args[3]);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&FWorld &6Loaded"));
					
					// Return true
					commandReturn = true;
					
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
			}
			
		// Check for 3 args
		} else if (args.length == 3) {
			// Check for create
			if (args[0].equalsIgnoreCase("holo") && args[1].equalsIgnoreCase("create")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// Create hologram
					GamemanagerPlugin.getInternalHolograms().createHologram(args[2], Bukkit.getPlayer(sender.getName()).getLocation());
								
					// Return true
					commandReturn = true;
								
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
			// Check for delete
			} else if (args[0].equalsIgnoreCase("holo") && args[1].equalsIgnoreCase("delete")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// Create hologram
					GamemanagerPlugin.getInternalHolograms().deleteHologram(args[2]);
									
					// Return true
					commandReturn = true;
									
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
			// Check for teleport
			}else if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("teleport")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					Player player = Bukkit.getPlayer(sender.getName());
					Location loc = Bukkit.getWorld(args[2]).getSpawnLocation();
								
					// Check if world exist in context
					if (Bukkit.getWorld(args[2]) != null) {
						player.teleport(loc);	
					}
								
					// Return true
					commandReturn = true;
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
				
			// Check for delete
			} else if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("delete")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// Set High
					GamemanagerPlugin.delete = true;
					GamemanagerPlugin.world = args[2];
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&Ftype in &6/gamemanager &Fworld &8accept"));
					
					// Return true
					commandReturn = true;
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
			}
		// Check for 2 args
		} else if (args.length == 2) {
			// Check for list	
			if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("list")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					for (String it : GamemanagerPlugin.getInternalWorlds().getWorlds()) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + it));
					}
					// Return true
					commandReturn = true;
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));
	
					// Return false
					commandReturn = false;
				}
			// Check for accept
			} else if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("accept")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					//Check if delete is high
					if (GamemanagerPlugin.delete) {
						// Set low
						GamemanagerPlugin.delete = false;
						
						// Delete world
						GamemanagerPlugin.getInternalWorlds().deleteWorld(GamemanagerPlugin.world);
						GamemanagerPlugin.world = "";
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&FWorld &6Deleted"));
						
						// Return true
						commandReturn = true;
					}
					
					// Return true
					commandReturn = false;
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
				
			// Check for cancel
			} else if (args[0].equalsIgnoreCase("world") && args[1].equalsIgnoreCase("cancel")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					//Check if delete is high
					if (GamemanagerPlugin.delete) {
						// Set low
						GamemanagerPlugin.delete = false;
							
						// Send message
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&FWorld &6Delete &Fwas Canceled"));
							
						// Return true
						commandReturn = true;
					}
						
					// Return true
					commandReturn = false;
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
			}
		// Check for 1 args
		} else if (args.length == 1) {
			// Check for reload
			if (args[0].equalsIgnoreCase("reload")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// Reload internal config
					GamemanagerPlugin.getInternalConfig().reloadConfig();
					GamemanagerPlugin.getInternalWorlds().reloadWorlds();
							
					// Send reload message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getConfigReloadedMessage()));

					// Return true
					commandReturn = true;

					// Sender doesn't have perm
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							GamemanagerPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
						
			// Check for help
			} else if (args[0].equalsIgnoreCase("help")) {
				// Send help message
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&F/" 
						+ "\n&F/"
						+ "\n&F/warden &6reload" + "\n&F/warden &6debug" + "\n&F/warden &6help"));
				
				// Return true
				commandReturn = true;
					
			// Check for debug
			} else if (args[0].equalsIgnoreCase("debug")) {
				// Check for perms
				if (sender.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
					// check the state of debug
					if (GamemanagerPlugin.debug == true) {
						// Send message to sender and set false
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Disabled"));
						GamemanagerPlugin.debug = false;
					} else {
						// Send message to sender and set true
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Enabled"));
						GamemanagerPlugin.debug = true;
					}
							
				// Return true
				commandReturn = true;
				}
		
				return commandReturn;
			} else {
				// Send invalid
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						GamemanagerPlugin.getInternalConfig().getCommandInvalidMessage()));

				// Return false
				commandReturn = false;
			}
		
		} else {
			// Return invalid message
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					GamemanagerPlugin.getInternalConfig().getCommandInvalidMessage()));

			// Return false
			commandReturn = false;
		}
		return commandReturn;
	}
}