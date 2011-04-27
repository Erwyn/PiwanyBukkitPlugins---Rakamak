package com.piwany.erwyn.rakamak;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;


/**
 * Rakamak authentification plugin
 * 
 * 
 * @author Erwyn
 */
public class Rakamak extends JavaPlugin{

	static String directory = "Rakamak/";
	static File accounts = new File(directory + "users.rak");
	private final RakamakPlayerListener playerListener = new RakamakPlayerListener(this);
	private final RakamakBlockListener blockListener = new RakamakBlockListener(this);
	private final HashMap<Player,org.bukkit.Location> unlogged = new HashMap<Player,org.bukkit.Location>();
	
	/**
	 * Actions effectu�es lors de la coupure du plugin 
	 */
	public void onDisable() {
		System.out.println("Rakamak plugin disabled.... Shame on you!");
		
	}

	/**
	 * Actions effectu�es au lancement du plugin
	 */
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		System.out.println("Rakamak activation... Security is yours");
		
		/**
		 * Cr�ation du r�pertoire de configuration s'il n'existe pas d�j�
		 */
		new File(directory).mkdir();
		
		/**
		 * V�rification de l'existence du fichier de configuration, le cas inverse, on le cr��
		 */
		if(!accounts.exists()){
			try{
				accounts.createNewFile();
			}
			catch (IOException e){
				System.out.println("Probl�me � la cr�ation du ficher...");
				e.printStackTrace();
				System.out.println("Fin de la stackTrace");
			}
		}
		
		
		/**
		 * Message de fin d'activation du Plugin
		 */
		PluginDescriptionFile pdf = this.getDescription();
		System.out.println(pdf.getName()+" version "+pdf.getVersion()+" is enabled."+" Plugin provided by "+pdf.getAuthors());
		
		
		/**
		 * Les �vennements que l'on surveille pour la tenue des comptes
		 */
		pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
//		pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal,this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Event.Priority.Normal,this);
		pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, this.playerListener, Event.Priority.Normal,this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, this.playerListener, Event.Priority.Normal,this);
	}
	
	
	/**
	 * V�rifier qu'un joueur est logg�
	 * @param player
	 * @return true if player not in the unlogged list
	 */
	public boolean notlogged(Player player){
		return this.unlogged.containsKey(player);
	}

	
	/**
	 * Permet de permutter un joueur de logg� � non logg�
	 * @param player
	 */
	public void togglePlayer(Player player){
		if(notlogged(player)){
			this.unlogged.remove(player);
		}
		else{
			this.unlogged.put(player,player.getLocation());
		}
	}
	
	public org.bukkit.Location locate(Player player){
		return this.unlogged.get(player);
	}
}
