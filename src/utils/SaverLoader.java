package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Date;

import levels.Level0;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import entities.Key;
import entities.Player;

/**
 * This is a utility class used to save and load the game states
 */
public class SaverLoader {

	static String savePath, data;
	static int levelWidth, levelHeight;

	/**
	 * Save the game state
	 * 
	 * @return - success
	 */
	public static boolean saveGame(Level level, Player player, Checkpoint checkpoint, int levelID){
		System.out.println("Saving");
		Date date = new Date();

		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '.');

		savePath = "savedGames/" + timestamp + ".sav";

		data = "levelDims " + level.getWidth() + " " + level.getHeight() + "\n"
				+ "Player " + player.getX() + ", " + player.getY() + "\n"
				+ player.getInventory()
				+ "Checkpoint " + checkpoint.getCenterX() + ", " + checkpoint.getCenterY() + "\n"
				+ "LevelID " + levelID;

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
	public static boolean loadGame(String path, StateBasedGame sbg){
		System.out.println("Loading");
		String line;
		String[] words;
		Player player = new Player(new Rectangle(0, 0, 40, 60), new Vector2f(0, 0));
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				words = line.split(" ");
				if(words[0] == "Player"){
					player.move(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
				}
				if(words[0] == "Key"){
					Circle box = new Circle(Integer.parseInt(words[1]), Integer.parseInt(words[2]), 15);
					Key key = new Key(box, new Vector2f(0, 0));
					player.addItem(key);
				}
				if(words[0] == "Checkpoint"){
					player.move(Integer.parseInt(words[1]) - player.getWidth()/2, Integer.parseInt(words[2]) - player.getHeight()/2);
				}
				if(words[0] == "LevelID"){
					if(Integer.parseInt(words[1]) == 5){
						//Level0 level0 = new Level0(player, levelWidth, levelHeight);
					}
					sbg.enterState(Integer.parseInt(words[1]));
				}
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file: " + path);
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("Error reading file: " + path);
			e.printStackTrace();
		}
		return true;
	}
}
