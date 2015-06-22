package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import utils.Dialogue;
import entities.Entity;

/**
 * The player as it is represented on the screen
 */
public class Friendly extends Entity {

	float maxSpeed = 6;
	float gravity = 2;
	int time = 0;
	Dialogue dialogue;
	String text;
	int threshold = 1000;
	boolean wander = false;
	boolean canSpeak = false;
	boolean speaking = false;
	Rectangle speakingBox;
	Color bg = Color.black;
	Color textColor = Color.lightGray;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 */
	public Friendly(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
		speakingBox = new Rectangle(boundingBox.getX() - boundingBox.getWidth(), boundingBox.getY(), boundingBox.getWidth() * 3, boundingBox.getHeight());
	}

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param text - text for a speech bubble
	 */
	public Friendly(Rectangle boundingBox, Vector2f velocity, String text) {
		super(boundingBox, velocity);
		this.text = text;
		dialogue = new Dialogue(boundingBox.getX(), boundingBox.getY(), text, bg, textColor);
		speakingBox = new Rectangle(boundingBox.getX() - boundingBox.getWidth(), boundingBox.getY(), boundingBox.getWidth() * 3, boundingBox.getHeight());
	}

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param wander - whether or not the friendly should wander aimlessly
	 */
	public Friendly(Rectangle boundingBox, Vector2f velocity, boolean wander) {
		super(boundingBox, velocity);
		this.wander = wander;
		speakingBox = new Rectangle(boundingBox.getX() - boundingBox.getWidth(), boundingBox.getY(), boundingBox.getWidth() * 3, boundingBox.getHeight());
	}

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param text - text for a speech bubble
	 * @param wander - whether or not the friendly should wander aimlessly
	 */
	public Friendly(Rectangle boundingBox, Vector2f velocity, String text, boolean wander) {
		super(boundingBox, velocity);
		this.text = text;
		this.wander = wander;
		dialogue = new Dialogue(boundingBox.getX(), boundingBox.getY(), text, bg, textColor);
		speakingBox = new Rectangle(boundingBox.getX() - boundingBox.getWidth(), boundingBox.getY(), boundingBox.getWidth() * 3, boundingBox.getHeight());
	}

	/**
	 * Called every frame, used to update position, state, etc
	 * 
	 * @param gc - GameContainer
	 * @param delta - time difference since the last frame
	 */
	public void update(GameContainer gc, int delta) {
		setVelocity(velocity.getX(), velocity.getY() + gravity);

		//handle random wandering
		if(wander && !speaking){
			time += delta;
			if(threshold - time <= 0){
				Random r = new Random();
				threshold = r.nextInt(250) + 750;
				int direction = r.nextInt(6);

				if(direction <= 3){
					setVelocity(0, velocity.getY());
				}if(direction == 4){
					setVelocity(-maxSpeed, velocity.getY());
				}if(direction == 5){
					setVelocity(maxSpeed, velocity.getY());
				}

				time = 0;
			}
		}

		if(speaking){
			setVelocity(0, 0);
		}

		dialogue.move(x - dialogue.getWidth()/2 + boundingBox.getWidth()/2, y - dialogue.getHeight() - 10);

		if(!canSpeak){
			dialogue.hide();
			speaking = false;
		}

		handleInputs(gc);

		y += velocity.getY() * delta / gc.getFPS();
		x += velocity.getX() * delta / gc.getFPS();

		speakingBox.setLocation(x - boundingBox.getWidth(), y);
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

		speakingBox.setLocation(x - boundingBox.getWidth(), y);
		boundingBox.setLocation(x, y);
	}

	/**
	 * Rotates the entity by a given amount in degrees
	 * 
	 * @param degrees - rotation
	 */
	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
		speakingBox.transform(Transform.createRotateTransform(degrees));
	}

	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fill(boundingBox);
		if(dialogue != null){
			dialogue.draw(g);
		}
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
	public void collide(Entity e, GameContainer gc){

		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));

		if(e instanceof Player){
			if(speakingBox.intersects(e.getBoundingBox())){
				canSpeak = true;
			}else{
				canSpeak = false;
			}
		}

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

						//keep the friendly from wandering off an edge
						if(boundingBox.getX() <= e.getBoundingBox().getX()){
							setVelocity(0, 0);
						}
						if(boundingBox.getMaxX() >= e.getBoundingBox().getMaxX()){
							setVelocity(0, 0);
						}
					}
				}

				//collision occurred on the X axis (horizontally oriented)
				if(yOverlap >= xOverlap){
					//player is to the left of the Platform
					if(boundingBox.getCenterX() < e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getX() - boundingBox.getWidth();

						//player is to the right of the Platform
					}if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getBoundingBox().getMaxX();
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
		velocity = startingVelocity;
		dialogue.reset();
	}

	/**
	 * Called from update(), used to handle all inputs
	 * 
	 * @param gc - GameConatiner
	 */
	public void handleInputs(GameContainer gc){
		Input input = gc.getInput();

		if(input.isKeyPressed(Input.KEY_LSHIFT)){
			if(canSpeak && !dialogue.showing()){
				dialogue.show();
				speaking = true;
			}else if(dialogue.showing() && !gc.isPaused()){
				dialogue.advance();
			}
		}
	}
}
