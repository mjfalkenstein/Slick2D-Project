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
import entities.AlternatingLaser;
import entities.Door;
import entities.Entity;
import entities.Fan;
import entities.FollowerEnemy;
import entities.Friendly;
import entities.HorizontalOscillatingPlatform;
import entities.Key;
import entities.SlidingDoor;
import entities.SolidLaser;
import entities.SpikesDown;
import entities.SpikesLeft;
import entities.SpikesRight;
import entities.SpikesUp;
import entities.StationaryPlatform;
import entities.Player;
import entities.TurretEnemy;
import entities.VerticalOscillatingPlatform;

/**
 * A simple test level designed to integrate all entities in one environment
 */
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
	AlternatingLaser aLaser;
	SolidLaser sLaser;
	Fan fan;
	SlidingDoor slidingDoor;

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

		ground = new StationaryPlatform(new Rectangle(50, 700, 1250, 40), new Vector2f(0, 0));
		platform = new StationaryPlatform(new Rectangle(650, 480, 650, 40), new Vector2f(0, 0));
		leftWall = new StationaryPlatform(new Rectangle(50, ground.getY()-400, 40, 400), new Vector2f(0, 0));
		smallRightWall = new StationaryPlatform(new Rectangle(ground.getX() + ground.getWidth() - 40, ground.getY() - 25, 40, 25), new Vector2f(0, 0));
		bigRightWall = new StationaryPlatform(new Rectangle(platform.getMaxX(), 0, 40, 275), new Vector2f(0, 0));
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
		door = new Door(new Rectangle(stair2.getMaxX() - 50, stair2.getY() - 100, 50, 100), new Vector2f(0, 0), key2);
		aLaser = new AlternatingLaser(new Rectangle(bigRightWall.getX(), bigRightWall.getMaxY() - 40, 40, 40), new Vector2f(0, 0), platform.getY() - bigRightWall.getMaxY(), Entity.SOUTH, 2000, 2000);
		sLaser = new SolidLaser(new Rectangle(leftWall.getX(), leftWall.getY(), 40, 40), new Vector2f(0, 0), 500, Entity.NORTH);
		fan = new Fan(new Rectangle(ground.getMaxX() + 100, ground.getY(), 200, 40), new Vector2f(0, 0), 0.03f, 500, Entity.NORTH);
		slidingDoor = new SlidingDoor(new Rectangle(platform.getX() + 50, platform.getMaxY(), 40, ground.getY() - platform.getMaxY()), new Vector2f(0, 0), key1, Entity.SOUTH);
		
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
		world.add(aLaser);
		world.add(sLaser);
		world.add(fan);
		world.add(slidingDoor);
		
		world.add(friendly);
		world.add(follower);
		world.add(turret1);
		world.add(turret2);
		
		checkpoints.add(checkpoint);

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
				
				player.collide(e, gc);
				friendly.collide(e, gc);
				
				//leave these commented out if you don't want to die
				//otherwise, uncomment whichever one you want to test
				
				//follower.collide(e, gc);
				//turret1.collide(e, gc);
				//turret2.collide(e, gc);
				//spikesUp.collide(e, gc);
				//spikesDown.collide(e, gc);
				//spikesLeft.collide(e, gc);
				//spikesRight.collide(e, gc);
				//aLaser.collide(e, gc);
				//sLaser.collide(e, gc);

				fan.collide(e, gc);
				slidingDoor.collide(e, gc);
				door.collide(e, gc);
				
				e.update(gc, delta);
			}
			
			key1.collide(player, gc);
			key2.collide(player, gc);
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
