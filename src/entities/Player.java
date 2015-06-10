package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import entities.Entity;

public class Player extends Entity {
	
	float maxSpeed = 10;
	float gravity = 2;

	public Player(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
		System.out.println("Width: " + width + " Height: " + height);
	}

	public void update(GameContainer gc, int delta) {
		setVelocity(velocity.getX(), velocity.getY() + gravity);
		if(gc.getInput().isKeyDown(Input.KEY_A) && velocity.getX() > -maxSpeed){
			setVelocity(velocity.getX() - 1, velocity.getY());
		}else if(gc.getInput().isKeyDown(Input.KEY_D) && velocity.getX() < maxSpeed){
			setVelocity(velocity.getX() + 1, velocity.getY());
		}
		
		x += velocity.getX() * delta / gc.getFPS();
		y += velocity.getY() * delta / gc.getFPS();
		
		boundingBox.setLocation(x, y);
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
		
		boundingBox.setLocation(x, y);
	}
	
	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	public void draw(Graphics g) {
		g.setColor(Color.red.darker());
		g.draw(boundingBox);
		g.setColor(Color.red);
		g.fill(boundingBox);
	}
	
	public void jump(){
		velocity.setY(-30.0f);
	}
	
	public boolean collide(Entity e, GameContainer gc){
		if(boundingBox.intersects(e.getBoundingBox())){
			//TODO: figure out how to calculate which side of the entity the player collides with
			return true;
		}
		return false;
	}
}
