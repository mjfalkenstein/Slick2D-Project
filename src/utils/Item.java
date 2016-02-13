package utils;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import entities.Entity;

public abstract class Item extends Entity{
	
	public Item(int x, int y, float width, float height) {
		super(x, y, width, height);
	}

	public int maxWidth = 64;
	public int maxHeight = 64;
	public boolean isShowing = true;

	@Override
	public abstract void update(GameContainer gc, int delta);

	@Override
	public abstract void move(float x, float y);

	@Override
	public abstract void draw(Graphics g);
	
	public void hide(){
		isShowing = false;
	}
	
	public void show(){
		isShowing = true;
	}
	
	public abstract String toString();
}
