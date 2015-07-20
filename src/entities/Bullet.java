package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * This class serves as the projectile for the TurretEnemy class
 */
public class Bullet extends Entity{

	static public float maxSpeed = 15;
	float lifeTime = 5000;
	boolean visible = true;
	Vector2f direction;

	public Bullet(Shape boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
		direction = (Vector2f) velocity.normalise();
	}

	@Override
	public void update(GameContainer gc, int delta) {
		lifeTime -= delta;

		if(lifeTime < 0){
			remove();
		}
		y += direction.getY() * maxSpeed * delta / gc.getFPS();
		x += direction.getX() * maxSpeed * delta / gc.getFPS();

		boundingBox.setLocation(x, y);
	}

	@Override
	public void move(float x, float y) {
		boundingBox.setLocation(x, y);
	}

	@Override
	public void rotate(float degrees) {
		//Do nothing, this should never be used
	}

	@Override
	public void draw(Graphics g) {
		if(visible){
			g.setColor(Color.gray);
			g.fill(boundingBox);
			g.setColor(Color.gray.darker());
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

			//Player
			if(e instanceof Player){
				((Player) e).kill();
				remove();
			}
		}
	}

	@Override
	public void reset() {
		move(startingX, startingY);
		visible = true;
	}

	/**
	 * Remove the bullet from the screen
	 */
	public void remove(){
		move(-100, -100);
		setVelocity(0, 0);
		visible = false;
	}
}
