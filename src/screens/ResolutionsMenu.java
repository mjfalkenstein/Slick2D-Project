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
public class ResolutionsMenu extends BasicGameState{

	MainMenu mainMenu;

	SimpleButton res1, res2, res3, res4, res5, res6, back;
	boolean showFPS = false;
	boolean fullscreen = true;

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap, buttonXGap;

	Color background = Color.black;
	Color textColor = Color.lightGray;

	int mouseX, mouseY;

	GameContainer gc;
	StateBasedGame sbg;

	TrueTypeFont font;
	float fontSize = 24f;

	boolean addNewRes;

	/**
	 * Constructor
	 * 
	 * @param mainMenu - an instance of the Main Menu
	 */
	public ResolutionsMenu(MainMenu mainMenu){
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
		buttonXGap = (int)(gc.getWidth() * 0.2);

		res1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "1388 x 768");
		res2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "1920 x 1080");
		res3 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "1280 x 800");
		res4 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "1440 x 900");
		res5 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "1280 x 1024");

		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		buttons.add(res1);
		buttons.add(res2);
		buttons.add(res3);
		buttons.add(res4);
		buttons.add(res5);

		for(SimpleButton b : buttons){
			if(!(gc.getScreenWidth() + " x " + gc.getScreenHeight()).equals(b.getText())){
				addNewRes = true;
			}
		}
		if(addNewRes){
			res6 = new SimpleButton(0, 0, buttonWidth, buttonHeight, (gc.getScreenWidth() + " x " + gc.getScreenHeight()));
			buttons.add(res6);
		}
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
		
		back.draw(g, background, textColor);
		
		g.drawString("Select Resolution", buttonXOffset + buttonWidth - g.getFont().getWidth("Select Resolution"), buttonYOffset);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int xCounter = 0;
		int yCounter = 1;
		int buttonCounter = 0;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();

		for(xCounter = 0; xCounter <= 1; xCounter++){
			for(yCounter = 1; yCounter <= 3; yCounter++){
				if(buttonCounter < buttons.size()){
					buttons.get(buttonCounter).move(buttonXOffset - (xCounter * buttonXGap), buttonYOffset + (yCounter * buttonYGap));
					buttons.get(buttonCounter).hover(mouseX, mouseY);
				}
				buttonCounter++;
			}
		}

		back.move(buttonXOffset, buttonYOffset + (yCounter * buttonYGap));
		back.hover(mouseX, mouseY);
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
				back.reset();
				sbg.enterState(Driver.VIDEO_OPTIONS_MENU);
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return 3;
	}

}
