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
import java.util.ArrayList;
import java.util.Date;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import entities.Door;
import entities.Entity;
import entities.FollowerEnemy;
import entities.Friendly;
import entities.HorizontalOscillatingPlatform;
import entities.Key;
import entities.Player;
import entities.VerticalOscillatingPlatform;

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
		ArrayList<Entity> entities = level.getEntities();
		
		levelID = level.getID();

		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '.');

		savePath = "savedGames/" + timestamp + ".sav";

		data =	"LevelID " + level.getID() + "\n" 
				+ "levelDims " + level.getWidth() + " " + level.getHeight() + "\n"
				+ "Player " + player.getX() + " " + player.getY() + "\n"
				+ player.getInventory()
				+ "Checkpoint " + checkpoint.getCenterX() + " " + checkpoint.getMaxY() + "\n";
		
		for(Entity e : entities){
			if(e instanceof Door){
				data += "Door " + e.getX() + " " + e.getY() + " " + ((Door)e).isOpen() + "\n";
			}
			if(e instanceof Friendly){
				data += "Friendly " + e.getStartingX() + " " + e.getStartingY() + " " + e.getX() + " " + e.getY() + "\n";
			}
			if(e instanceof FollowerEnemy){
				data += "FollowerEnemy " + e.getStartingX() + " " + e.getStartingY() + " " + e.getX() + " " + e.getY() + "\n";
			}
			if(e instanceof HorizontalOscillatingPlatform){
				data += "HOP " + e.getX() + " " + e.getY() + "\n";
			}
			if(e instanceof VerticalOscillatingPlatform){
				data += "VOP " + e.getX() + " " + e.getY() + "\n";
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
					for(Entity e : level.getEntities()){
						if(e instanceof Player){
							player = (Player)e;
						}
					}
				}
				else if(words[0].equals("Player")){
					player.move(Float.parseFloat(words[1]), Float.parseFloat(words[2]));
				}
				else if(words[0].equals("Key")){
					Circle box = new Circle(Float.parseFloat(words[1]), Float.parseFloat(words[2]), 15);
					for(Entity e : level.getEntities()){
						if(e instanceof Key){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								e.remove();
							}
							if(Float.parseFloat(words[1]) < 0 && Float.parseFloat(words[2]) < 0){
								e.remove();
							}
							
						}
					}
					if(Float.parseFloat(words[1]) > 0 && Float.parseFloat(words[2]) > 0){
						Key key = new Key(box, new Vector2f(0, 0));
						player.addItem(key);
					}
				}
				else if(words[0].equals("Checkpoint")){
					player.move(Float.parseFloat(words[1]) - player.getWidth()/2, Float.parseFloat(words[2]) - player.getHeight());
					for(Checkpoint c : level.getCheckpoints()){
						if(c.getCenterX() == Float.parseFloat(words[1]) && c.getMaxY() == Float.parseFloat(words[2])){
							c.deactivate();
						}
					}
				}
				else if(words[0].equals("Door")){
					for(Entity e : level.getEntities()){
						if(e instanceof Door){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								if(words[3].equals("true")){
									((Door)e).open();
								}
							}
						}
					}
				}
				else if(words[0].equals("Friendly")){
					for(Entity e : level.getEntities()){
						if(e instanceof Friendly){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								e.move(Float.parseFloat(words[3]), Float.parseFloat(words[4]));
							}
						}
					}
				}
				else if(words[0].equals("FollowerEnemy")){
					for(Entity e : level.getEntities()){
						if(e instanceof FollowerEnemy){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								e.move(Float.parseFloat(words[3]), Float.parseFloat(words[4]));
							}
						}
					}
				}
				else if(words[0].equals("HOP")){
					for(Entity e : level.getEntities()){
						if(e instanceof HorizontalOscillatingPlatform){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								e.move(Float.parseFloat(words[3]), Float.parseFloat(words[4]));
							}
						}
					}
				}
				else if(words[0].equals("VOP")){
					for(Entity e : level.getEntities()){
						if(e instanceof VerticalOscillatingPlatform){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								e.move(Float.parseFloat(words[3]), Float.parseFloat(words[4]));
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
