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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import driver.Driver;
import utils.BackgroundBarsAnimation;
import utils.Camera;
import utils.Checkpoint;
import utils.Level;
import utils.PauseMenu;
import entities.Door;
import entities.Entity;
import entities.FollowerEnemy;
import entities.Friendly;
import entities.HorizontalOscillatingPlatform;
import entities.Key;
import entities.SpikesDown;
import entities.SpikesLeft;
import entities.SpikesRight;
import entities.SpikesUp;
import entities.StationaryPlatform;
import entities.Player;
import entities.TurretEnemy;
import entities.VerticalOscillatingPlatform;

public class Level0 extends Level{
	
	Friendly friendly;
	FollowerEnemy follower;
	TurretEnemy turret1, turret2;
	StationaryPlatform ground, platform, leftWall, smallRightWall, bigRightWall, stair1, stair2, stair3;
	HorizontalOscillatingPlatform HOP1;
	VerticalOscillatingPlatform VOP1;
	Key key1, key2;
	SpikesUp spikesUp;
	SpikesDown spikesDown;
	SpikesLeft spikesLeft;
	SpikesRight spikesRight;
	Door door;
	Checkpoint checkpoint;

	Circle background;

	Color sky = Color.decode("#99CCFF");
	BackgroundBarsAnimation backgroundAnimation;
	
	public Level0(Player p, int levelWidth, int levelHeight) {
		super(p, levelWidth, levelHeight);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
		
		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);

		ground = new StationaryPlatform(new Rectangle(50, gc.getHeight() * 9/10, gc.getWidth() - 100, 40), new Vector2f(0, 0));
		platform = new StationaryPlatform(new Rectangle(gc.getWidth()/2 - 50, gc.getHeight()/2 + 100, gc.getWidth()/2, 40), new Vector2f(0, 0));
		leftWall = new StationaryPlatform(new Rectangle(50, ground.getY()-400, 40, 400), new Vector2f(0, 0));
		smallRightWall = new StationaryPlatform(new Rectangle(ground.getX() + ground.getWidth() - 40, ground.getY() - 50, 40, 50), new Vector2f(0, 0));
		bigRightWall = new StationaryPlatform(new Rectangle(platform.getMaxX(), 0, 50, 275), new Vector2f(0, 0));
		stair1 = new StationaryPlatform(new Rectangle(platform.getX() - 100, platform.getY() + 100, 100, 40), new Vector2f(0, 0));
		stair2 = new StationaryPlatform(new Rectangle(platform.getMaxX() + 100, platform.getY() - 100, 100, 40), new Vector2f(0, 0));
		stair3 = new StationaryPlatform(new Rectangle(stair2.getMaxX() + 100, stair2.getY() - 100, 100, 40), new Vector2f(0, 0));
		HOP1 = new HorizontalOscillatingPlatform(new Rectangle(200, 400, 200, 40), new Vector2f(0, 0), 600);
		VOP1 = new VerticalOscillatingPlatform(new Rectangle (1000, 400, 200, 40), new Vector2f(0, 0), 100);
		key1 = new Key(new Circle(500, 600, 15), new Vector2f(0, 0));
		key2 = new Key(new Circle(1000, 600, 15), new Vector2f(0, 0));
		spikesUp = new SpikesUp(new Rectangle(ground.getMaxX() - 200 - smallRightWall.getWidth(), ground.getY() - 50, 200, 50), new Vector2f(0, 0));
		spikesDown = new SpikesDown(new Rectangle(platform.getCenterX() - 100, platform.getMaxY(), 200, 50), new Vector2f(0, 0));
		spikesLeft = new SpikesLeft(new Rectangle(bigRightWall.getX() - 50, bigRightWall.getY(), 50, bigRightWall.getHeight()), new Vector2f(0, 0));
		spikesRight = new SpikesRight(new Rectangle(bigRightWall.getMaxX(), bigRightWall.getY(), 50, bigRightWall.getHeight()), new Vector2f(0, 0));
		door = new Door(new Rectangle(stair2.getMaxX() - 50, stair2.getY() - 100, 50, 100), new Vector2f(0, 0), key1);
		
		//player = new Player(new Rectangle(120, 100, 40, 60), new Vector2f(0, 0));
		String s = "This is testing the speech bubble. Hello goodbye a b c 1 2 3 hopefully this works this should be on page 2 by now maybe even page 3 lets try getting onto the third page oh yeah lets go here we come fourth page";
		friendly = new Friendly(new Rectangle(ground.getX() + 100, ground.getY() - 75, 40, 60), new Vector2f(0, 0), s, true);
		follower = new FollowerEnemy(new Circle(1000, 300, 15), new Vector2f(0, 0), player);
		turret1 = new TurretEnemy(new Circle(600, 200, 25), new Vector2f(0, 0), player);
		turret2 = new TurretEnemy(new Circle(1000, 200, 25), new Vector2f(0, 0), player);

		checkpoint = new Checkpoint(this, new Rectangle(stair3.getX(), stair3.getY() - 100, stair2.getWidth(), 100), player, getID());
		
		world.add(player);
		world.add(ground);
		world.add(platform);
		world.add(leftWall);
		world.add(smallRightWall);
		world.add(bigRightWall);
		world.add(stair1);
		world.add(stair2);
		world.add(stair3);
		world.add(HOP1);
		world.add(VOP1);
		world.add(key1);
		world.add(key2);
		world.add(spikesUp);
		world.add(spikesDown);
		world.add(spikesLeft);
		world.add(spikesRight);
		world.add(door);
		
		world.add(friendly);
		world.add(follower);
		world.add(turret1);
		world.add(turret2);

		background = new Circle(gc.getWidth()/2, gc.getHeight()*3, gc.getHeight()*2.5f);

		camera = new Camera(gc, levelWidth, levelHeight);

		pauseMenu = new PauseMenu(gc, gc.getGraphics(), Color.black, Color.lightGray);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.translate(gc, g, player);

		g.setBackground(sky);
		g.setColor(Color.decode("#99FF33"));
		g.fill(background);

		backgroundAnimation.draw(g);

		for(Entity e : world){
			e.draw(g);
		}
		
		pauseMenu.draw(g);
		
		checkpoint.draw(g);
		
		if(player.getInventory() != null){
			player.getInventory().draw(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//make sure to add cameraX/cameraY to account for moving camera
		int mouseX = gc.getInput().getMouseX() + camera.getX();
		int mouseY = gc.getInput().getMouseY() + camera.getY();

		//make sure to call collide before update
		if(!paused){

			for(Entity e : world){
				
				player.collide(e, gc);
				friendly.collide(e, gc);
				//follower.collide(e, gc);
				//turret1.collide(e, gc);
				//turret2.collide(e, gc);
				//spikesUp.collide(e, gc);
				//spikesDown.collide(e, gc);
				//spikesLeft.collide(e, gc);
				//spikesRight.collide(e, gc);
				door.collide(e, gc);
				
				e.update(gc, delta);
			}
			
			checkpoint.collide(gc);
			
			key1.collide(player, gc);
			key2.collide(player, gc);

			//if the player leaves the screen, it dies
			if(player.getY() > levelHeight){
				player.kill();
			}
			
			player.getInventory().move(camera.getX(), camera.getY());
		}
		
		player.getInventory().update(gc, delta);
		
		if(player.getInventory().isVisible()){
			pause();
		}else{
			unpause();
		}
		
		//if the player is dead, pause and reset
		if(player.isDead()){
			for(Entity e : world){
				e.reset();
			}
			// pause();
		}

		pauseMenu.hover(mouseX, mouseY);
		pauseMenu.move(camera.getX() + gc.getWidth()/2 - pauseMenu.getWidth()/2, camera.getY() + gc.getHeight()/2 - pauseMenu.getHeight()/2);
	}

	@Override
	public void mouseReleased(int button, int x, int y){
		//make sure to add cameraX/cameraY to account for moving camera
		x += camera.getX();
		y += camera.getY();
		
		String pauseMenuSelection = pauseMenu.hover(x, y);
		
		if(button == 0){
			if(paused){
				if(pauseMenuSelection == "mainMenu"){
					pauseMenu.reset();
					gc.resume();
					for(Entity e : world){
						e.reset();
					}
					sbg.enterState(Driver.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
				}else if(pauseMenuSelection == "quit"){
					gc.exit();
				}
			}
		}
	}

	@Override
	public void keyPressed(int key, char c){
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

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		gc.resume();
		pauseMenu.hide();
		gc.setMouseGrabbed(true);
		paused = false;
		player.reset();
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
}
