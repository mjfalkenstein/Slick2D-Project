package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class is a simple sliding bar used for volume control
 * 
 * Actual functionality is handled outside this class
 */
public class SlideBar {
	
	int x, y, width, height;
	Color bg, hl;
	
	Rectangle body, highlight, bodyL, bodyR;
	
	/**
	 * Constructor 
	 * 
	 * @param x - x coordinate of the top left of the body of the slide bar
	 * @param y - y coordinate of the top left of the body of the slide bar
	 * @param width - width of the bar
	 * @param height - height of the bar
	 * @param bg - color of the 'unhighlighted' portion of the bar
	 * @param hl - color of the highlighted portion of the bar
	 */
	public SlideBar(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bg = Color.darkGray.darker();
		this.hl = Color.white;
		
		body = new Rectangle(x, y, width, height);
		highlight = new Rectangle(x, y, width/2, height);
		bodyL = new Rectangle(x, y, width/3, height);
		bodyR = new Rectangle(body.getX() + body.getWidth() * 2/3 + 1, y, width/3, height);
	}
	
	/**
	 * Draws the bar
	 * 
	 * @param g - graphics object
	 */
	public void draw(Graphics g){
		g.setColor(bg);
		g.fill(body);
		g.setColor(hl);
		g.fill(highlight);
	
		g.fill(bodyL, new GradientFill(bodyL.getX(), bodyL.getY(), bg, bodyL.getX() + bodyL.getWidth(), bodyL.getY(), Color.transparent));
		g.fill(bodyR, new GradientFill(bodyR.getX(), bodyR.getY(), Color.transparent, bodyR.getX() + bodyR.getWidth() + 1, bodyR.getY(), bg));
	}
	
	/**
	 * called on mouse click
	 * updates the highlighted portion of the bar appropriately
	 * 
	 * @param x - x coordinate of the mouse
	 * @param y - y coordinate of the mouse
	 */
	public boolean hover(int x, int y){
		if(body.contains(x, y)){
			highlight.setWidth(x - highlight.getX());
			return true;
		}
		return false;
	}
	
	/**
	 * Moves the slide bar
	 * 
	 * @param x - x coordinate to be moved to
	 * @param y - y coordinate to be moved to
	 */
	public void move(int x, int y){
		body.setLocation(x, y);
		highlight.setLocation(x, y);
		bodyL.setLocation(x, y);
		bodyR.setLocation(body.getX() + body.getWidth() * 2/3 + 1, y);
	}
	
	/**
	 * gets the width of the highlighted portion of the bar as a fraction of the width of the body
	 * 
	 * @return 0-1
	 */
	public float getValue(){
		return highlight.getWidth()/body.getWidth();
	}
	
	public void setValue(float f){
		highlight.setWidth(body.getWidth() * f);
	}

	public float getX() {
		return body.getX();
	}
	
	public float getY() {
		return body.getY();
	}

}
