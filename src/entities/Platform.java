package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import entities.Entity;

public class Platform extends Entity {

	public Platform(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	public void update(GameContainer gc, int delta) {
		x += velocity.getX() * delta / gc.getFPS();
		y += velocity.getY() * delta / gc.getFPS();

//		if(gc.getInput().isKeyDown(gc.getInput().KEY_A) && velocity.getX() < maxPlayerSpeed){
//			setVelocity(velocity.getX() + 1, velocity.getY());
//		}else if(gc.getInput().isKeyDown(gc.getInput().KEY_D) && velocity.getX() > -maxPlayerSpeed){
//			setVelocity(velocity.getX() - 1, velocity.getY());
//		}
//		if(velocity.getX() < 0 && !gc.getInput().isKeyDown(Input.KEY_A)){
//			velocity.setX(velocity.getX() + 1);
//		}
//		if(velocity.getX() > 0 && !gc.getInput().isKeyDown(Input.KEY_D)){
//			velocity.setX(velocity.getX() - 1);
//		}

		boundingBox.setLocation(x, y);
	}

	public void move(float x, float y) {
		this.x = x;
		this.y = y;

		boundingBox.setLocation(x, y);
	}

	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fill(boundingBox);
	}

	public boolean collide(Entity e, GameContainer gc){
		if(boundingBox.intersects(e.getBoundingBox())){
			return true;
		}
		return false;
	}
}
