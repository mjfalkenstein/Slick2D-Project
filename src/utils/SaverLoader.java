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

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import entities.Door;
import entities.Entity;
import entities.Key;
import entities.Player;

/**
 * This is a utility class used to save and load the game states
 */
public class SaverLoader {

	static String savePath, data;
	static int levelWidth, levelHeight;
	int levelID;

	/**
	 * Save the game state
	 * 
	 * @return - success
	 */
	public static boolean saveGame(Level level, Player player, Checkpoint checkpoint, int levelID){
		Date date = new Date();
		
		levelID = level.getID();

		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '.');

		savePath = "savedGames/" + timestamp + ".sav";

		data =	"LevelID " + level.getID() + "\n" 
				+ "levelDims " + level.getWidth() + " " + level.getHeight() + "\n"
				+ "Player " + player.getX() + " " + player.getY() + "\n"
				+ player.getInventory()
				+ "Checkpoint " + checkpoint.getCenterX() + " " + checkpoint.getCenterY() + "\n";
		
		for(Entity e : level.getEntities()){
			if( e instanceof Door){
				data += "Door " + e.getX() + " " + e.getY() + " " + ((Door)e).isOpen() + "\n";
			}
		}

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
	 * @param sbg - the statebasedgame as it exists before loading
	 * @return - success
	 */
	public static boolean loadGame(String path, StateBasedGame sbg){
		System.out.println("Attempting to load file: " + path);
		String line;
		String[] words;
		Player player = null;
		
		int levelID = -1;
		
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			Level level = null;

			while((line = bufferedReader.readLine()) != null) {
				words = line.split(" ");
				if(words[0].equals("LevelID")){
					level = (Level) sbg.getState(Integer.parseInt(words[1]));
					levelID = Integer.parseInt(words[1]);
					System.out.println("Loading level with ID: " + levelID);
					for(Entity e : level.getEntities()){
						if(e instanceof Player){
							System.out.println("Creating player");
							player = (Player)e;
						}
					}
				}
				else if(words[0].equals("Player")){
					System.out.printf("Loading player position to: %s, %s\n", words[1], words[2]);
					player.move(Float.parseFloat(words[1]), Float.parseFloat(words[2]));
				}
				else if(words[0].equals("Key")){
					System.out.println("Player: " + player);
					System.out.println("Inventory:" + player.getInventory());
					System.out.printf("Loading key at: %s, %s\n", words[1], words[2]);
					Circle box = new Circle(Float.parseFloat(words[1]), Float.parseFloat(words[2]), 15);
					Key key = new Key(box, new Vector2f(0, 0));
					player.addItem(key);
				}
				else if(words[0].equals("Checkpoint")){
					System.out.printf("Loading checkpoint at: %s, %s\n", words[1], words[2]);
					player.move(Float.parseFloat(words[1]) - player.getWidth()/2, Float.parseFloat(words[2]) - player.getHeight()/2);
				}
				else if(words[0].equals("Door")){
					for(Entity e : level.getEntities()){
						if(e instanceof Door){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								((Door)e).open();
							}
						}
					}
				}
			}
			bufferedReader.close();
			if(levelID == -1){
				throw new IOException("Invalid level ID");
			}
			sbg.enterState(levelID, new FadeOutTransition(), new FadeInTransition());
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
