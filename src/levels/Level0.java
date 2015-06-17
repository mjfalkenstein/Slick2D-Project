package levels;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import driver.Driver;
import utils.BackgroundBarsAnimation;
import utils.Camera;
import utils.PauseMenu;
import entities.Entity;
import entities.Platform;
import entities.Player;

public class Level0 extends BasicGameState{

	GameContainer gc;
	StateBasedGame sbg;

	Player player;
	Camera camera;
	Platform ground, platform, leftWall, rightWall, stair1, stair2, stair3;

	Circle background;

	ArrayList<Entity> world = new ArrayList<Entity>();

	Color sky = Color.decode("#99CCFF");
	BackgroundBarsAnimation backgroundAnimation;

	int levelWidth = 2000;
	int levelHeight = 1000;

	boolean paused = false;
	
	PauseMenu pauseMenu;

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;

		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);

		player = new Player(new Rectangle(120, 100, 50, 75), new Vector2f(0, 0));
		ground = new Platform(new Rectangle(50, gc.getHeight() * 9/10, gc.getWidth() - 100, 50), new Vector2f(0, 0));
		platform = new Platform(new Rectangle(gc.getWidth()/2 - 50, gc.getHeight()/2 + 100, gc.getWidth()/2, 50), new Vector2f(0, 0));
		leftWall = new Platform(new Rectangle(50, ground.getY()-400, 50, 400), new Vector2f(0, 0));
		rightWall = new Platform(new Rectangle(ground.getX() + ground.getWidth() - 50, ground.getY() - 50, 50, 50), new Vector2f(0, 0));
		stair1 = new Platform(new Rectangle(platform.getX() - 100, platform.getY() + 100, 100, 50), new Vector2f(0, 0));
		stair2 = new Platform(new Rectangle(platform.getMaxX() + 100, platform.getY() - 100, 100, 50), new Vector2f(0, 0));
		stair3 = new Platform(new Rectangle(stair2.getMaxX() + 100, stair2.getY() - 100, 100, 50), new Vector2f(0, 0));

		world.add(ground);
		world.add(platform);
		world.add(leftWall);
		world.add(rightWall);
		world.add(stair1);
		world.add(stair2);
		world.add(stair3);

		background = new Circle(gc.getWidth()/2, gc.getHeight()*3, gc.getHeight()*2.5f);

		camera = new Camera(gc, levelWidth, levelHeight);
		
		pauseMenu = new PauseMenu(gc, Color.black, Color.lightGray);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.translate(gc, g, player);

		g.setBackground(sky);
		g.setColor(Color.decode("#99FF33"));
		g.fill(background);

		backgroundAnimation.draw(g);

		player.draw(g);

		for(Entity e : world){
			e.draw(g);
		}
		
		pauseMenu.draw(g);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int mouseX = gc.getInput().getAbsoluteMouseX();
		int mouseY = gc.getInput().getAbsoluteMouseY();
		
		if(!paused){
			player.update(gc, delta);

			for(Entity e : world){
				player.collide(e, gc);
			}

			//if the player leaves the screen, reset
			if(player.getY() > levelHeight || player.getMaxX() < 0 || player.getX() > levelWidth){
				player.reset();
			}
		}else{
			gc.getInput().clearKeyPressedRecord();
		}
		
		pauseMenu.hover(mouseX, mouseY);
		pauseMenu.move(camera.getX() + gc.getWidth()/2 - pauseMenu.getWidth()/2, camera.getY() + gc.getHeight()/2 - pauseMenu.getHeight()/2);
	}

	public void keyPressed(int key, char c){
		if(key == Input.KEY_ESCAPE){
			if(!paused){
				gc.pause();
				pauseMenu.show();
				gc.setMouseGrabbed(false);
				paused = true;
			}else{
				gc.resume();
				pauseMenu.hide();
				gc.setMouseGrabbed(true);
				paused = false;
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return 5;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg){
		gc.setMouseGrabbed(true);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg){
		gc.setMouseGrabbed(false);
	}

}
