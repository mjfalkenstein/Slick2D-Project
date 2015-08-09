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

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.Door;
import entities.Entity;
import entities.FollowerEnemy;
import entities.Friendly;
import entities.HorizontalOscillatingPlatform;
import entities.Key;
import entities.Player;
import entities.SlidingDoor;
import entities.VerticalOscillatingPlatform;

/**
 * This is a utility class used to save and load the game states
 */
public class SaverLoader {

	static String savePath, data;
	static int levelWidth, levelHeight;
	int levelID;
	GameContainer gc;

	/**
	 * Save the game state
	 * 
	 * @return - success
	 */
	public static boolean saveGame(GameContainer gc, Level level, Player player, Checkpoint checkpoint, int levelID){
		Date date = new Date();
		ArrayList<Entity> entities = level.getEntities();

		levelID = level.getID();

		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '_');
		
		timestamp = timestamp.substring(0, timestamp.indexOf('.'));

		savePath = "savedGames/" + timestamp + ".sav";
		
		savePath = savePath.replace(":", "_");

		data =	"GC " + gc.getWidth() + " " + gc.getHeight() + " " + gc.isFullscreen() + " " + gc.isShowingFPS() + "\n"
				+ "LevelID " + level.getID() + "\n" 
				+ "levelDims " + level.getWidth() + " " + level.getHeight() + "\n"
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
			if(e instanceof SlidingDoor){
				data+= "SlidingDoor " + e.getStartingX() + " " + e.getStartingY() + " " + e.getX() + " " + e.getY() + " " + ((SlidingDoor)e).isOpen() + "\n"; 
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
			System.err.println("Could not find file: " + savePath);
			e.printStackTrace();
			System.exit(0);
			return false;
		} catch (IOException e) {
			System.err.println("Error reading file: " + savePath);
			e.printStackTrace();
			System.exit(0);
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
	 * @throws SlickException 
	 */
	public static boolean loadGame(GameContainer gc, String path, StateBasedGame sbg){
		String line;
		String[] words;
		Player player = null;
		float playerX = 0;
		float playerY = 0;

		int levelID = -1;

		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			Level level = null;

			while((line = bufferedReader.readLine()) != null) {
				words = line.split(" ");

				if(words[0].equals("LevelID")){
					try{
						level = (Level) sbg.getState(Integer.parseInt(words[1]));
					}catch(Exception e){
						System.err.println("Invalid level ID");
					}
					levelID = Integer.parseInt(words[1]);
					for(Entity e : level.getEntities()){
						if(e instanceof Player){
							player = (Player)e;
						}
					}
				}
				else if(words[0].equals("Key")){
					for(Entity e : level.getEntities()){
						if(e instanceof Key){
							if(Float.parseFloat(words[3]) < 0 && Float.parseFloat(words[4]) < 0){
								if(e.getStartingX() == Float.parseFloat(words[1]) && e.getStartingY() == Float.parseFloat(words[2])){
									e.remove();
								}
							}
							else{
								if(e.getStartingX() == Float.parseFloat(words[1]) && e.getStartingY() == Float.parseFloat(words[2])){
									player.addItem((Item)e);
								}
							}
						}
					}
				}
				else if(words[0].equals("Checkpoint")){
					playerX = Float.parseFloat(words[1]) - player.getWidth()/2;
					playerY = Float.parseFloat(words[2]) - player.getHeight();
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
				else if(words[0].equals("SlidingDoor")){
					for(Entity e : level.getEntities()){
						if(e instanceof SlidingDoor){
							if(e.getX() == Float.parseFloat(words[1]) && e.getY() == Float.parseFloat(words[2])){
								if(words[5].equals("true")){
									((SlidingDoor)e).open();
								}
							}
						}
					}
				}
			}

			bufferedReader.close();
			if(levelID == -1){
				throw new IOException("Invalid level ID");
			}
			player.move(playerX, playerY);
			sbg.enterState(levelID);
			level.unpause();
			player.setVelocity(0, 0);
		}
		catch(FileNotFoundException e) {
			System.err.println("Unable to open file: " + path);
			e.printStackTrace();
		}
		catch(IOException e) {
			System.err.println("Error reading file: " + path);
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Save the settings of the game container
	 * 
	 * @param gc - the current game container
	 * @return - whether saving was successful or not
	 */
	public static boolean saveSettings(GameContainer gc){
		Date date = new Date();

		String timestamp = new Timestamp(date.getTime()).toString().replace(' ', '_');
		
		timestamp = timestamp.substring(0, timestamp.indexOf('.'));

		savePath = "config/config.cfg";

		data =	"GC " + gc.getWidth() + " " + gc.getHeight() + " " + gc.isFullscreen() + " " + gc.isShowingFPS() + "\n";
				
		try {
			File file = new File(savePath);
			if(!file.exists()){
				file.mkdir();
			}
			FileOutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			bw.write(data);
			bw.close();
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + savePath);
			return false;
		} catch (IOException e) {
			System.err.println("Error reading file: " + savePath);
			return false;
		}
		return true;
	}
	
	/**
	 * Load the settings for the game container from an old session
	 * 
	 * @param gc - the current game container
	 * @param path - the path of the file we're reading
	 * 
	 * @return - whether loading was successful or not
	 * @throws SlickException
	 */
	public static boolean loadSettings(GameContainer gc) throws SlickException{
		String line;
		String[] words;
		
		String path = "config/config.cfg";

		try {
			File file = new File("config");
			if(!file.exists()){
				file.mkdir();
			}
			file = new File(path);
			if(!file.exists()){
				file.createNewFile();
				saveSettings(gc);
			}
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				words = line.split(" ");
				
				if(words[0].equals("GC")){
					if(words[3].equals("true")){
						((AppGameContainer) gc).setDisplayMode(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
					}else{
						((AppGameContainer) gc).setDisplayMode(Integer.parseInt(words[1]), Integer.parseInt(words[2]), false);
					}
					if(words[4].equals("true")){
						((AppGameContainer) gc).setShowFPS(true);
					}else{
						((AppGameContainer) gc).setShowFPS(false);
					}
					
				}
			}
			
			bufferedReader.close();
		}		
		
		catch(FileNotFoundException e) {
			System.err.println("Unable to open file: " + path);
			e.printStackTrace();
		}
		catch(IOException e) {
			System.err.println("Error reading file: " + path);
			e.printStackTrace();
		}
		return true;
	}
}
