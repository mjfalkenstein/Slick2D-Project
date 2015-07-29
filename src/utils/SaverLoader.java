package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Date;

import entities.Player;

/**
 * This is a utility class used to save and load the game states
 */
public class SaverLoader {
	
	static String savePath, data;

	/**
	 * Save the game state
	 * 
	 * @return - success
	 */
	public static boolean saveGame(Player player, Checkpoint checkpoint, int levelID){
		Date date = new Date();
		
		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '.');
		
		savePath = "savedGames/" + timestamp + ".sav";
		
		data = player.getX() + ", " + player.getY() + "\n"
			 + player.getInventory()
			 + checkpoint.getCenterX() + ", " + checkpoint.getCenterY() + "\n"
			 + levelID;
		
		try {
			File file = new File(savePath);
			FileOutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			bw.write(data);
			bw.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
