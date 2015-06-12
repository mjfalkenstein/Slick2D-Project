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

	float maxSpeed = 20;
	float gravity = 2;
	boolean crouched = false;
	boolean canJump = false;

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
		
		if(gc.getInput().isKeyDown(Input.KEY_S) && !crouched){
			crouch();
			y += boundingBox.getHeight();
		}else if(!gc.getInput().isKeyDown(Input.KEY_S) && crouched){
			uncrouch();
		}
		
		if(velocity.getX() > 0 && !gc.getInput().isKeyDown(Input.KEY_D)){
			velocity.setX(velocity.getX() - 1);
		}
		if(velocity.getX() < 0 && !gc.getInput().isKeyDown(Input.KEY_A)){
			velocity.setX(velocity.getX() + 1);
		}

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
	 */
	public void draw(Graphics g) {
		g.setColor(Color.red.darker());
		g.draw(boundingBox);
		g.setColor(Color.red);
		g.fill(boundingBox);
	}
	
	/**
	 * Jump in a given direction
	 * 
	 * @param direction - either "UP", "LEFT", or "RIGHT"
	 */
	public void jump(String direction){
		if(direction.equals("LEFT")){
			setVelocity(maxSpeed, -30.0f);
		}else if(direction.equals("RIGHT")){
			setVelocity(-maxSpeed, -30.0f);
		}else{
			setVelocity(velocity.getX(), -30.0f);
		}
		canJump = false;
	}

	/**
	 * Shrinks the hitbox of the player by half vertically
	 */
	public void crouch(){
		boundingBox = new Rectangle(x, y + height/2, width, height/2);
		height = boundingBox.getHeight();
		crouched = true;
	}
	
	/**
	 * Restores the hitbox of the player to its full height
	 */
	public void uncrouch(){
		boundingBox = new Rectangle(x, y, width, height * 2);
		height *= 2;
		crouched = false;
	}

	/**
	 * returns true if this entity has collided with the given 
	 * 
	 * This method also handles when/how to jump
	 * 
	 * @param e - Entity we're checking the collision for
	 * @param gc - GameContainer
	 * 
	 * @return - true if the collision occurred, false otherwise
	 */
	public void collide(Entity e, GameContainer gc){
		
		//TODO
		//Fix issue with jumping when mid-air/jumping after falling without colliding
		//jumping after walking off a platform causes the player to immediately jump upon hitting the ground
		
		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));
		
		//if both axes overlap, there is a collision
		if(xOverlap > 0 && yOverlap > 0){

			//PLATFORMS
			if(e instanceof Platform){
				
				canJump = true;
				
				//collision occurred on the Y axis (vertically oriented)
				if(yOverlap < xOverlap){
					//player is above the Platform
					if(boundingBox.getCenterY() < e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getY() - height;

						//handle jumping
						if(gc.getInput().isKeyPressed(Input.KEY_W) || gc.getInput().isKeyPressed(Input.KEY_SPACE)){
							jump("UP");
						}
						
					//player is below the Platform
					}if(boundingBox.getCenterY() > e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getBoundingBox().getMaxY();
						
						//if overlap is too great, assume player has uncrouched under a low ceiling and needs to be moved out
						if(yOverlap > 15){
							if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
								move(x + 5, y);
							}else{
								move(x - 5, y);
							}
						}
					}

				//collision occurred on the X axis (horizontally oriented)
				}if(yOverlap >= xOverlap){
					//player is to the left of the Platform
					if(boundingBox.getCenterX() < e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getX() - boundingBox.getWidth();
						
						//handle jumping
						if(gc.getInput().isKeyPressed(Input.KEY_W) || gc.getInput().isKeyPressed(Input.KEY_SPACE)){
							jump("RIGHT");
						}

					//player is to the right of the Platform
					}if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getBoundingBox().getMaxX();
						
						//handle jumping
						if(gc.getInput().isKeyPressed(Input.KEY_W) || gc.getInput().isKeyPressed(Input.KEY_SPACE)){
							jump("LEFT");
						}
					}
				}
			}
		}
		if(!canJump){
			gc.getInput().isKeyPressed(Input.KEY_W);
			gc.getInput().isKeyPressed(Input.KEY_SPACE);
		}
	}
	
	/**
	 * Resets the Entity to its original position, velocity, and states
	 */
	public void reset(){
		move(startingX, startingY);
		setVelocity(0, 0);
		canJump = false;
		crouched = false;
		velocity = startingVelocity;
	}
}
