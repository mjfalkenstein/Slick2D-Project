package driver;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import screens.MainMenu;
import screens.OptionsMenu;
import screens.VideoOptionsMenu;

/**
 * The main driver class, initializes all the game states and handles the initial display mode
 */
public class Driver extends StateBasedGame{
	
	StateBasedGame game;
	GameContainer gc;
	
	public static final int MAIN_MENU = 0;
	public static final int OPTIONS_MENU = 1;
	public static final int VIDEO_OPTIONS_MENU = 2;

	/**
	 * Constructor
	 * 
	 * @param name - the name of the game object
	 */
	public Driver(String name) {
		super(name);
	}
	
	/**
	 * Called at program startup
	 * 
	 * @param gc - the game container
	 * @param game - the game itself
	 * 
	 * @throws SlickException
	 */
	public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		this.gc = gc;
		this.game = game;
	}

	/**
	 * Called at program startup
	 * 
	 * Used to initialize all of the other screens in the game
	 */
	public void initStatesList(GameContainer gc) throws SlickException {
		MainMenu mainMenu = new MainMenu();
		addState(mainMenu);
		addState(new OptionsMenu(mainMenu));
		addState(new VideoOptionsMenu(mainMenu));
		
		enterState(MAIN_MENU);
	}
	
	/**
	 * Called at program startup 
	 * 
	 * Used to initialize everything in the window, including title, size, etc.
	 * Will eventually be used for loading saved games, user settings, etc.
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args){
		AppGameContainer container;
		try{
			container = new AppGameContainer(new Driver("TEST"));
			container.setDisplayMode(container.getScreenWidth(), container.getScreenHeight(), true);
			container.setShowFPS(false);
			container.setTargetFrameRate(60);
			container.setAlwaysRender(true);
			container.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
