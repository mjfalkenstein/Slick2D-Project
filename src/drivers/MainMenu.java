package drivers;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
	
	//a container for all the buttons on the screen
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();
	
	//it's handy to have a global pointer to the game object
	GameContainer game;
	
	//the bars that float in the background
	BackgroundBarsAnimation backgroundAnimation;

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		game = gc;
		
		backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);
		
		buttonWidth = 200;
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
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		backgroundAnimation.draw(g);
		
		newGame.draw(g, background, textColor);
		loadGame.draw(g, background, textColor);
		options.draw(g, background, textColor);
		quit.draw(g, background, textColor);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int mouseX, mouseY;
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
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			for(SimpleButton b : buttons){
				if(b.hover(x, y)){
					System.out.println("You clicked: " + b.getText());
					if(b == quit){
						game.exit();
					}
				}
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return 0;
	}

}
