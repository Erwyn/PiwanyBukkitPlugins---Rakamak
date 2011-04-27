package com.piwany.erwyn.rakamak;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


/**
 * Some basic stuff to manage players accounts
 * 
 * @author Erwyn
 *
 */

public class AccountsMgmt {

	/**
	 * A method to verify that a player has an account
	 * 
	 * @param player
	 * @param file
	 * @return true if the player already has an account on the server
	 */
	public static boolean hasAccount(Player player, File file) {
		Properties properties = new Properties();
		String playerName = player.getName();

		try {
			FileInputStream in = new FileInputStream(file);
			properties.load(in);

			if (properties.containsKey(playerName)) {
				return true;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;

	}
	
	public static void addAccount(Player player, String pass, File file){
		Properties properties = new Properties();
		String playerName = player.getName();
		
		try{
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			properties.setProperty(playerName, pass);
			properties.store(new FileOutputStream(file), null);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		player.sendMessage(ChatColor.GREEN+"Votre compte a été créé, /rakapass <votrepass> pour vous identifier!");
	}
	
	public static boolean verify(Player player, String pass, File file){
		Properties properties = new Properties();
		String playerName = player.getName();
		
		try{
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			String userpass = properties.getProperty(playerName);
			return userpass.equals(pass);
		}
		catch(IOException e){
			System.out.println(e.getMessage());			
		}
		return false;
	}

}
