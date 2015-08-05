package menuScreens;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import driver.Driver;
import utils.SimpleButton;

/**
 * This is a simple options menu with buttons that lead to other menus with the actual options
 */
public class OptionsMenu extends BasicGameState{
	
	MainMenu mainMenu;
	
	SimpleButton soundOptions, videoOptions, gameOptions, back;
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;
	
	Color textColor = Color.lightGray;
	Color background;
	
	int mouseX, mouseY;
	
	StateBasedGame sbg;
	
	TrueTypeFont font;
	float fontSize = 24f;
	
	/**
	 * Constructor
	 * 
	 * @param mainMenu - an instance of the Main Menu
	 */
	public OptionsMenu(MainMenu mainMenu){
		this.mainMenu = mainMenu;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.sbg = sbg;
		 
		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		
		soundOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Sound Options");
		videoOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Video Options");
		gameOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Game Options");
		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");
		
		buttons.add(soundOptions);
		buttons.add(videoOptions);
		buttons.add(gameOptions);
		buttons.add(back);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		mainMenu.backgroundAnimation.draw(g);

		background = mainMenu.background;
		
		g.setFont(mainMenu.font);
		
		for(SimpleButton b : buttons){
			b.draw(g, background, textColor);
		}
		
		g.drawString("Options", buttonXOffset + buttonWidth - g.getFont().getWidth("Options"), buttonYOffset);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int counter = 1;
		
		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		
		for(SimpleButton b : buttons){
			b.move(buttonXOffset, buttonYOffset + (counter * buttonYGap));
			counter++;
			b.hover(mouseX, mouseY);
		}
	}
	
	@Override
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			if(back.handleMouseInput(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				sbg.enterState(Driver.MAIN_MENU);
			}else if(videoOptions.handleMouseInput(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				sbg.enterState(Driver.VIDEO_OPTIONS_MENU);
			}else if(soundOptions.handleMouseInput(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				sbg.enterState(Driver.SOUND_MENU);
			}
		}
	}

	@Override
	public int getID() {
		return Driver.OPTIONS_MENU;
	}

}
