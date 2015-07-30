package utils;

import java.util.ArrayList;

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
	
	protected ArrayList<Entity> world = new ArrayList<Entity>();
	
	public Level(Player p, int levelWidth, int levelHeight){
		player = p;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
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
	public abstract void keyPressed(int key, char c);
	
	@Override
	public abstract void enter(GameContainer gc, StateBasedGame sbg);

	@Override
	public abstract void leave(GameContainer gc, StateBasedGame sbg);
	
	public abstract void pause();
	
	public abstract void unpause();
	
	public int getWidth(){
		return levelWidth;
	}
	
	public int getHeight(){
		return levelHeight;
	}

	@Override
	public abstract int getID();

}
