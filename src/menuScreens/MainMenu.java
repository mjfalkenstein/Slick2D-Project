package menuScreens;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import driver.Driver;
import utils.BackgroundBarsAnimation;
import utils.SimpleButton;

/**
 * The Main Menu screen for the game
 */
public class MainMenu extends BasicGameState {

	//all buttons to be displayed on the Main Menu
	SimpleButton newGame, loadGame, options, quit;

	//the background color and the text color of the buttons
	Color background = Color.black;
	Color textColor = Color.lightGray;

	//various formatting helpers for the buttons
	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

	TrueTypeFont font;
	float fontSize = 12f;

	//a container for all the buttons on the screen
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	//it's handy to have a global pointer to the game object
	GameContainer gc;
	StateBasedGame sbg;

	//the bars that float in the background
	public BackgroundBarsAnimation backgroundAnimation;

	int mouseX, mouseY;

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.gc = gc;
		this.sbg = sbg;

		//loading the font
		try{
			InputStream is = ResourceLoader.getResourceAsStream("HappyKiller.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
			awtFont = awtFont.deriveFont(fontSize);
			font = new TrueTypeFont(awtFont, false);
		}catch(Exception e){
			e.printStackTrace();
		}

		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);

		newGame = new SimpleButton(0, 0, buttonWidth, buttonHeight, "New Game");
		loadGame = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Load Game");
		options = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Options");
		quit = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Quit");

		buttons.add(newGame);
		buttons.add(loadGame);
		buttons.add(options);
		buttons.add(quit);
		
		//generate random background color
		Random r = new Random();
		String color = Integer.toHexString(r.nextInt(7)) + Integer.toHexString(r.nextInt(7)) + //red
				       Integer.toHexString(r.nextInt(3)) + Integer.toHexString(r.nextInt(3)) + //green
				       Integer.toHexString(r.nextInt(10)) + Integer.toHexString(r.nextInt(10));//blue
		background = Color.decode("#"+color);
		background.darker(5.0f);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		backgroundAnimation.draw(g);

		g.setFont(font);
		
		g.setBackground(background);

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
		
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);

		for(SimpleButton b : buttons){
			b.move(buttonXOffset, buttonYOffset + (counter * buttonYGap));
			if(b == quit){
				counter++;
				b.move(buttonXOffset, buttonYOffset + (counter * buttonYGap));
			}
			counter++;
			b.hover(mouseX, mouseY);
		}
		
		//System.out.printf("Mouse pos: (%d, %d)\n", mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			if(quit.hover(x, y)){
				gc.exit();
			}else if(options.hover(x, y)){
				for(SimpleButton b2 : buttons){
					b2.reset();
				}
				sbg.enterState(Driver.OPTIONS_MENU);
			}else if(newGame.hover(x, y)){
				for(SimpleButton b2 : buttons){
					b2.reset();
				}
				sbg.enterState(Driver.LEVEL_0, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return Driver.MAIN_MENU;
	}

}
