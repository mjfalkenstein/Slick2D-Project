package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import utils.Item;

/**
 * An Item pickup that represents a Key to open a door
 */
public class Key extends Item {

	/**
	 * Constructor
	 * 
	 * @param boundingBox - the bounding hitbox for the Key
	 * @param velocity - the Key's initial velocity
	 */
	public Key(Shape boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		//Do nothing
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
	public void draw(Graphics g) {
		if(isShowing){
			g.setColor(Color.pink);
			g.fill(boundingBox);
			g.setColor(Color.pink.darker());
			g.setLineWidth(3);
			g.draw(boundingBox);
		}
	}

	@Override
	public void collide(Entity e, GameContainer gc) {
		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));

		//if both axes overlap, there is a collision
		if(xOverlap > 0 && yOverlap > 0){

			//if the player has collided with the item, add it to the player's inventory
			if(e instanceof Player){
				((Player) e).addItem(this);
				isShowing = false;
			}
		}
	}

	@Override
	public void reset() {
		move(startingX, startingY);
		isShowing = true;
	}
	
	public String toString(){
		return "Key " + startingX + " " + startingY + "\n";
	}
}
