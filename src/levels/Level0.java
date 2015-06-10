package levels;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import driver.Driver;
import utils.BackgroundBarsAnimation;
import entities.Entity;
import entities.Platform;
import entities.Player;

public class Level0 extends BasicGameState{

	GameContainer gc;
	StateBasedGame sbg;

	Player player;
	Platform ground, platform, leftWall, rightWall;
	
	ArrayList<Entity> world = new ArrayList<Entity>();

	Color background = Color.decode("#99CCFF");
	BackgroundBarsAnimation backgroundAnimation;

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;

		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);

		player = new Player(new Rectangle(120, 100, 75, 150), new Vector2f(0, 0));
		ground = new Platform(new Rectangle(50, gc.getHeight() * 9/10, gc.getWidth() - 100, 50), new Vector2f(0, 0));
		platform = new Platform(new Rectangle(gc.getWidth()/2 - 50, gc.getHeight()/2 + 100, gc.getWidth()/2, 50), new Vector2f(0, 0));
		leftWall = new Platform(new Rectangle(50, ground.getY()-50, 50, 50), new Vector2f(0, 0));
		rightWall = new Platform(new Rectangle(ground.getX() + ground.getWidth() - 50, ground.getY() - 50, 50, 50), new Vector2f(0, 0));
		
		world.add(ground);
		world.add(platform);
		world.add(leftWall);
		world.add(rightWall);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(background);

		backgroundAnimation.draw(g);

		player.draw(g);
		
		for(Entity e : world){
			e.draw(g);
		}
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for(Entity e : world){
			player.collide(e, gc);
		}
		
		player.update(gc, delta);

		//if the player leaves the screen, reset
		if(player.getY() > gc.getHeight() || player.getX() < -player.getWidth() || player.getX() > gc.getWidth()){
			player.move(120, 100);
			player.setVelocity(0, 0);
		}
	}

	public void keyPressed(int key, char c){
		if(key == Input.KEY_ESCAPE){
			sbg.enterState(Driver.MAIN_MENU);;
		}
		if(key == Input.KEY_W || key == Input.KEY_SPACE){
			boolean jump = false;
			for(Entity e : world){
				if(player.collide(e)){
					jump = true;
				}
			}
			if(jump){
				player.jump();
				jump = false;
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return 5;
	}

}
