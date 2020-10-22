package tk.mythicaldimensions.gamemanager.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import tk.mythicaldimensions.essentials.events.RightClickNPC;
import tk.mythicaldimensions.gamemanager.GamemanagerPlugin;
import tk.mythicaldimensions.gamemanager.utility.GamemanagerUtil;

/**
 * A listener class containing all events for our plugin Currently there is only
 * one event, however we could easily add more
 * 
 * @author [Empty]
 */
public class GamemangerEvents implements Listener {
	
	// Player Join
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		// Send welcoming message
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', GamemanagerPlugin.getInternalConfig().getWelcomeMessage()));
		
		if (Bukkit.getWorld("hub") != null) {
			World world = Bukkit.getWorld("hub");
			Location loc = world.getSpawnLocation();
			
			// Teleport player to main world
			player.teleport(loc);
			
			// Add to in hub world
			GamemanagerPlugin.inHub.put(player.getDisplayName(), true);
		}
	}
	
	/*
	 * Right click npc
	 */
	@EventHandler
	public void onClick(RightClickNPC event) {
		Player player = event.getPlayer();
		
		if (event.getNPC().getName().equalsIgnoreCase("SCPSL")) {
			player.chat("/scpsl addtoscpsl " + player.getDisplayName());
		}
	}
	
	/*
	 * Server ping event
	 */
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		// Set MOTD
		event.setMotd(ChatColor.translateAlternateColorCodes('&', "&f&lWelcome to &6&lMythical Dimensions"));
	}
	
	/*
	 * World change event
	 */
	@EventHandler
	public void onWorldChangeEvent(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		
		if (event.getFrom() == Bukkit.getWorld("hub")) {GamemanagerPlugin.inHub.put(player.getDisplayName(), false);}
		if (player.getWorld() == Bukkit.getWorld("hub")) {GamemanagerPlugin.inHub.put(player.getDisplayName(), false);}
		
	}
	
	// Block break event
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
			
		if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
			event.setCancelled(true);
		}
	}
		
	// Block place event
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
			
		if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
			event.setCancelled(true);
		}
	}
		
	// Item pickup event
	@EventHandler 
	public void onEnitityPickupItemEvent(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
				
			if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
				event.setCancelled(true);
			}
		}
	}
		
	// Item drop event
	@EventHandler 
	public void onEnitityDropItemEvent(EntityDropItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
				
			if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
				event.setCancelled(true);
			}
		}
	}
		
	// Player interact event
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
			
		if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
			
		}
	}
		
	// Inventory click event
	@EventHandler
	public void onPlayerInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getView().getPlayer();

		if (GamemanagerPlugin.inHub.get(player.getDisplayName()) && !player.hasPermission(GamemanagerUtil.GAMEMANAGER_RELOAD_PERM)) {
			event.setCancelled(true);
		}

	}
}
