package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Notification {

	int x, y, width, height, lines;
	String title, text;
	Rectangle header, body;
	String[] textWords, headerWords;
	ArrayList<String> textComplete;
	Color bg, textColor;
	boolean showing = false;
	SimpleButton b1, b2;

	public Notification(int x, int y, int width, int height, Color bg, Color textColor, SimpleButton b1, SimpleButton b2, String title, String text) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public void draw(Graphics g){
		if(showing){
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
			
			if(g.getFont().getWidth(title) > header.getWidth()){
				title = title.substring((int) (header.getWidth()/g.getFont().getWidth("X") - 3));
				title += "...";
			}
			
			g.setColor(bg);
			g.setLineWidth(4);
			g.draw(header);
			g.draw(body);
			bg.a = 0.95f;
			g.setColor(bg);
			g.fill(header);
			g.fill(body);
			
			g.setColor(textColor);
			
			g.drawString(title, header.getX() + 10, header.getY() + header.getHeight() * 1/2 - g.getFont().getLineHeight()/2);
			
			int counter = 0;
			for(String s : textComplete){	
				g.drawString(s, body.getX() + 10, (body.getY() + 10) + g.getFont().getLineHeight() * counter);
				counter++;
				System.out.println(s);
			}
			
			if(b1 != null){
				b1.move((int)(body.getX() + 10), (int)(body.getY() + body.getHeight() - b1.getHeight()));
				b1.draw(g, bg, textColor);
			}
			if(b2 != null){
				b2.move((int)(body.getX() + body.getWidth()/2 + 10), (int)(body.getY() + body.getHeight() - b2.getHeight()));
				b2.draw(g, bg, textColor);
			}
		}
	}
	
	public void move(int x, int y){
		header.setLocation(x, y);
		body.setLocation(x , y + header.getHeight());
	}
	
	public boolean hover(int x, int y){
		return header.contains(x, y) || body.contains(x, y);
	}

	public boolean isShowing(){
		return showing;
	}

	public void show(){
		showing = true;
	}

	public void hide(){
		showing = false;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
