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
	
	public void pause(){
		gc.pause();
		gc.setMouseGrabbed(false);
		paused = true;
	}
	
	public void unpause(){
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
	
	public void handlePauseMenu(int button, int x, int y){
		
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

	@Override
	public abstract int getID();

}
