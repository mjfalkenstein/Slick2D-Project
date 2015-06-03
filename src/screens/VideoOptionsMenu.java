package screens;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import driver.Driver;
import utils.SimpleButton;

/**
 * This is a simple options menu with buttons that lead to other menus with the actual options
 */
public class VideoOptionsMenu extends BasicGameState{
	
	MainMenu mainMenu;
	
	SimpleButton toggleFPS, toggleFullscreen, resolutions, back;
	boolean showFPS = false;
	boolean fullscreen = true;
	
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;
	
	Color background = Color.black;
	Color textColor = Color.lightGray;
	
	int mouseX, mouseY;
	
	GameContainer gc;
	StateBasedGame sbg;
	
	TrueTypeFont font;
	float fontSize = 24f;
	
	/**
	 * Constructor
	 * 
	 * @param mainMenu - an instance of the Main Menu
	 */
	public VideoOptionsMenu(MainMenu mainMenu){
		this.mainMenu = mainMenu;
	}

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.sbg = sbg;
		this.gc = gc;
		
		//loading the font
		try{
			InputStream is = ResourceLoader.getResourceAsStream("Squared Display.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
			awtFont = awtFont.deriveFont(fontSize);
			font = new TrueTypeFont(awtFont, false);
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		
		toggleFPS = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Toggle FPS");
		toggleFullscreen = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Toggle Fullscreen");
		resolutions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Set Resolution");
		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");
		
		buttons.add(toggleFPS);
		buttons.add(toggleFullscreen);
		buttons.add(resolutions);
		buttons.add(back);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		mainMenu.backgroundAnimation.draw(g);
		
		g.setFont(font);
		
		for(SimpleButton b : buttons){
			b.draw(g, background, textColor);
		}
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int counter = 1;
		
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		
		for(SimpleButton b : buttons){
			b.move(buttonXOffset, buttonYOffset + (counter * buttonYGap));
			counter++;
			b.hover(mouseX, mouseY);
		}
	}
	
	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void mousePressed(int button, int x, int y){
		if(button == 0){
			if(back.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				sbg.enterState(Driver.OPTIONS_MENU);
			}else if(toggleFPS.hover(x, y)){
				showFPS = !showFPS;
				gc.setShowFPS(showFPS);
			}else if(toggleFullscreen.hover(x, y)){
				fullscreen = !fullscreen;
				try {
					gc.setFullscreen(fullscreen);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return 2;
	}

}
