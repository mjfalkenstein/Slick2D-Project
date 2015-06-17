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
	boolean onGround = false;
	boolean onLeftWall = false;
	boolean onRightWall = false;

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
		
		handleInputs(gc);

		y += velocity.getY() * delta / gc.getFPS();
		x += velocity.getX() * delta / gc.getFPS();

		boundingBox.setLocation(x, y);
		
		onGround = false;
		onRightWall = false;
		onLeftWall = false;
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
			onRightWall = false;
		}else if(direction.equals("RIGHT")){
			setVelocity(-maxSpeed, -30.0f);
			onLeftWall = false;
		}else{
			setVelocity(velocity.getX(), -30.0f);
			onGround = false;
		}
		onGround = false;
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
		
		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));
		
		//if both axes overlap, there is a collision
		if(xOverlap > 0 && yOverlap > 0){

			//PLATFORMS
			if(e instanceof Platform){
				
				//collision occurred on the Y axis (vertically oriented)
				if(yOverlap < xOverlap){
					//player is above the Platform
					if(boundingBox.getCenterY() < e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getY() - height;

						onGround = true;
						
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
						
						onLeftWall = true;

					//player is to the right of the Platform
					}if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getBoundingBox().getMaxX();
						
						onRightWall = true;
					}
				}
			}
		}
	}
	
	/**
	 * Resets the Entity to its original position, velocity, and states
	 */
	public void reset(){
		move(startingX, startingY);
		setVelocity(0, 0);
		onGround = false;
		crouched = false;
		onLeftWall = false;
		onRightWall = false;
		velocity = startingVelocity;
	}
	
	/**
	 * Called from update(), used to handle all inputs
	 * 
	 * @param gc - GameConatiner
	 */
	public void handleInputs(GameContainer gc){
		Input input = gc.getInput();
		
		//handling horizontal movement
		if(velocity.getX() > 0){
			if(!input.isKeyDown(Input.KEY_D)){
				velocity.setX(velocity.getX() - 1);
			}
		}
		if(velocity.getX() < 0){
			if(!input.isKeyDown(Input.KEY_A)){
				velocity.setX(velocity.getX() + 1);
			}
		}
		if(input.isKeyDown(Input.KEY_A)){
			if(velocity.getX() > -maxSpeed){
				setVelocity(velocity.getX() - 1, velocity.getY());
			}
		}
		if(input.isKeyDown(Input.KEY_D)){
			if(velocity.getX() < maxSpeed){
				setVelocity(velocity.getX() + 1, velocity.getY());
			}
		}
		
		//handling crouching
		if(input.isKeyDown(Input.KEY_S)){
			if(!crouched){
				crouch();
				y += boundingBox.getHeight();
			}
		}else if(!input.isKeyDown(Input.KEY_S)){
			if(crouched){
			uncrouch();
			}
		}
		
		//handling jumping
		if(input.isKeyPressed(input.KEY_W) || input.isKeyPressed(input.KEY_SPACE)){
			if(onGround){
				jump("UP");
			}else if(onLeftWall){
				jump("RIGHT");
			}else if(onRightWall){
				jump("LEFT");
			}
		}
	}
}
