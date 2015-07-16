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
public class VerticalOscillatingPlatform extends Entity {
	
	int counter = 0;
	float distance, center, minY, maxY;
	float oldY;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 * @param maxX - the maximum X value that the platform will reach before turning around
	 */
	public VerticalOscillatingPlatform(Rectangle boundingBox, Vector2f velocity, float maxY) {
		super(boundingBox, velocity);
		minY = boundingBox.getY();
		this.maxY = maxY;
		distance = (maxY - minY)/2;
		center = (maxY + minY)/2;
		startingX = center;
		boundingBox.setLocation(boundingBox.getX(), center);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		counter = counter%360;
		
		oldY = boundingBox.getY();
		
		move(boundingBox.getX(), (float) (center + distance * -Math.sin(counter * Math.PI / 180)));
		
		setVelocity(0, (y - oldY)/delta * gc.getFPS());
		
		counter++;
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
		Color c = Color.magenta;
		c.a = 1.0f;
		g.setColor(c);
		g.fill(boundingBox);
		g.setColor(Color.magenta.darker());
		g.setLineWidth(3);
		g.draw(boundingBox);
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
