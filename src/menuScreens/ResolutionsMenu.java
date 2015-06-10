package menuScreens;

import java.util.ArrayList;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.AppGameContainer;
import org.lwjgl.opengl.Display;

import driver.Driver;
import utils.Notification;
import utils.SimpleButton;
import utils.BackgroundBarsAnimation;

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

	Color background;
	Color textColor = Color.lightGray;

	int mouseX, mouseY;

	GameContainer gc;
	StateBasedGame sbg;

	TrueTypeFont font;
	float fontSize = 24f;

	boolean addNewRes;
	
	Notification warning;
	SimpleButton b1, b2;
	
	String res;

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

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		//generating list of supported resolutions and adding them to the list of buttons
		try{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			if(modes.length > 9){
				DisplayMode[] temp = new DisplayMode[9];
				for(int i = 0; i < 9; i++){
					temp[i] = modes[i];
				}
				modes = temp;
			}
			for (int i=0;i<modes.length;i++) {
				DisplayMode current = modes[i];
				String s = current.getWidth() + " x " + current.getHeight();
				buttons.add(new SimpleButton(0, 0, buttonWidth, buttonHeight, s));
			}
		}catch(Exception e){}
		
		if(buttons.size() > 6){
			buttons.subList(0, 5);
		}
		
		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");
		
		warning = new Notification(0, 0, gc.getWidth()/3, gc.getHeight()/3, background, textColor, b1, b2, "Change Resolution", "This will change your resolution are you sure you want to continue?");
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		mainMenu.backgroundAnimation.draw(g);
		
		background = mainMenu.background;

		g.setFont(mainMenu.font);

		for(SimpleButton b : buttons){
			b.draw(g, background, textColor);
		}

		back.draw(g, background, textColor);

		g.drawString("Select Resolution", buttonXOffset + buttonWidth - g.getFont().getWidth("Select Resolution"), buttonYOffset);
		
		warning.draw(g);
	}

	/**
	 * Called once every frame
	 * 		return Driver.SOUND_MENU;

	 * Used to update all necessary data, ie mouse position
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int xCounter = 0;
		int yCounter = 1;
		int buttonCounter = 0;

		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();

		for(xCounter = 0; xCounter <= 1; xCounter++){
			for(yCounter = 1; yCounter <= 3; yCounter++){
				if(buttonCounter < buttons.size()){
					buttons.get(buttonCounter).move(buttonXOffset - (xCounter * (buttonXGap + 100)), buttonYOffset + (yCounter * buttonYGap));
					buttons.get(buttonCounter).hover(mouseX, mouseY);
				}
				buttonCounter++;
			}
		}
		back.move(buttonXOffset, buttonYOffset + (yCounter * buttonYGap));
		back.hover(mouseX, mouseY);
		
		warning.move(gc.getWidth()/2 - warning.getWidth()/2, gc.getHeight()/2 - warning.getHeight()/2);
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			if(b2.hover(x, y) && warning.isShowing()){
				warning.hide();
			}
			if(b1.hover(x, y) && warning.isShowing()){
				String dimensions[] = res.split(" x ");
				for(SimpleButton resButton : buttons){
					resButton.reset();
					b1.reset();
					b2.reset();
				}
				try { 
					((AppGameContainer) gc).setDisplayMode(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), gc.isFullscreen());
					mainMenu.backgroundAnimation = new BackgroundBarsAnimation(gc, Color.white);
					warning.hide();
				} catch (Exception e) {
					//TODO: Add an error window saying that it was unable to switch resolutions
					//      In theory, this will never happen since it buttons are generated
					//      from a list of supported resolutions, but you never know
				}
			}
			if(back.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				back.reset();
				sbg.enterState(Driver.VIDEO_OPTIONS_MENU);
			}
			for(SimpleButton b : buttons){
				if(b.hover(x, y) && !warning.isShowing()){
					warning.show();
					res = b.getText();
				}
			}
		}
	}

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return Driver.RESOLUTIONS_MENU;
	}

}
