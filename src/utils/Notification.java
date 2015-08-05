package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * This is a simple pop-up window that can contain 1, 2, or 0 buttons
 * Includes a title and body of text
 * Buton functionality is handled outside this class
 */
public class Notification {

	int x, y, width, height, lines, buttonGap;
	String title, text;
	Rectangle header, body;
	String[] textWords, headerWords;
	ArrayList<String> textComplete;
	Color bg, textColor;
	boolean showing = false;
	SimpleButton b1, b2;

	/**
	 * Constructor
	 * 
	 * @param x - The x coordinate of the top left of the window
	 * @param y - the y coordinate at the top left of the window
	 * @param width - the width of the window
	 * @param height - the height of the window
	 * @param bg - the background color of the window
	 * @param textColor - the color of the text
	 * @param b1 - the button in the bottom left
	 * @param b2 - the button in the bottom right
	 * @param title - the text to be displayed at the top
	 * @param text - the text to be displayed in the center
	 */
	public Notification(int x, int y, int width, int height, Color bg, Color textColor, SimpleButton b1, SimpleButton b2, int buttonGap, String title, String text) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonGap = buttonGap;
		this.title = title;
		this.text = text;
		this.b1 = b1;
		this.b2 = b2;
		this.bg = bg;
		this.textColor = textColor;

		headerWords = title.split(" ");
		textWords = text.split(" ");
		
		textComplete = new ArrayList<String>();

		header = new Rectangle(x, y, width, height/5);
		body = new Rectangle(x, y + header.getHeight(), width, height);
	}

	/**
	 * Draws the window to the screen, if the window should be displayed
	 * 
	 * @param g - Graphics context
	 */
	public void draw(Graphics g, TrueTypeFont font){
		if(showing){
			
			g.setFont(font);
			
			//splitting the body text into separate lines depending on the width of the indow
			text = "";
			int textWidth = 0;
			textComplete.clear();
			for(String s : textWords){
				if(textWidth + g.getFont().getWidth(s) < body.getWidth()){
					text += s + " ";
					textWidth += g.getFont().getWidth(text);
				}else{
					textComplete.add(text + s);
					text = "";
					textWidth = 0;
				}
			}
			textComplete.add(text);
			
			//truncating the title as necessary
			if(g.getFont().getWidth(title) > header.getWidth()){
				title = title.substring((int) (header.getWidth()/g.getFont().getWidth("X") - 3));
				title += "...";
			}
			
			//drawing the window
			g.setColor(Color.white);
			g.setLineWidth(4);
			g.draw(header);
			g.draw(body);
			bg.a = 0.95f;
			g.setColor(bg);
			g.fill(header);
			g.fill(body);
			
			//drawing the text
			g.setColor(textColor);
			
			g.drawString(title, header.getX() + 10, header.getY() + header.getHeight() * 1/2 - g.getFont().getLineHeight()/2);
			
			int counter = 0;
			for(String s : textComplete){	
				g.drawString(s, body.getX() + 10, (body.getY() + 10) + g.getFont().getLineHeight() * counter);
				counter++;
			}
			
			if(b1 != null){
				b1.move((int)(body.getX() + body.getWidth()/2 + 5), (int)(body.getY() + body.getHeight() - b1.getHeight() - buttonGap));
				b1.draw(g, bg, textColor);
			}
			if(b2 != null){
				b2.move((int)(body.getX() + body.getWidth()/2 + 5), (int)(body.getY() + body.getHeight() - b2.getHeight()) - 5);
				b2.draw(g, bg, textColor);
			}
		}
	}
	
	/**
	 * Instantly moves the window to new coordinates
	 * 
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void move(int x, int y){
		header.setLocation(x, y);
		body.setLocation(x , y + header.getHeight());
	}
	
	/**
	 * Checks if the cursor is hovering over the window
	 * 
	 * @param x - x coordinate of the mouse
	 * @param y - y coordinate of the mouse
	 * @return - true if the window is showing and the cursor is hovering over it
	 */
	public boolean hover(int x, int y){
		return (header.contains(x, y) || body.contains(x, y)) && showing;
	}

	public boolean isShowing(){
		return showing;
	}

	public void show(){
		showing = true;
		b1.reset();
		b2.reset();
	}

	public void hide(){
		showing = false;
		b1.reset();
		b2.reset();
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setBody(String s){
		text = s;
		textWords = text.split(" ");
	}
	
	public void setHeader(String s){
		title = s;
		headerWords = title.split(" ");
	}
}
