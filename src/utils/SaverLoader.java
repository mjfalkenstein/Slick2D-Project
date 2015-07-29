package utils;

import java.sql.Timestamp;
import java.util.Date;

import entities.Player;

/**
 * This is a utility class used to save and load the game states
 */
public class SaverLoader {
	
	static String savePath;

	/**
	 * Save the game state
	 * 
	 * @return - success
	 */
	public static boolean saveGame(Player player, Checkpoint checkpoint){
		Date date = new Date();
		
		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '.');
		
		savePath = "savedGame" + timestamp + ".sav";
		
		//This is where the saving will be done
		return true;
	}
	
	/**
	 * Load the game state from the file at the given path
	 * 
	 * @param path - the file path to the save game
	 * @return - success
	 */
	public static boolean loadGame(String path){
		//This is where the saving will be done
		return true;
	}
}
