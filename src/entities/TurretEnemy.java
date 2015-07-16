package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class TurretEnemy extends Entity{
	
	Shape barrel, originalBarrel;
	float barrelWidth = 25;
	float barrelLength = 60;
	float angle = 0;
	Entity target;
	float targetDistance = 400;
	float cooldown = 1000;
	ArrayList<Bullet> bullets;
	
	public TurretEnemy(Shape boundingBox, Vector2f velocity, Entity target){
		super(boundingBox, velocity);
		barrel = new Rectangle(boundingBox.getCenterX() - barrelWidth/2, boundingBox.getCenterY() - barrelLength, barrelWidth, barrelLength);
		originalBarrel = new Rectangle(boundingBox.getCenterX() - barrelWidth/2, boundingBox.getCenterY() - barrelLength, barrelWidth, barrelLength);
		this.target = target;	
		bullets = new ArrayList<Bullet>();
	}

	@Override
	public void update(GameContainer gc, int delta) {
		Line line = new Line(target.getCenterX() - boundingBox.getCenterX(), target.getCenterY() - boundingBox.getCenterY());
		cooldown -= delta;
		for(Bullet b : bullets){
			if(!b.visible){
				bullets.remove(b);
			}
			b.update(gc, delta);
		}
		if(line.length() < targetDistance){
			angle = (float) Math.atan2(target.getCenterY() - boundingBox.getCenterY(), target.getCenterX() - boundingBox.getCenterX());
			angle += Math.PI/2;
			barrel = originalBarrel.transform(Transform.createRotateTransform(angle, boundingBox.getCenterX(), boundingBox.getCenterY()));
			if(cooldown < 0){
				attack();
				cooldown = 1000;
			}
		}
	}

	@Override
	public void move(float x, float y) {
		//Do nothing, the Turret is stationary
	}

	@Override
	public void rotate(float degrees) {
		boundingBox.transform(Transform.createRotateTransform(degrees));
		barrel.transform(Transform.createRotateTransform(degrees + 90));
	}

	@Override
	public void draw(Graphics g) {
		for(Bullet b : bullets){
			b.draw(g);
		}
		g.setColor(Color.gray);
		g.fill(boundingBox);
		g.fill(barrel);
		g.setColor(Color.gray.darker());
		g.setLineWidth(3);
		g.draw(boundingBox);
		g.draw(barrel);
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
			}
		}
	}

	@Override
	public void reset() {
		barrel = new Rectangle(boundingBox.getCenterX() - barrelWidth/2, boundingBox.getCenterY(), barrelWidth, barrelLength);
	}
	
	/**
	 * Create a new Bullet object with an initial velocity towards the target
	 */
	private void attack(){
		Line line = new Line(target.getCenterX() - barrel.getCenterX(), target.getCenterY() - barrel.getCenterY());
		bullets.add(new Bullet(new Circle(barrel.getCenterX(), barrel.getCenterY(), 4), new Vector2f(line.getDX() * Bullet.maxSpeed, line.getDY() * Bullet.maxSpeed)));
	}
}
