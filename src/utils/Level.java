package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import entities.Player;

/**
 * A class that handles all level essentials
 * All over 'Levels' must inherit from this in order to work properly
 */
public abstract class Level extends BasicGameState{
	
	protected GameContainer gc;
	protected StateBasedGame sbg;
	protected boolean paused = false;
	protected PauseMenu pauseMenu;
	protected Player player;
	protected Camera camera;
	protected int mouseX, mouseY;
	protected int levelWidth, levelHeight;
	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonXGap, buttonYGap;
	protected Notification warning;
	protected SimpleButton b1, b2;
	
	protected ArrayList<Entity> world = new ArrayList<Entity>();
	protected ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
	
	/**
	 * Constructor
	 * 
	 * @param gc - the GameContainer
	 * @param p - the Player object for this level
	 * @param levelWidth - the width of the level
	 * @param levelHeight - the height of the level
	 */
	public Level(GameContainer gc, Player p, int levelWidth, int levelHeight){
		player = p;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		Color background = Color.black;
		Color textColor = Color.lightGray;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);
		
		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");
		warning = new Notification(0, 0, gc.getWidth()/3, gc.getHeight()/3, background, textColor, b1, b2, buttonYGap, "Start New Game","Are you sure you want to start a new game? Unsaved progress will be lost.");
	}

	@Override
	public abstract void init(GameContainer gc, StateBasedGame sbg) throws SlickException;

	@Override
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException;

	@Override
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException;
	
	@Override
	public abstract void mouseReleased(int button, int x, int y);
	
	@Override
	public abstract void keyReleased(int key, char c);
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		gc.resume();
		pauseMenu.hide();
		gc.setMouseGrabbed(true);
		paused = false;
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg){
		gc.setMouseGrabbed(false);
	}
	
	/**
	 * Called to pause the level
	 */
	protected void pause(){
		gc.pause();
		gc.setMouseGrabbed(false);
		paused = true;
	}
	
	/**
	 * Called to unpause the level
	 */
	protected void unpause(){
		gc.resume();
		gc.setMouseGrabbed(true);
		paused = false;
	}
	
	public int getWidth(){
		return levelWidth;
	}
	
	public int getHeight(){
		return levelHeight;
	}
	
	public ArrayList<Entity> getEntities(){
		return world;
	}
	
	public ArrayList<Checkpoint> getCheckpoints(){
		return checkpoints;
	}
	
	/**
	 * Handles all inputs for the pause menu
	 * 
	 * @param button - the mouse button that was pressed
	 * @param x - thex coordinate of the mouse
	 * @param y - the y coordinate of the mouse
	 */
	protected void handlePauseMenuInputs(int button, int x, int y){
		
		String pauseMenuSelection = pauseMenu.hover(x, y);
		
		if(button == 0){
			if(paused){
				if(pauseMenuSelection == "mainMenu"){
					warning.setHeader("Return to Main Menu");
					warning.setBody("Are you sure you want to return to the Main Menu? Unsaved progress will be lost.");
					warning.show();
//					pauseMenu.reset();
//					gc.resume();
//					for(Entity e : world){
//						e.reset();
//					}
//					sbg.enterState(Driver.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
				}else if(pauseMenuSelection == "newGame"){
					warning.setHeader("Begin a New Game");
					warning.setBody("Are you sure you want to Begin a New Game? Unsaved progress will be lost.");
					warning.show();
				}else if(pauseMenuSelection == "loadGame"){
					warning.setHeader("Load a Previous Save");
					warning.setBody("Are you sure you want to load a Previous Save? Unsaved progress will be lost.");
					warning.show();
				}else if(pauseMenuSelection == "options"){
					
				}else if(pauseMenuSelection == "quit"){
					warning.setHeader("Quit to desktop");
					warning.setBody("Are you sure you want to quit to your desktop? Unsaved progress will be lost.");
					warning.show();
				}
			}else{
				warning.hide();
			}
		}
	}
	
	/**
	 * Handles much of the autonomous and essential part of the Level
	 * eg. player death, drawing the pause menu, etc.
	 * 
	 * @param delta - time since the last frame
	 */
	protected void updateLevelEssentials(int delta){
		//if the player leaves the screen, it dies
		if(player.getY() > levelHeight){
			player.kill();
		}
		
		//if player is dead, reset the level
		if(player.isDead()){
			for(Entity e : world){
				e.reset();
			}
			for(Checkpoint c : checkpoints){
				c.reset();
			}
		}
		
		player.getInventory().update(gc, delta);
		
		if(!warning.isShowing()){
			pauseMenu.hover(mouseX, mouseY);
		}
		pauseMenu.move(camera.getX() + gc.getWidth()/2 - pauseMenu.getWidth()/2, camera.getY() + gc.getHeight()/2 - pauseMenu.getHeight()/2);
		
		warning.move(camera.getX() + gc.getWidth()/2 - warning.getWidth()/2, camera.getY() + gc.getHeight()/2 - warning.getHeight()/2);
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);
	}

	@Override
	public abstract int getID();

}
