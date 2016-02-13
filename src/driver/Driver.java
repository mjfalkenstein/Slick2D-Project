package driver;

import levels.Level0;
import menuScreens.LoadMenu;
import menuScreens.MainMenu;
import menuScreens.OptionsMenu;
import menuScreens.ResolutionsMenu;
import menuScreens.SoundMenu;
import menuScreens.VideoOptionsMenu;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.state.StateBasedGame;

import utils.SaverLoader;
import entities.Player;

/**
 * The main driver class, initializes all the game states and handles the initial display mode
 */
public class Driver extends StateBasedGame{

	StateBasedGame game;
	GameContainer gc;

	//Constant state values for each of the screens in the game
	public static final int MAIN_MENU         	 	= 0;
	public static final int OPTIONS_MENU 			= 1;
	public static final int VIDEO_OPTIONS_MENU		= 2;
	public static final int RESOLUTIONS_MENU 		= 3;
	public static final int SOUND_MENU 				= 4;
	public static final int LOAD_GAME				= 5;
	public static final int LEVEL_0					= 6;

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
		addState(new LoadMenu(mainMenu));
		addState(new OptionsMenu(mainMenu));
		addState(new VideoOptionsMenu(mainMenu));
		addState(new ResolutionsMenu(mainMenu));
		addState(new SoundMenu(mainMenu));
		
		Player p = new Player(0, 0, 50, 50);
		addState(new Level0(gc, p, 3000, 1200));

		enterState(MAIN_MENU); 
	}

	/**
	 * Called at program startup 
	 * 
	 * Used to initialize everything in the window, including title, size, etc.
	 * Will eventually be used for loading saved games, user settings, etc.
	 * 
	 * @param args - command line arguments, not used
	 */
	public static void main(String Args[]) throws SlickException {
		Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
		AppGameContainer app = new AppGameContainer(new Driver("TEST"));
		app.setShowFPS(false);
		app.setVSync(true);
		app.setAlwaysRender(true);
		app.setTargetFrameRate(60);
		app.setMultiSample(4);
		app.setMinimumLogicUpdateInterval(0);
		app.setMaximumLogicUpdateInterval(16);
		try{
			app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
		}catch(SlickException e){
			app.setDisplayMode(1280, 800, true);
		}

		SaverLoader.loadSettings(app);

		app.start();
		app.getGraphics().setAntiAlias(true);
	}
}
