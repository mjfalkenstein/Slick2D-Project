package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * This class represents a series of left-facing spikes of a fixed width and height that kill the player if it touches them
 */
public class SpikesLeft extends Entity{
	
	ArrayList<Polygon> spikes;
	int spikeHeight = 50;
	int numSpikes;
	
	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Entity
	 * @param velocity - the initial velocity
	 */
	public SpikesLeft(Shape boundingBox, Vector2f velocity) {
		super(boundingBox, velocity);
		if(boundingBox.getHeight() % spikeHeight != 0){
			boundingBox = new Rectangle(boundingBox.getX(), boundingBox.getY(), spikeHeight, (boundingBox.getHeight() * spikeHeight) / spikeHeight);
		}
		setVelocity(0, 0);
		
		spikes = new ArrayList<Polygon>();
		
		numSpikes = (int) (boundingBox.getHeight() / spikeHeight);
		
		float[] points = new float[numSpikes * 6];
		float counter = 0;
		for(int i = 0; i < numSpikes * 6 - 5; i += 6){
			points[i+0] = boundingBox.getMaxX();
			points[i+1] = boundingBox.getY() + counter * spikeHeight;
			points[i+2] = boundingBox.getMaxX();
			points[i+3] = boundingBox.getY() + (counter + 1) * spikeHeight;
			points[i+4] = boundingBox.getX();
			points[i+5] = boundingBox.getY() + counter * spikeHeight + 0.5f * spikeHeight;
			counter++;
		}
		
		for(int i = 0; i < numSpikes * 6 - 5; i += 6){
			Polygon p = new Polygon();
			p.addPoint(points[i+0], points[i+1]);
			p.addPoint(points[i+2], points[i+3]);
			p.addPoint(points[i+4], points[i+5]);
			p.setAllowDuplicatePoints(true);
			p.setClosed(true);
			spikes.add(p);
		}
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
		g.setColor(Color.red.darker());
		g.setLineWidth(3);
		for(Shape s : spikes){
			g.fill(s);
		}
		g.setLineWidth(3);
		g.setColor(Color.red.darker().darker());
		for(Shape s : spikes){
			g.draw(s);
		}
	}

	@Override
	public void collide(Entity e, GameContainer gc) {
		if(e instanceof Player){
			for(Polygon p : spikes){
				if(p.intersects(e.getBoundingBox())){
					((Player) e).kill();
				}
			}
		}
	}

	@Override
	public void reset() {
		//Do nothing
	}

}
