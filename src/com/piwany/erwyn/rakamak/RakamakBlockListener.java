package com.piwany.erwyn.rakamak;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * The listener for player's interactions with blocks
 * 
 * @author Erwyn
 *
 */

public class RakamakBlockListener extends BlockListener{
	static Rakamak plugin;
	
	/**
	 * The small constructor
	 * @param instance
	 */
	public RakamakBlockListener(Rakamak instance){
		plugin = instance;
	}
	
	/**
	 * When a player places a block
	 * 
	 * @param event
	 */
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(plugin.notlogged(player)){
			player.sendMessage(ChatColor.GREEN+"Vous devez d'abord vous authentifier avec la commande /rakapass <votremotdepasse>  avant de pouvoir faire quoique ce soit.");
			event.setCancelled(true);
		}	
		
	}

	/**
	 * When a player breaks a block
	 * @param event
	 */
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(plugin.notlogged(player)){
			player.sendMessage(ChatColor.GREEN+"Vous devez d'abord vous authentifier avec la commande /rakapass <votremotdepasse>  avant de pouvoir faire quoique ce soit.");
			event.setCancelled(true);
		}	
	}

}
