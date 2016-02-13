package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import entities.Player;

/**
 * This class represents a checkpoint that the player must cross in order for the game to save
 */
public class Checkpoint {

	Shape boundingBox;
	Player player;
	boolean saved = false;
	int levelID;
	Level level;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - the area that the player must enter for the game to be saved
	 * @param target - the player
	 */
	public Checkpoint(Level level, Shape boundingBox, Player player, int levelID){
		this.level = level;
		this.boundingBox = boundingBox;
		this.player = player;
		this.levelID = levelID;
	}

	/**
	 * Draws the bounds of the checkpoint area, for debugging purposes only
	 * 
	 * @param g - Graphics context
	 */
	public void draw(Graphics g){
		if(saved){
			g.setColor(Color.green.darker());
			g.setLineWidth(3);
			g.draw(boundingBox);
		}else{
			g.setColor(Color.red.darker());
			g.setLineWidth(3);
			g.draw(boundingBox);
		}
	}
	
	public void deactivate(){
		saved = true;
	}
	
	public void reset(){
		saved = false;
	}
	
	public float getX(){
		return boundingBox.getX();
	}
	
	public float getY(){
		return boundingBox.getY();
	}

	public float getCenterX() {
		return boundingBox.getCenterX();
	}
	
	public float getCenterY(){
		return boundingBox.getCenterY();
	}

	public float getMaxY() {
		return boundingBox.getMaxY();
	}
	
	public float getMaxX() {
		return boundingBox.getMaxX();
	}
}
