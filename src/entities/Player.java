package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import entities.Entity;

/**
 * The player as it is represented on the screen
 */
public class Player extends Entity {

	float maxSpeed = 15;
	float gravity = 2;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 */
	public Player(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	/**
	 * Called every frame, used to update position, state, etc
	 * 
	 * @param gc - GameContainer
	 * @param delta - time difference since the last frame
	 */
	public void update(GameContainer gc, int delta) {
		setVelocity(velocity.getX(), velocity.getY() + gravity);
		if(gc.getInput().isKeyDown(Input.KEY_A) && velocity.getX() > -maxSpeed){
			setVelocity(velocity.getX() - 1, velocity.getY());
		}else if(gc.getInput().isKeyDown(Input.KEY_D) && velocity.getX() < maxSpeed){
			setVelocity(velocity.getX() + 1, velocity.getY());
		}
		
		if(velocity.getX() > 0 && !gc.getInput().isKeyDown(Input.KEY_D)){
			velocity.setX(velocity.getX() - 1);
		}
		if(velocity.getX() < 0 && !gc.getInput().isKeyDown(Input.KEY_A)){
			velocity.setX(velocity.getX() + 1);
		}

		x += velocity.getX() * delta / gc.getFPS();
		y += velocity.getY() * delta / gc.getFPS();

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
	 */
	public void draw(Graphics g) {
		g.setColor(Color.red.darker());
		g.draw(boundingBox);
		g.setColor(Color.red);
		g.fill(boundingBox);
	}

	/**
	 * Causes the player to jump vertically
	 */
	public void jump(){
		velocity.setY(-30.0f);
	}

	/**
	 * returns true if this entity has collided with the given 
	 * 
	 * @param e - Entity we're checking the collision for
	 * @param gc - GameContainer
	 * 
	 * @return - true if the collision occurred, false otherwise
	 */
	public boolean collide(Entity e, GameContainer gc){
		if(boundingBox.intersects(e.getBoundingBox())){
			
			//calculating the overlap of the Player and the entity on each of the axes
			//whichever overlap is smaller indicates the axis on which the collision occurred
			float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
			float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));
			
			//PLATFORMS
			if(e instanceof Platform){
				
				//collision occurred on the Y axis (vertically oriented)
				if(yOverlap < xOverlap){
					//player is above the Platform
					if(boundingBox.getCenterY() < e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getY() - height;
						return true;
					//player is below the Platform
					}if(boundingBox.getCenterY() > e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getBoundingBox().getMaxY();
						return true;
					}

				//collision occurred on the X axis (horizontally oriented)
				}if(yOverlap >= xOverlap){
					//player is to the left of the Platform
					if(boundingBox.getCenterX() < e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getX() - boundingBox.getWidth();
						return true;

					//player is to the right of the Platform
					}if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getBoundingBox().getMaxX();
						return true;
					}
				}
			}
		}
		return false;
	}
}
