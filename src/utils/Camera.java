package utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Camera {

	int dX, dY;
	int levelWidth, levelHeight;
	Rectangle view;

	/**
	 * Constructor
	 * 
	 * @param gc - GameContainer
	 * @param levelWidth - the maximum width of the level in pixels
	 * @param levelHeight - the maximum height of the level in pixels
	 */
	public Camera(GameContainer gc, int levelWidth, int levelHeight) {
		dX = 0;
		dY = 0;
		view = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
	}

	/**
	 * Instructs the camera to follow the given entity
	 * 
	 * @param gc - GameContainer
	 * @param g - the Graphics context
	 * @param e - the Entity to follow
	 */
	public void translate(GameContainer gc, Graphics g, Entity e) {

		//Checking that the player is within bounds of the camera horizontally
		if(e.getCenterX()-gc.getWidth()/2 < 0){
			dX = 0;
		}else if(e.getCenterX()-gc.getWidth()/2 > levelWidth){
			dX = -levelWidth+gc.getWidth();
		}else{
			dX = (int)-e.getCenterX()+gc.getWidth()/2;
		}

		//checking that the player is within bounds of the camera vertically
		if(e.getCenterY()-gc.getHeight()/2 < 0){
			dY = 0;
		}else if(e.getCenterY()+gc.getHeight()/2 > levelHeight){
			dY = -levelHeight+gc.getHeight();
		}else{
			dY = (int)-e.getCenterY()+gc.getHeight()/2;
		}
		
		//Moving the camera appropriately
		g.translate(dX, dY);
		view.setX(-dX);
		view.setY(-dY);

	}
}