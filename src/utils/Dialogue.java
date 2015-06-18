package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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

	public void advance(){
		if(index + 1 == speechBubbles.size()){
			reset();
		}else{
			index++;
			speechBubbles.get(index).show();
		}
	}

	public void reset(){
		for(SpeechBubble s : speechBubbles){
			s.hide();
		}
		index = 0;
		showing = false;
	}

	public void move(float x, float y){
		for(SpeechBubble s : speechBubbles){
			s.move(x, y);
		}
	}

	public void draw(Graphics g){
		speechBubbles.get(index).draw(g);
	}

	public void show(){
		speechBubbles.get(index).show();
		showing = true;
	}

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
