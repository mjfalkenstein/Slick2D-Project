package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class PauseMenu {

	int x, y, width, height;
	SimpleButton mainMenu, newGame, saveGame, loadGame, quit;

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

	Rectangle body;

	Color bg, textColor;
	
	boolean showing = false;

	public PauseMenu(GameContainer gc, Color bg, Color textColor){
		this.bg = bg;
		this.textColor = textColor;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth()/2 - buttonWidth/2);
		buttonYOffset = (int)(gc.getHeight()/2 - buttonWidth * 3/2);
		buttonYGap = (int)(buttonHeight * 1.2);

		int counter = 0;
		mainMenu = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Main Menu");
		counter++;
		newGame = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "New Game");
		counter++;
		saveGame = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Save Game");
		counter++;
		loadGame = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Load Game");
		counter++;
		quit = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Quit");
		
		body = new Rectangle(100, 100, 100, 100);
		x = (int) body.getX();
		y = (int) body.getY();
		width = (int) body.getWidth();
		height = (int) body.getHeight();
	}

	public void draw(Graphics g){
		if(showing){
			g.setColor(bg);
			g.setLineWidth(4);
			g.draw(body);
			bg.a = 0.95f;
			g.setColor(bg);
			g.fill(body);

			mainMenu.draw(g, bg, textColor);
			newGame.draw(g, bg, textColor);
			saveGame.draw(g, bg, textColor);
			loadGame.draw(g, bg, textColor);
			quit.draw(g, bg, textColor);
		}
	}
	
	public void move(int x, int y){
		body.setLocation(100, 100);
		
		int counter = 0; 
		mainMenu.move(buttonXOffset, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		newGame.move(buttonXOffset, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		saveGame.move(buttonXOffset, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		loadGame.move(buttonXOffset, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		quit.move(buttonXOffset, y + buttonYOffset + (counter * buttonYGap));
	}
	
	public String hover(int x, int y){
		String s = "";
		if(mainMenu.hover(x, y)){
			s =  "mainMenu";
		}if(newGame.hover(x, y)){
			s = "newGame";
		}if(saveGame.hover(x, y)){
			s =  "saveGame";
		}if(loadGame.hover(x, y)){
			s =  "loadGame";
		}if(quit.hover(x, y)){
			s =  "quit";
		}if(body.contains(x, y)){
			s =  "body";
		}
		return s;
	}
	
	public void show(){
		showing = true;
	}
	
	public void hide(){
		showing = false;
	}
	
	public int getWidth(){
		return (int) body.getWidth();
	}
	
	public int getHeight(){
		return (int) body.getHeight();
	}

}
