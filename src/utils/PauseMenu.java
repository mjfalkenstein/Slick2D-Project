package utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * A simple pause menu that contains the necessary buttons for minimal functionality
 */
public class PauseMenu {

	int x, y, width, height;
	SimpleButton mainMenu, newGame, loadGame, options, quit;

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

	Rectangle body;

	Color bg, textColor;
	
	Graphics g;
	
	boolean showing = false;

	/**
	 * Constructor
	 * 
	 * @param gc - the GameContainer
	 * @param g - the graphics context in which this will be drawn
	 * @param bg - background color
	 * @param textColor - text color
	 */
	public PauseMenu(GameContainer gc, Graphics g, Color bg, Color textColor){
		this.bg = bg;
		this.textColor = textColor;
		this.g = g;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth()/2 - buttonWidth/2);
		buttonYOffset = (int)(gc.getHeight()/2 - buttonWidth * 3/2);
		buttonYGap = (int)(buttonHeight * 1.2);

		int counter = 0;
		mainMenu = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Main Menu");
		counter++;
		newGame = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "New Game");;
		counter++;
		loadGame = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Load Game");
		counter++;
		options = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Options");
		counter++;
		quit = new SimpleButton(buttonXOffset, y + buttonYOffset + (counter * buttonYGap), buttonWidth, buttonHeight, "Quit");
		
		body = new Rectangle(mainMenu.getX() - 10, mainMenu.getY() - g.getFont().getHeight("Paused") -  20, mainMenu.getWidth() + 20, quit.getMaxY() - mainMenu.getY() + g.getFont().getHeight("Paused") + 30);
		x = (int) body.getX();
		y = (int) body.getY();
		width = (int) body.getWidth();
		height = (int) body.getHeight();
	}

	/**
	 * draws the pause menu to the screen
	 * 
	 * @param g - the Graphics context
	 */
	public void draw(Graphics g){
		if(showing){
			g.setColor(bg);
			g.setLineWidth(4);
			g.draw(body);
			bg.a = 0.95f;
			g.setColor(bg);
			g.fill(body);
			
			g.setColor(textColor);
			g.drawString("Paused", mainMenu.getX() + mainMenu.getWidth()/2 - g.getFont().getWidth("Paused")/2, mainMenu.getY() - g.getFont().getHeight("Paused") - 10);

			mainMenu.draw(g, bg, textColor);
			newGame.draw(g, bg, textColor);
			loadGame.draw(g, bg, textColor);
			options.draw(g, bg, textColor);
			quit.draw(g, bg, textColor);
		}
	}
	
	/**
	 * moves the pause menu to the given x and y
	 * 
	 * @param x - x coordinate of the pause menu
	 * @param y - y coordinate of the pause menu
	 */
	public void move(int x, int y){
		int counter = 0; 
		mainMenu.move(x, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		newGame.move(x, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		loadGame.move(x, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		options.move(x, y + buttonYOffset + (counter * buttonYGap));
		counter++;
		quit.move(x, y + buttonYOffset + (counter * buttonYGap));
		
		body.setLocation(mainMenu.getX() - 10, mainMenu.getY() - g.getFont().getHeight("Paused") - 20);
	}
	
	/**
	 * detects which part, if any, the mouse is hovering over
	 * 
	 * @param x - x coordinate of the mouse
	 * @param y - y coordinate of the mouse
	 * 
	 * @return - string indicative of where the moues is located
	 */
	public String hover(int x, int y){
		String s = "";
		if(body.contains(x, y)){
			s = "body";
		}if(mainMenu.hover(x, y)){
			s =  "mainMenu";
		}if(newGame.hover(x, y)){
			s = "newGame";
		}if(loadGame.hover(x, y)){
			s =  "loadGame";
		}if(options.hover(x, y)){
			s =  "options";
		}if(quit.hover(x, y)){
			s =  "quit";
		}
		return s;
	}
	
	/**
	 * Instructs the menu to be drawn
	 */
	public void show(){
		mainMenu.reset();
		newGame.reset();
		loadGame.reset();
		options.reset();
		quit.reset();
		showing = true;
	}
	
	/**
	 * Instructs the menu to be hidden
	 */
	public void hide(){
		mainMenu.reset();
		newGame.reset();
		loadGame.reset();
		options.reset();
		quit.reset();
		showing = false;
	}
	
	/**
	 * Resets the menu and all contained buttons
	 */
	public void reset(){
		mainMenu.reset();
		newGame.reset();
		loadGame.reset();
		options.reset();
		quit.reset();
	}
	
	public int getWidth(){
		return (int) body.getWidth();
	}
	
	public int getHeight(){
		return (int) body.getHeight();
	}

}
