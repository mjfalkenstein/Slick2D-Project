package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * This class represents a single speech bubble in a dialogue from an NPC
 */
public class SpeechBubble {

	float x, y;
	float width = 200;
	float height = 75;
	String text;
	RoundedRectangle body, hint;
	Color bg, textColor;
	boolean showing = false;
	String[] textWords;
	ArrayList<String> lines;
	
	static int maxLength = 150;

	/**
	 * Constructor
	 * 
	 * @param x - x coordinate of the speech bubble
	 * @param y - y coordinate of the speech bubble
	 * @param text - text in the speech bubble
	 * @param bg - background color
	 * @param textColor - text color
	 */
	public SpeechBubble(float x, float y, String text, Color bg, Color textColor){
		this.x = x;
		this.y = y;
		this.text = text;
		this.bg = bg;
		this.textColor = textColor;
		body = new RoundedRectangle(x, y, width, height, height/5);
		hint = new RoundedRectangle(body.getX() + body.getWidth() * 3/5, body.getMaxY(), body.getWidth() * 2/5, 30, 10);
		textWords = text.split(" ");
		lines = new ArrayList<String>();
	}

	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public void draw(Graphics g){
		if(showing){
			
			//splitting the body text into separate lines depending on the width of the window
			text = "";
			int textWidth = 0;
			lines.clear();
			for(String s : textWords){
				if(textWidth + g.getFont().getWidth(s) < body.getWidth()){
					text += s + " ";
					textWidth += g.getFont().getWidth(text);
				}else{
					lines.add(text + s);
					text = "";
					textWidth = 0;
				}
			}
			lines.add(text);

			g.setColor(bg.darker());
			g.draw(body);
			g.draw(hint);
			bg.a = 0.9f;
			g.setColor(bg);
			g.fill(body);
			g.fill(hint);
			g.setColor(textColor);

			int counter = 0;
			for(String s : lines){	
				//if((body.getY() + 10) + g.getFont().getLineHeight() * counter + g.getFont().getHeight(s) < body.getMaxY()){
					g.drawString(s, body.getX() + 10, (body.getY() + 10) + g.getFont().getLineHeight() * counter);
				//}
				counter++;
			}

			g.drawString("L-Shift", hint.getX() + hint.getWidth()/2 - g.getFont().getWidth("L-Shift")/2, hint.getY() + hint.getHeight()/2 - g.getFont().getHeight("L-Shift")/2 - 3);

		}
	}

	/**
	 * Instantly moves the entity to new coordinates (x, y)
	 * 
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void move(float x, float y){
		body.setLocation(x, y);
		hint.setLocation(body.getX() + body.getWidth() * 3/5, body.getMaxY());
	}

	/**
	 * shows the bubble to the screen
	 */
	public void show(){
		showing = true;
	}

	/**
	 * removes the bubble from the screen
	 */
	public void hide(){
		showing = false;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return body.getHeight() + hint.getHeight();
	}
}
