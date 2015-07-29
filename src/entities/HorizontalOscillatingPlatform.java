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
	
	float maxSpeed = 0.01444444f;
	float acceleration = 0.0111111f;
	float minX, maxX;
	boolean movingLeft = false;
	boolean movingRight = true;

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
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if(movingRight){
			if(boundingBox.getMaxX() < maxX){
				if(velocity.getX() < maxSpeed){
					setVelocity(velocity.getX() + acceleration, 0);
				}
			}else{
				movingRight = false;
				movingLeft = true;
			}
		}
		if(movingLeft){
			if(boundingBox.getX() > minX){
				if(velocity.getX() > -maxSpeed){
					setVelocity(velocity.getX() - acceleration, 0);
				}
			}else{
				movingRight = true;
				movingLeft = false;
			}
		}
		
		y += velocity.getY() * delta;
		x += velocity.getX() * delta;

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
		//do nothing, collision is handled by other entities
	}
	
	@Override
	public void reset(){
		move(startingX, startingY);
		velocity = startingVelocity;
	}
}
