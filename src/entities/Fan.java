package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

/**
 * This class represents a fan that pushes the player around on the screen
 */
public class Fan extends Entity{

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	float force, range;
	int direction;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Entity
	 * @param velocity - the initial velocity
	 */
	public Fan(Shape boundingBox, Vector2f velocity, float force, float range, int direction) {
		super(boundingBox, velocity);
		this.force = force;
		this.range = range;
		this.direction = direction;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		//Do nothing
	}

	@Override
	public void move(float x, float y) {
		//Do nothing
	}

	@Override
	public void rotate(float degrees) {
		//Do nothing
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.gray.darker());
		g.draw(boundingBox);
		g.setColor(Color.gray);
		g.setLineWidth(3);
		g.fill(boundingBox);
	}

	@Override
	public void collide(Entity e, GameContainer gc) {		//calculating the overlap of the Player and the entity on each of the axes
		//whichever overlap is smaller indicates the axis on which the collision occurred
		float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
		float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));

		float distance = new Line(e.getCenterX() - boundingBox.getCenterX(), e.getCenterY() - boundingBox.getCenterY()).length();
		float effectiveForce = (1 - (distance / range)) * force;
		
		if(e instanceof Player){
			switch(direction){
			case NORTH:
				if(xOverlap > 0){
					if(e.getCenterY() < boundingBox.getCenterY()){
						e.setVelocity(e.getVelocity().getX(), e.getVelocity().getY() - effectiveForce);
					}
				}
				break;
			case SOUTH: 
				if(xOverlap > 0){
					if(e.getCenterY() > boundingBox.getCenterY()){
						e.setVelocity(e.getVelocity().getX(), e.getVelocity().getY() + effectiveForce);
					}
				}
				break;
			case EAST:
				if(yOverlap > 0){
					if(e.getCenterX() > boundingBox.getCenterX()){
						e.setVelocity(e.getVelocity().getX() + effectiveForce, e.getVelocity().getY());
					}
				}
				break;
			case WEST:
				if(yOverlap > 0){
					if(e.getCenterX() > boundingBox.getCenterX()){
						e.setVelocity(e.getVelocity().getX() - effectiveForce, e.getVelocity().getY());
					}
				}
				break;
			}
			if(boundingBox.intersects(e.getBoundingBox())){
				((Player)e).kill();
			}
		}
	}

	@Override
	public void reset() {
		//Do nothing
	}

}
