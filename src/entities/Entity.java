package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity {
	
	protected float x, y, width, height;
	protected float maxPlayerSpeed = 15;
	protected Rectangle boundingBox;
	protected Vector2f velocity;
	
	public Entity(Rectangle boundingBox, Vector2f velocity) {
		super();
		x = boundingBox.getMinX();
		y = boundingBox.getMinY();
		width = boundingBox.getWidth();
		height = boundingBox.getHeight();
		this.boundingBox = boundingBox;
		this.velocity = velocity;
	}
	
	public abstract void update(GameContainer gc, int delta);
	
	public abstract void move(float x, float y);
	
	public abstract void rotate(float degrees);
	
	public abstract void draw(Graphics g);

	public abstract boolean collide(Entity e, GameContainer gc);
	
	public Rectangle getBoundingBox(){
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
