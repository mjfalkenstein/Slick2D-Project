package utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import entities.Player;

public abstract class Level extends BasicGameState{
	
	GameContainer gc;
	StateBasedGame sbg;
	boolean paused = false;
	PauseMenu pauseMenu;
	protected Player player;
	Camera camera;
	int mouseX, mouseY;
	int levelWidth, levelHeight;
	
	public Level(Player p, int levelWidth, int levelHeight){
		player = p;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
	}

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
