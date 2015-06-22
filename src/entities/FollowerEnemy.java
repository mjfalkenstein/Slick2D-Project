package entities;

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

	/**
	 * Called every frame, used to update position, state, etc
	 * 
	 * @param gc - GameContainer
	 * @param delta - time difference since the last frame
	 */
	public void update(GameContainer gc, int delta) {
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

		follow();

		y += velocity.getY() * delta / gc.getFPS();
		x += velocity.getX() * delta / gc.getFPS();

		boundingBox.setLocation(x, y);
	}

	/**
	 * Instantly moves the entity to new coordinates (x, y)
	 * 
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void move(float x, float y) {
		this.x = x;
		this.y = y;

		boundingBox.setLocation(x, y);
	}

	/**
	 * Rotates the entity by a given amount in degrees
	 * 
	 * @param degrees - rotation
	 */
	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fill(boundingBox);
	}

	/**
	 * returns true if this entity has collided with the given 
	 * 
	 * This method also handles when/how to jump
	 * 
	 * @param e - Entity we're checking the collision for
	 * @param gc - GameContainer
	 * 
	 */
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

	/**
	 * Resets the Entity to its original position, velocity, and states
	 */
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
