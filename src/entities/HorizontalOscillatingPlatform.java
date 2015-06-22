package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import entities.Entity;

/**
 * A solid platform that the player can stand on
 * 
 * serves as ground, ceiling, and walls
 */
public class HorizontalOscillatingPlatform extends Entity {
	
	int counter = 0;
	float distance, minX, maxX, startingX;
	float oldX;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param maxX - the maximum X value that the platform will reach before turning around
	 */
	public HorizontalOscillatingPlatform(Rectangle boundingBox, Vector2f velocity, float maxX) {
		super(boundingBox, velocity);
		minX = boundingBox.getX();
		this.maxX = maxX;
		distance = maxX - minX;
	}

	/**
	 * Called every frame, used to update position, state, etc
	 * 
	 * @param gc - GameContainer
	 * @param delta - time difference since the last frame
	 */
	public void update(GameContainer gc, int delta) {
		counter = counter%360;
		
		oldX = boundingBox.getX();
		
		move((float) (minX + distance * -Math.sin(counter * Math.PI / 180)), boundingBox.getY());
		
		setVelocity(x - oldX, 0);
		
		counter++;
		
		System.out.println(velocity.getX());
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
	public void draw(Graphics g){
		Color c = Color.magenta;
		c.a = 1.0f;
		g.setColor(c);
		g.fill(boundingBox);
	}

	/**
	 * returns true if this entity has collided with the given 
	 * 
	 * @param e - Entity we're checking the collision for
	 * @param gc - GameContainer
	 * 
	 * @return - true if the collision occurred, false otherwise
	 */
	public void collide(Entity e, GameContainer gc){
		//do nothing, collision is handles by other entities
	}
	
	/**
	 * Resets the Entity to its original position, velocity, and states
	 */
	public void reset(){
		move(startingX, startingY);
		velocity = startingVelocity;
	}
}
