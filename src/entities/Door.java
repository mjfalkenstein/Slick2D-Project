package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class Door extends Entity {

	boolean open = false;
	Key key;

	public Door(Shape boundingBox, Vector2f velocity, Key key) {
		super(boundingBox, velocity);
		this.key = key;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		//Do nothing, door state is handled in collide method
	}

	@Override
	public void move(float x, float y) {
		//Do nothing, the door shouldn't move
	}

	@Override
	public void rotate(float degrees) {
		//Do nothing, the door shouldn't rotate
	}

	@Override
	public void draw(Graphics g) {
		if(!open){
			g.setColor(Color.gray.darker());
			g.setLineWidth(3);
			g.draw(boundingBox);
			g.setColor(Color.gray);
			g.fill(boundingBox);
		}else{
			g.setColor(Color.gray);
			g.setLineWidth(3);
			g.draw(boundingBox);
			g.setColor(Color.gray.brighter());
			g.fill(boundingBox);
		}
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
		open = false;
	}
	
	public void open(){
		open = true;
	}

	public boolean isOpen(){
		return open;
	}

}
