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
public class StationaryPlatform extends Entity {

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 */
	public StationaryPlatform(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		x += velocity.getX() * delta / gc.getFPS();
		y += velocity.getY() * delta / gc.getFPS();

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
	public void draw(Graphics g){
		Color c = Color.black;
		c.a = 1.0f;
		g.setColor(c);
		g.fill(boundingBox);
	}

	@Override
	public void collide(Entity e, GameContainer gc){
		//do nothing, collision is handles by other entities
	}
	
	@Override
	public void reset(){
		move(startingX, startingY);
		velocity = startingVelocity;
	}
}
