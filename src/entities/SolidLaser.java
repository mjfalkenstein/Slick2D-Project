package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * A laser that is on permanently
 * Kills the player if he touches it
 */
public class SolidLaser extends Entity {

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	float laserWidth = 10;
	float laserLength;

	Rectangle laser;

	public SolidLaser(Shape boundingBox, Vector2f velocity, float laserLength, int direction) {
		super(boundingBox, velocity);
		this.laserLength = laserLength;

		boundingBox = new Rectangle(boundingBox.getX(), boundingBox.getY(), 30, 30);
		switch(direction){
		case NORTH:
			laser = new Rectangle(boundingBox.getCenterX(), boundingBox.getCenterY() - laserLength, laserWidth, laserLength);
			break;
		case SOUTH:
			laser = new Rectangle(boundingBox.getCenterX(), boundingBox.getCenterY(), laserWidth, laserLength);
			break;
		case EAST:
			laser = new Rectangle(boundingBox.getCenterX(), boundingBox.getCenterY(), laserLength, laserWidth);
			break;
		case WEST:
			laser = new Rectangle(boundingBox.getCenterX() - laserLength, boundingBox.getCenterY(), laserLength, laserWidth);
			break;
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {
		//Do nothing
	}

	@Override
	public void move(float x, float y) {
		//Do nothing, the laser should never move
	}

	@Override
	public void rotate(float degrees) {
		boundingBox.transform(Transform.createRotateTransform(degrees));
		laser.transform(Transform.createRotateTransform(degrees, boundingBox.getCenterX(), boundingBox.getCenterY()));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.pink);
		g.fill(laser);
		g.setColor(Color.pink.darker());
		g.setLineWidth(3);
		g.draw(laser);
		g.setColor(Color.gray);
		g.fill(boundingBox);
		g.setColor(Color.gray.darker());
		g.setLineWidth(3);
		g.draw(boundingBox);
	}

	@Override
	public void collide(Entity e, GameContainer gc) {
		if(e instanceof Player){
			if(laser.intersects(e.getBoundingBox())){
				((Player)e).kill();
			}
		}
	}

	@Override
	public void reset() {
		//Do nothing
	}

}
