package levels;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import utils.BackgroundBarsAnimation;
import utils.Checkpoint;
import utils.Level;
import entities.Door;
import entities.Entity;
import entities.Friendly;
import entities.Player;

/**
 * A simple test level designed to integrate all entities in one environment
 */
public class Level0 extends Level{
	

	Circle background;

	Color sky = Color.decode("#99CCFF");
	BackgroundBarsAnimation backgroundAnimation;
	
	public Level0(GameContainer gc, Player p, int levelWidth, int levelHeight) {
		super(gc, p, levelWidth, levelHeight);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
		
		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);

		background = new Circle(gc.getWidth()/2, gc.getHeight()*3, gc.getHeight()*2.5f);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(sky);
		g.setColor(Color.decode("#99FF33"));
		g.fill(background);

		backgroundAnimation.draw(g);
		
		drawLevelEssentials(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//make sure to add cameraX/cameraY to account for moving camera
		int mouseX = gc.getInput().getMouseX() + camera.getX();
		int mouseY = gc.getInput().getMouseY() + camera.getY();

		//make sure to call collide before update
		if(!paused){

			for(Entity e : world){
				e.update(gc, delta);
			}
		}
		
		updateLevelEssentials(mouseX, mouseY, delta);
	}

	@Override
	public void mouseReleased(int button, int x, int y){
		//make sure to add cameraX/cameraY to account for moving camera
		x += camera.getX();
		y += camera.getY();
		
		handlePauseMenuInputs(button, x, y);
	}

	@Override
	public void keyReleased(int key, char c){
		if(key == Input.KEY_ESCAPE){
			if(!paused){
				pause();
				pauseMenu.show();
			}else{
				unpause();
				pauseMenu.hide();
			}
		}
	}

	@Override
	public int getID() {
		return 6;
	}
}
