package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

/**
 * This class represents anything that can act as a solid object on the screen
 */
public abstract class Entity {
	
	protected float x, y, width, height, startingX, startingY;
	protected Shape boundingBox;
	protected Vector2f velocity, startingVelocity;
	protected String spritePath;
	protected SpriteSheet sprites;
	
	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Entity
	 * @param velocity - the initial velocity
	 */
	public Entity(Shape boundingBox, Vector2f velocity) {
		super();
		x = boundingBox.getMinX();
		y = boundingBox.getMinY();
		startingX = x;
		startingY = y;
		width = boundingBox.getWidth();
		height = boundingBox.getHeight();
		this.boundingBox = boundingBox;
		this.velocity = velocity;
		startingVelocity = velocity;
	}
	
	/**
	 * Called every frame, used to update position, state, etc
	 * 
	 * @param gc - GameContainer
	 * @param delta - time difference since the last frame
	 */
	public abstract void update(GameContainer gc, int delta);
	
	/**
	 * Instantly moves the entity to new coordinates (x, y)
	 * 
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public abstract void move(float x, float y);
	
	/**
	 * Rotates the entity by a given amount in degrees
	 * 
	 * @param degrees - rotation
	 */
	public abstract void rotate(float degrees);
	
	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * returns true if this entity has collided with the given 
	 * 
	 * @param e - Entity we're checking the collision for
	 * @param gc - GameContainer
	 * 
	 */
	public abstract void collide(Entity e, GameContainer gc);
	
	/**
	 * Resets the Entity to its original position, velocity, and states
	 */
	public abstract void reset();
	
	public Shape getBoundingBox(){
		return boundingBox;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}
	
	public float getCenterX(){
		return boundingBox.getCenterX();
	}
	
	public float getCenterY(){
		return boundingBox.getCenterY();
	}
	
	public float getMaxX(){
		return boundingBox.getMaxX();
	}
	
	public float getMaxY(){
		return boundingBox.getMaxY();
	}

	public float getY() {
		return y;
	}
	
	public Vector2f getVelocity(){
		return velocity;
	}
	
	public void setVelocity(Vector2f velocity){
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y){
		velocity = new Vector2f(x, y);
	}
	
}
