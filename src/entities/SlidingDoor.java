package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * A simple sliding door that opens with a Key
 */
public class SlidingDoor extends Entity {

	boolean open = false;
	int direction;
	float speed = 0.1f;
	Key key;

	public SlidingDoor(Shape boundingBox, Vector2f velocity, Key key, int direction) {
		super(boundingBox, velocity);
		this.direction = direction;
		this.key = key;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if(open){
			switch(direction){
			case 0:
				if(boundingBox.getMaxY() > startingY){
					move(boundingBox.getX(), boundingBox.getY() - speed * delta);
				}
				break;
			case 1:
				if(boundingBox.getY() < startingMaxY){
					move(boundingBox.getX(), boundingBox.getY() + speed * delta);
				}
				break;
			case 2:
				if(boundingBox.getX() < startingMaxX){
					move(boundingBox.getX() + speed * delta, boundingBox.getY());
				}
				break;
			case 3:
				if(boundingBox.getMaxX() > startingX){
					move(boundingBox.getX() - speed * delta, boundingBox.getY());
				}
				break;
			}
		}
		if(!open){
			switch(direction){
			case 0:
				if(boundingBox.getMaxY() < startingY){
					move(boundingBox.getX(), boundingBox.getY() + speed * delta);
				}
				break;
			case 1:
				if(boundingBox.getY() > startingMaxY){
					move(boundingBox.getX(), boundingBox.getY() - speed * delta);
				}
				break;
			case 2:
				if(boundingBox.getX() > startingMaxX){
					move(boundingBox.getX() - speed * delta, boundingBox.getY());
				}
				break;
			case 3:
				if(boundingBox.getMaxX() < startingX){
					move(boundingBox.getX() + speed * delta, boundingBox.getY());
				}
				break;
			}
		}
	}

	@Override
	public void move(float x, float y) {
		boundingBox.setLocation(x, y);
	}

	@Override
	public void rotate(float degrees) {
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.gray.darker());
		g.setLineWidth(3);
		g.draw(boundingBox);
		g.setColor(Color.black);
		g.fill(boundingBox);
	}

	@Override
	public void collide(Entity e, GameContainer gc) {
		if(e instanceof Player){
			if(boundingBox.intersects(e.getBoundingBox())){
				if(((Player) e).has(key)){
					open = true;
					((Player) e).getInventory().removeItem(key);
				}
			}
		}
	}

	@Override
	public void reset() {
		move(startingX, startingY);
		open = false;
	}
	
	public void open(){
		open = true;
	}
	
	public void close(){
		open = false;
	}
	
	public boolean isOpen(){
		return open;
	}

}
