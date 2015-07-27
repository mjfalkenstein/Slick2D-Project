package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Transform;

/**
 * A simple enemy that follows a target so long as the target is within some range
 */
public class FollowerEnemy extends Entity{

	float startingX, startingY;
	float maxSpeed = 10;
	float acceleration = 1;
	float followRange = 300;
	Entity target;
	boolean dead = false;
	boolean following = false;
	private int threshold = 1000;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param target - the entity that this will follow
	 */
	public FollowerEnemy(Circle boundingBox, Vector2f velocity, Entity target) {
		super(boundingBox, velocity);
		startingX = boundingBox.getX();
		startingY = boundingBox.getY();
		this.target = target;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		threshold -= delta;
		if(Math.abs(boundingBox.getCenterX() - target.getCenterX()) < followRange && Math.abs(boundingBox.getCenterY() - target.getCenterY()) < followRange){
			following = true;
		}else{
			following = false;
		}
		
		if(velocity.getX() > maxSpeed){
			setVelocity(maxSpeed, velocity.getY());
		}else if(velocity.getX() < -maxSpeed){
			setVelocity(-maxSpeed, velocity.getY());
		}
		if(velocity.getY() > maxSpeed){
			setVelocity(velocity.getX(), maxSpeed);
		}else if(velocity.getY() < -maxSpeed){
			setVelocity(velocity.getX(), -maxSpeed);
		}

		if(following){
			follow();
		}else{
			if(threshold <= 0){
				Random r = new Random();
				if(r.nextInt(10) > 5){
					setVelocity(r.nextInt(10) - 5, r.nextInt(10) - 5);
				}else{
					setVelocity(0, 0);
				}
				threshold = 1000;
			}
		}
		y += velocity.getY() * delta / gc.getFPS();
		x += velocity.getX() * delta / gc.getFPS();
		
		if(x < 0){
			setVelocity(0, velocity.getY());
		}
		if(y < 0){
			setVelocity(velocity.getX(), 0);
		}

		boundingBox.setLocation(x, y);
	}

	@Override
	public void move(float x, float y) {
		this.x = x;
		this.y = y;

		boundingBox.setLocation(x, y);
	}

	@Override
	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fill(boundingBox);
		g.setColor(Color.yellow.darker());
		g.setLineWidth(3);
		g.draw(boundingBox);
	}

	@Override
	public void collide(Entity e, GameContainer gc) {

		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));

		//if both axes overlap, there is a collision
		if(xOverlap > 0 && yOverlap > 0){

			//Player
			if(e instanceof Player){
				((Player) e).kill();
			}
		}
	}

	@Override
	public void reset() {
		move(startingX, startingY);
		following = false;
	}

	/**
	 * Used to follow the designated target at max speed
	 */
	private void follow(){
		if(following){
			if(boundingBox.getCenterX() - target.getCenterX() < 0){
				setVelocity(velocity.getX() + acceleration, velocity.getY());
			}else if(boundingBox.getCenterX() - target.getCenterX() > 0){
				setVelocity(velocity.getX() - acceleration, velocity.getY());
			}
			if(boundingBox.getCenterY() - target.getCenterY() < 0){
				setVelocity(velocity.getX(), velocity.getY() + acceleration);
			}else if(boundingBox.getCenterY() - target.getCenterY() > 0){
				setVelocity(velocity.getX(), velocity.getY() - acceleration);
			}
		}else{
			setVelocity(velocity.getX()/2, velocity.getY()/2);
			if(Math.abs(velocity.getX()) < 1){
				setVelocity(0, velocity.getY());
			}
			if(Math.abs(velocity.getY()) < 1){
				setVelocity(velocity.getX(), 0);
			}
		}
	}

	public void setTarget(Entity e){
		target = e;
	}

}
