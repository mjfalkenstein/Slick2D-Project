package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import entities.Entity;

public class Player extends Entity {

	float maxSpeed = 15;
	float gravity = 2;

	public Player(Rectangle boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
	}

	public void update(GameContainer gc, int delta) {
		setVelocity(velocity.getX(), velocity.getY() + gravity);
		if(gc.getInput().isKeyDown(Input.KEY_A) && velocity.getX() > -maxSpeed){
			setVelocity(velocity.getX() - 1, velocity.getY());
		}else if(gc.getInput().isKeyDown(Input.KEY_D) && velocity.getX() < maxSpeed){
			setVelocity(velocity.getX() + 1, velocity.getY());
		}
		
		if(velocity.getX() > 0 && !gc.getInput().isKeyDown(Input.KEY_D)){
			velocity.setX(velocity.getX() - 1);
		}
		if(velocity.getX() < 0 && !gc.getInput().isKeyDown(Input.KEY_A)){
			velocity.setX(velocity.getX() + 1);
		}

		x += velocity.getX() * delta / gc.getFPS();
		y += velocity.getY() * delta / gc.getFPS();

		boundingBox.setLocation(x, y);
	}

	public void move(float x, float y) {
		this.x = x;
		this.y = y;

		boundingBox.setLocation(x, y);
	}

	public void rotate(float degrees){
		boundingBox.transform(Transform.createRotateTransform(degrees));
	}

	public void draw(Graphics g) {
		g.setColor(Color.red.darker());
		g.draw(boundingBox);
		g.setColor(Color.red);
		g.fill(boundingBox);
	}

	public void jump(){
		velocity.setY(-30.0f);
	}

	public boolean collide(Entity e, GameContainer gc){
		if(boundingBox.intersects(e.getBoundingBox())){
			float yOverlap = (boundingBox.getHeight()/2 + e.getBoundingBox().getHeight()/2) - Math.abs((boundingBox.getCenterY() - e.getBoundingBox().getCenterY()));
			float xOverlap = (boundingBox.getWidth()/2 + e.getBoundingBox().getWidth()/2 - Math.abs((boundingBox.getCenterX() - e.getBoundingBox().getCenterX())));
			
			//PLATFORMS
			if(e instanceof Platform){
				
				//whichever overlap is smaller indicates the axis on which the collision occurred
				//collision occurred on the Y axis
				if(yOverlap < xOverlap){
					//player is above the Platform
					if(boundingBox.getCenterY() < e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getY() - height;
						return true;
					//player is below the Platform
					}if(boundingBox.getCenterY() > e.getBoundingBox().getCenterY()){
						setVelocity(velocity.getX(), 0);
						y = e.getBoundingBox().getMaxY();
						return true;
					}

				//collision occurred on the X axis
				}if(yOverlap >= xOverlap){
					//player is to the left of the Platform
					if(boundingBox.getCenterX() < e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getX() - boundingBox.getWidth();
						return true;

					//player is to the right of the Platform
					}if(boundingBox.getCenterX() > e.getBoundingBox().getCenterX()){
						setVelocity(0, velocity.getY());
						x = e.getBoundingBox().getMaxX();
						return true;
					}
				}
			}
		}
		return false;
	}
}
