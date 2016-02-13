package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import utils.Direction;

/**
 * This class represents anything that can act as a solid object on the screen
 */
public abstract class Entity {
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	
	protected float x, y, width, height, startingX, startingY, startingMaxX, startingMaxY;
	protected String spritePath;
	protected SpriteSheet sprites;
	boolean visible;
	Direction direction;
	
	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Entity
	 * @param velocity - the initial velocity
	 */
	public Entity(int x, int y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		visible = true;
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
	public void rotate(Direction d){
		direction = d;
	}
	
	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public abstract void draw(Graphics g);
	
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

	public float getY() {
		return y;
	}
	
	public void hide(){
		visible = false;
	}
	
	public void show(){
		visible = true;
	}

	public float getStartingX() {
		return startingX;
	}
	
	public float getStartingY(){
		return startingY;
	}
	
}
