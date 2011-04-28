package com.piwany.erwyn.rakamak;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The Listener for players
 * 
 * @author Erwyn
 * 
 */
public class RakamakPlayerListener extends PlayerListener {
	static Rakamak plugin;

	/**
	 * A basic constructor
	 * 
	 * @param instance
	 */
	public RakamakPlayerListener(Rakamak instance) {
		plugin = instance;
	}

	/**
	 * Actions when a player joins the world
	 * 
	 * @param event
	 */
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setNoDamageTicks(1000000000);
		plugin.togglePlayer(player);
		if (AccountsMgmt.hasAccount(player, Rakamak.accounts)) {
			player.sendMessage(ChatColor.GREEN
					+ "Hello "
					+ player.getName()
					+ ", remember to authenticate with /rakapass <yourpassword>");
		} else {
			player.sendMessage(ChatColor.GREEN
					+ "Welcome "
					+ player.getName()
					+ ", before doing anything, you have to create a password for your character by typing /rakaset <passwordyouwant>");
		}
	}

//	/**
//	 * Verifying if a player has the right to move
//	 */
//
//	public void onPlayerMove(PlayerMoveEvent event) {
//		Player player = event.getPlayer();
//		if (plugin.notlogged(player)) {
//			player.teleport(plugin.locate(player));
//		}
//	}

	/**
	 * Verifying if the player has the right to interact with the world
	 */
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (plugin.notlogged(player)) {
			player.sendMessage(ChatColor.GREEN+"You have to identify yourself with /rakapass <yourpassword> before being able to do anything");
			event.setCancelled(true);
		}
	}

	/**
	 * Verifying if the player has the right to drop an item from inventory
	 */
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (plugin.notlogged(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * Verifying if the player has the right to pick an item in its inventory
	 */
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (plugin.notlogged(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * Actions to do when a player leaves the world
	 * 
	 * @param event
	 */

	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (plugin.notlogged(player)) {
			player.teleport(plugin.locate(player));
			plugin.togglePlayer(player);
		}
	}

	/**
	 * When a player type in a command
	 * 
	 * @param event
	 */
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String cmd[] = event.getMessage().split(" ");
		Player player = event.getPlayer();
		if (cmd.length != 2
				&& ((cmd[0].equalsIgnoreCase("/rakaset") || cmd[0]
						.equalsIgnoreCase("/rs")) || (cmd[0]
						.equalsIgnoreCase("/rakapass") || cmd[0]
						.equalsIgnoreCase("/rp")))) {
			player.sendMessage("Rakamak: invalid number of arguments");
		} else {
			if ((cmd[0].equalsIgnoreCase("/rakaset"))
					|| (cmd[0].equalsIgnoreCase("/rs"))) {
				if (AccountsMgmt.hasAccount(player, Rakamak.accounts)) {
					player.sendMessage(ChatColor.GREEN
							+ "You already have a password");
				} else {
					AccountsMgmt.addAccount(player, cmd[1], Rakamak.accounts);
				}
				event.setCancelled(true);
			} else if ((cmd[0].equalsIgnoreCase("/rakapass"))
					|| (cmd[0].equalsIgnoreCase("/rp"))) {
				if (!AccountsMgmt.hasAccount(player, Rakamak.accounts)) {
					player.sendMessage(ChatColor.GREEN
							+"You do not have any account yet, please type /rakaset <passwordyouwant> to create one!");
				} else {
					if (AccountsMgmt.verify(player, cmd[1], Rakamak.accounts)) {
						if (plugin.notlogged(player)) {
							plugin.togglePlayer(player);
							player.setNoDamageTicks(0);
							player.sendMessage(ChatColor.GREEN
									+ "You're now authenticated, have fun");
						} else {
							player.sendMessage(ChatColor.GREEN
									+ "You're already authenticated!!!");
						}
					} else {
						player.sendMessage(ChatColor.GREEN
								+ "Wrong password!");
					}
				}
				event.setCancelled(true);
			}

		}
	}

}
