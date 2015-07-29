package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class represents a simple button on the screen with 
 * a simple highlight animation when the cursor is hovering over it
 * 
 * button functionality is handled outside this class
 */
public class SimpleButton {
	
	//The text to be displayed on the button
	private String text;
	
	//The various rectangles that make up the button
	private Rectangle body, bodyL, bodyR, highlight, top, bottom;
	
	/**
	 * Constructor
	 * 
	 * @param x - the x position of the button
	 * @param y - the y position of the button
	 * @param width - the width of the button
	 * @param height - the height of the button
	 * @param text - the text in the button
	 */
	public SimpleButton(int x, int y, int width, int height, String text) {
		this.text = text;
		body      = new Rectangle(x, y, width, height);
		bodyL     = new Rectangle(x, y, width/5, height);
		bodyR     = new Rectangle(x + 4 * width/5, y, width/5 + 1, height);
		top       = new Rectangle(x, y, width, height/10);
		bottom    = new Rectangle(x, y + 9 * body.getHeight()/10, width, height/10);
		highlight = new Rectangle(x + body.getWidth()/2, y, 0, height);
	}
	
	/**
	 * This function is used to draw everything that needs to be drawn, called every frame
	 * 
	 * @param g - the graphics object, required to draw
	 * @param background - the color of the background behind the button
	 * @param textColor - the color of the text
	 */
	public void draw(Graphics g, Color background, Color textColor){
		if(highlight.getX() < body.getX()){
			highlight.setX(body.getX());
		}
		if(highlight.getWidth() >= body.getWidth()-1){
			highlight.setWidth(body.getWidth());
		}else if(highlight.getWidth() < 0){
			highlight.setWidth(0);
		}
		
		g.setColor(Color.white);
		g.fill(highlight);
		g.fill(top);
		g.fill(bottom);
		
		g.fill(bodyL, new GradientFill(bodyL.getX(), bodyL.getY(), background, bodyL.getX() + bodyL.getWidth(), bodyL.getY(), Color.transparent));
		g.fill(bodyR, new GradientFill(bodyR.getX(), bodyR.getY(), Color.transparent, bodyR.getX() + bodyR.getWidth(), bodyR.getY(), background));

		g.setColor(textColor);
		g.drawString(text, body.getX() + body.getWidth()/2 - g.getFont().getWidth(text)/2, body.getY() + body.getHeight()/2 - g.getFont().getHeight(text)/2);
	}
	
	/**
	 * Determines whether or not the given coordinates are on the button
	 * If they are, play the highlight animation
	 * 
	 * @param x - x-coordinate of the mouse
	 * @param y - y-coordinate of the mouse
	 * 
	 * @return - whether or not (x,y) is contained in the button's space
	 */
	public boolean hover(int x, int y){
		if(body.contains(x,y)){
			if(highlight.getWidth() < body.getWidth() - 2){
				highlight.setLocation(body.getX(), highlight.getY());
				highlight.setWidth(body.getWidth());
			}
			return true;
		}
		if(highlight.getWidth() > 0){
			highlight.setLocation(highlight.getX() + 2, highlight.getY());
			highlight.setWidth(highlight.getWidth() - 4);
		}
		return false;
	}

	/**
	 * Moves the entire button to new coordinates
	 * 
	 * @param x - new x-coordinate
	 * @param y - new y-coordinate
	 */
	public void move(int x, int y){
		body.setLocation(x, y);
		bodyL.setLocation(x, y);
		bodyR.setLocation(body.getX() + body.getWidth() * 4/5, y);
		top.setLocation(x, y);
		bottom.setLocation(x, y + 9 * body.getHeight()/10);
		if(highlight.getWidth() <= 0)
			highlight.setLocation(body.getX() + body.getWidth()/2, y);
	}
	
	/**
	 * Called upon leaving a screen to reset the highlight animation
	 */
	public void reset(){
		highlight.setWidth(0);
	}

	/**
	 * Accessor
	 * 
	 * @return - the text in the button
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public float getHeight(){
		return body.getHeight();
	}
	
	public float getWidth(){
		return body.getWidth();
	}
	
	public float getX(){
		return body.getX();
	}
	
	public float getY(){
		return body.getY();
	}
	
	public float getMaxX(){
		return body.getMaxX();
	}
	
	public float getMaxY(){
		return body.getMaxY();
	}
}