package utils;

import inGameMenus.InGameLoadMenu;
import inGameMenus.InGameOptionsMenu;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import driver.Driver;
import entities.Entity;
import entities.Player;

/**
 * A class that handles all level essentials
 * All other 'Levels' must inherit from this in order to work properly
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
	protected InGameLoadMenu loadMenu; 
	protected InGameOptionsMenu optionsMenu;

	boolean goToMainMenu = false;
	boolean newGame = false;
	boolean quit = false;

	protected ArrayList<Entity> world = new ArrayList<Entity>();
	protected ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();

	TrueTypeFont font;
	float fontSize = 12f;

	/**
	 * Constructor
	 * 
	 * @param gc - the GameContainer
	 * @param p - the Player object for this level
	 * @param levelWidth - the width of the level
	 * @param levelHeight - the height of the level
	 */
	public Level(GameContainer gc, Player p, int levelWidth, int levelHeight){

		//loading the font
		try{
			InputStream is = ResourceLoader.getResourceAsStream("HappyKiller.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
			awtFont = awtFont.deriveFont(fontSize);
			font = new TrueTypeFont(awtFont, false);
		}catch(Exception e){
			e.printStackTrace();
		}

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

		camera = new Camera(gc, levelWidth, levelHeight);

		pauseMenu = new PauseMenu(gc, gc.getGraphics(), Color.black, Color.lightGray);

		loadMenu = new InGameLoadMenu(gc);

		optionsMenu = new InGameOptionsMenu(gc);


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
		loadMenu.hide();
		gc.setMouseGrabbed(true);
		paused = false;
		goToMainMenu = false;
		quit = false;
		newGame = false;
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
		pauseMenu.hide();
		loadMenu.hide();
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
	 * @param x - the x coordinate of the mouse
	 * @param y - the y coordinate of the mouse
	 */
	protected void handlePauseMenuInputs(int button, int x, int y){

		String pauseMenuSelection = pauseMenu.handleMouseInput(x, y);

		if(button == 0){
			if(paused){
				if(pauseMenuSelection == "continue"){
					unpause();
					pauseMenu.hide();
				}else if(pauseMenuSelection == "mainMenu"){
					warning.setHeader("Return to Main Menu");
					warning.setBody("Are you sure you want to return to the Main Menu? Unsaved progress will be lost.");
					warning.show();
					goToMainMenu = true;
				}else if(pauseMenuSelection == "newGame"){
					warning.setHeader("Begin a New Game");
					warning.setBody("Are you sure you want to Begin a New Game? Unsaved progress will be lost.");
					warning.show();
					newGame = true;
				}else if(pauseMenuSelection == "loadGame"){
					loadMenu.show();
				}else if(pauseMenuSelection == "options"){
					optionsMenu.show();
				}else if(pauseMenuSelection == "quit"){
					warning.setHeader("Quit to desktop");
					warning.setBody("Are you sure you want to quit to your desktop? Unsaved progress will be lost.");
					warning.show();
					quit = true;
				}
			}else{
				warning.hide();
			}
			if(warning.isShowing()){
				if(b1.handleMouseInput(x, y)){
					if(goToMainMenu){
						gc.resume();
						pauseMenu.reset();
						warning.hide();
						for(Entity e : world){
							e.reset();
						}
						sbg.enterState(Driver.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
					}else if(newGame){
						gc.resume();
						pauseMenu.reset();
						warning.hide();
						for(Entity e : world){
							e.reset();
						}
						sbg.enterState(Driver.LEVEL_0, new FadeOutTransition(), new FadeInTransition());
					}else if(quit){
						gc.resume();
						pauseMenu.reset();
						warning.hide();
						for(Entity e : world){
							e.reset();
						}
						gc.exit();
					}
				}else if(b2.handleMouseInput(x, y)){
					warning.hide();
				}
			}
		}
		if(paused){
			loadMenu.handleMouseInput(button, x, y);
			optionsMenu.handleMouseInput(button, x, y);
		}
		pauseMenuSelection = "";
	}

	/**
	 * Handles much of the autonomous and essential part of the Level
	 * eg. player death, drawing the pause menu, etc.
	 * 
	 * @param delta - time since the last frame
	 */
	protected void updateLevelEssentials(int mouseX, int mouseY, int delta){
		//if the player leaves the level bounds, it dies
		if(player.getY() > levelHeight || player.getX() > levelWidth){
			player.kill();
		}

		//if player is dead, reset the level
		if(player.isDead()){
			reset();
		}
		if(!paused){
			player.getInventory().update(gc, delta);
		}

		if(	!warning.isShowing() && 
			!loadMenu.isShowing() && 
			!optionsMenu.isShowing()){

			pauseMenu.hover(mouseX, mouseY);
		}
		pauseMenu.move(camera.getX() + gc.getWidth()/2 - pauseMenu.getWidth()/2, camera.getY() + gc.getHeight()/2 - pauseMenu.getHeight()/2);

		warning.move(camera.getX() + gc.getWidth()/2 - warning.getWidth()/2, camera.getY() + gc.getHeight()/2 - warning.getHeight()/2);
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);

		for(Checkpoint c : checkpoints){
			c.collide(gc);
		}

		player.getInventory().move(camera.getX(), camera.getY());

		loadMenu.update(camera.getX(), camera.getY(), mouseX, mouseY);
		optionsMenu.update(camera.getX(), camera.getY(), mouseX, mouseY);
	}

	/**
	 * Called to draw all of the "essentials" that every level needs
	 * 
	 * @param g - the Graphics context
	 */
	protected void drawLevelEssentials(Graphics g){
		camera.translate(gc, g, player);

		for(Entity e : world){
			e.draw(g);
		}

		for(Checkpoint c : checkpoints){
			c.draw(g);
		}

		if(player.getInventory() != null){
			player.getInventory().draw(g);
		}

		pauseMenu.draw(g, font);

		warning.draw(g, font);
		warning.move(gc.getWidth()/2 - warning.getWidth()/2, gc.getHeight()/2 - warning.getHeight()/2);
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);

		loadMenu.draw(g, font);
		optionsMenu.draw(g, font);
	}

	/**
	 * Helper function that resets everything in the level to its original state
	 */
	protected void reset(){
		for(Entity e : world){
			e.reset();
		}
		for(Checkpoint c : checkpoints){
			c.reset();
		}
		pauseMenu.reset();
		optionsMenu.remove();
		warning.hide();
		unpause();
	}

	@Override
	public abstract int getID();

}
