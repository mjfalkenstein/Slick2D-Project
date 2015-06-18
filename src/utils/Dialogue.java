package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * This class is a wrapper class to handle several speech bubbles at once
 */
public class Dialogue {

	float x, y;
	float width, height;
	String text, current;
	Color bg, textColor;
	int index = 0;
	ArrayList<SpeechBubble> speechBubbles;
	ArrayList<String> splitText;
	boolean showing = false;
	String[] textWords;
	ArrayList<String> lines;

	/**
	 * Constructor
	 * 
	 * @param x - x coordinate of the speech bubbles 
	 * @param y - y coordinate of the speech bubbles
	 * @param text - text in the speech bubbles
	 * @param bg - background color
	 * @param textColor - text color
	 */
	public Dialogue(float x, float y, String text, Color bg, Color textColor){
		this.x = x;
		this.y = y;
		this.text = text;
		this.bg = bg;
		this.textColor = textColor;
		speechBubbles = new ArrayList<SpeechBubble>();

		//splitting the given text into individual speech bubbles
		textWords = text.split(" ");
		lines = new ArrayList<String>();

		text = "";
		int textWidth = 0;
		lines.clear();
		for(String s : textWords){
			if(s.length() + textWidth < SpeechBubble.maxLength){
				text += s + " ";
				textWidth += text.length();
			}else{
				lines.add(text + s);
				text = "";
				textWidth = 0;
			}
		}
		lines.add(text);

		for(String s : lines){
			s = s.trim();
			if(s.length() > 0){
				speechBubbles.add(new SpeechBubble(x, y, s, bg, textColor));
			}
		}

		width = speechBubbles.get(0).getWidth();
		height = speechBubbles.get(0).getHeight();
	}

	/**
	 * Move on to the next speech bubble
	 */
	public void advance(){
		if(index + 1 == speechBubbles.size()){
			reset();
		}else{
			index++;
			speechBubbles.get(index).show();
		}
	}

	/**
	 * Reset to the original state of the dialogue
	 */
	public void reset(){
		for(SpeechBubble s : speechBubbles){
			s.hide();
		}
		index = 0;
		showing = false;
	}

	/**
	 * Instantly moves the entity to new coordinates (x, y)
	 * 
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void move(float x, float y){
		for(SpeechBubble s : speechBubbles){
			s.move(x, y);
		}
	}
	
	/**
	 * Draws the entity to the given Graphics context
	 * 
	 * @param g - Graphics
	 */
	public void draw(Graphics g){
		speechBubbles.get(index).draw(g);
	}

	/**
	 * Draws the dialogue to the screen
	 */
	public void show(){
		speechBubbles.get(index).show();
		showing = true;
	}

	/**
	 * Remove the dialogue from the screen
	 */
	public void hide(){
		reset();
		showing = false;
	}

	public boolean showing(){
		return showing;
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
		return speechBubbles.get(0).getHeight();
	}
}
