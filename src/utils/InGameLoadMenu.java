package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;

import utils.Notification;
import utils.SaverLoader;
import utils.SimpleButton;

/**
 * This class serves as a simple ui for the player to load a saved game
 */
public class InGameLoadMenu {

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonXGap, buttonYGap;

	Color textColor = Color.lightGray;
	Color background = Color.black;

	int mouseX, mouseY;

	StateBasedGame sbg;

	TrueTypeFont font;
	float fontSize = 24f;

	SimpleButton cancel;

	Notification warning;
	SimpleButton b1, b2;

	String path;

	RoundedRectangle body;

	boolean showing = false;

	public InGameLoadMenu(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.sbg = sbg;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);

		File folder = new File("savedGames/");
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);

		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		if(listOfFiles.length >= 6){
			File[] temp = new File[6];
			int counter = 0;
			for(int i = listOfFiles.length; i > listOfFiles.length - 6; i--, counter++){
				temp[counter] = listOfFiles[i-1];
			}
			listOfFiles = temp;
		}

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				buttons.add(new SimpleButton(0, 0, buttonWidth, buttonHeight, listOfFiles[i].getName()));
			} 
		}

		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		warning = new Notification(0, 0, gc.getWidth()/3, gc.getHeight()/3, background, textColor, b1, b2, buttonYGap, "Load Game", "Are you sure you want to load file: \n" + path);
	}

	public void draw(Graphics g){
		if(showing){
			Color c = Color.black;
			c.a = 0.95f;
			g.setColor(Color.black);
			g.setLineWidth(3);
			g.draw(body);
			g.setColor(c);
			g.fill(body);

			for(SimpleButton b : buttons){
				b.draw(g, background, textColor);
			}

			cancel.draw(g, background, textColor);

			g.drawString("Load Game", (int)body.getX() + buttonXOffset + buttonWidth - g.getFont().getWidth("Load Game"), (int)body.getY() + buttonYOffset);

			warning.draw(g);
		}
	}

	public void update(GameContainer gc, int x, int y) throws SlickException {
		body.setLocation(x + 20, y + 20);
		
		int xCounter = 0;
		int yCounter = 1;
		int buttonCounter = 0;

		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		mouseX = gc.getInput().getMouseX() + x;
		mouseY = gc.getInput().getMouseY() + y;

		for(xCounter = 0; xCounter <= 1; xCounter++){
			for(yCounter = 1; yCounter <= 3; yCounter++){
				if(buttonCounter < buttons.size()){
					buttons.get(buttonCounter).move((int)body.getX() + buttonXOffset - (xCounter * (buttonXGap + 100)), (int)body.getY() + buttonYOffset + (yCounter * buttonYGap));
					buttons.get(buttonCounter).hover(mouseX, mouseY);
				}
				buttonCounter++;
			}
		}
		cancel.move((int)body.getX() + buttonXOffset, (int)body.getY() + buttonYOffset + (yCounter * buttonYGap));
		cancel.hover(mouseX, mouseY);

		warning.move((int)body.getX() + gc.getWidth()/2 - warning.getWidth()/2, (int)body.getY() + gc.getHeight()/2 - warning.getHeight()/2);
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);
	}

	public void mouseReleased(int button, int x, int y){
		if(button == 0){			
			if(b2.hover(x, y) && warning.isShowing()){
				warning.hide();
			}
			if(b1.hover(x, y) && warning.isShowing()){
				for(SimpleButton b : buttons){
					b.reset();
					b1.reset();
					b2.reset();
				}
				SaverLoader.loadGame("savedGames/" + path, sbg);
			}
			for(SimpleButton b : buttons){
				if(b.hover(x, y) && !warning.isShowing()){
					path = b.getText();
					warning.setBody("Are you sure you want to load file: \n" + path);
					warning.show();
				}
			}
			if(cancel.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				cancel.reset();
				hide();
			}
		}
	}

	public void show(){
		showing = true;
	}

	public void hide(){
		showing = false;
	}

	public boolean isShowing(){
		return showing;
	}
}
