package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
		g.setColor(Color.black);
		g.fill(boundingBox);
	}

}
