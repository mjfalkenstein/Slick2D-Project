package utils;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import entities.Entity;

public abstract class Item extends Entity{
	
	public int maxWidth = 64;
	public int maxHeight = 64;
	public boolean isShowing = true;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - hitbox for the item
	 * @param velocity - initial velocity
	 */
	public Item(Shape boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	@Override
	public abstract void update(GameContainer gc, int delta);

	@Override
	public abstract void move(float x, float y);

	@Override
	public abstract void rotate(float degrees);

	@Override
	public abstract void draw(Graphics g);

	@Override
	public abstract void collide(Entity e, GameContainer gc);
	
	@Override
	public abstract void reset();
	
	public void hide(){
		isShowing = false;
	}
	
	public void show(){
		isShowing = true;
	}
	
	public abstract String toString();
}
