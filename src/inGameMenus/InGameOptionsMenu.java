package inGameMenus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;

import utils.SimpleButton;

/**
 * This is a simple options menu with buttons that lead to other menus with the actual options
 * 
 * It serves as an in-game alternative to the OptionsMenu accessed from the main menu
 */
public class InGameOptionsMenu{

	SimpleButton soundOptions, videoOptions, gameOptions, cancel;
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

	Color textColor = Color.lightGray;
	Color background = Color.black;

	RoundedRectangle body;

	boolean showing = false;

	InGameResolutionsMenu resolutionsMenu;
	InGameSoundMenu soundMenu;
	InGameVideoMenu videoMenu;

	/**
	 * Constructor 
	 * 
	 * @param gc - the GameContainer
	 */
	public InGameOptionsMenu(GameContainer gc){

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);

		soundOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Sound Options");
		videoOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Video Options");
		gameOptions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Game Options");
		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		buttons.add(soundOptions);
		buttons.add(videoOptions);
		buttons.add(gameOptions);
		buttons.add(cancel);

		soundMenu = new InGameSoundMenu(gc, this);
		videoMenu = new InGameVideoMenu(gc, this);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void draw(Graphics g, TrueTypeFont font){

		if(showing){
			
			g.setFont(font);

			Color c = Color.black;
			c.a = 0.95f;
			g.setColor(Color.black);
			g.setLineWidth(3);
			g.draw(body);
			g.setColor(c);
			g.fill(body);

			for(SimpleButton b : buttons){
				b.draw(g, background, textColor);
			}

			g.drawString("Options", body.getX() + buttonXOffset + buttonWidth - g.getFont().getWidth("Options"), body.getY() + buttonYOffset);
		}
		soundMenu.draw(g, font);
		videoMenu.draw(g, font);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(int cameraX, int cameraY, int mouseX, int mouseY) {
		body.setLocation(cameraX + 20, cameraY + 20);

		int counter = 1;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(body.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(body.getHeight() * 0.5f);
		buttonYGap = (int)(body.getHeight() * 0.075f);

		for(SimpleButton b : buttons){
			b.move((int)body.getX() + buttonXOffset, (int)body.getY() + buttonYOffset + (counter * buttonYGap));
			counter++;
			b.hover(mouseX, mouseY);
		}

		soundMenu.update(cameraX, cameraY, mouseX, mouseY);
		videoMenu.update(cameraX, cameraY, mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(showing && !soundMenu.isShowing() && !videoMenu.isShowing()){
			if(button == 0){
				if(cancel.handleMouseInput(x, y)){
					showing = false;
				}else if(videoOptions.handleMouseInput(x, y)){
					hide();
					videoMenu.show();
				}else if(soundOptions.handleMouseInput(x, y)){
					hide();
					soundMenu.show();
				}else if(gameOptions.handleMouseInput(x, y)){
					//TODO: Implement the game options menu
				}
			}
		}
		soundMenu.handleMouseInput(button, x, y);
		videoMenu.handleMouseInput(button, x, y);
	}

	public void show(){
		for(SimpleButton b : buttons){
			b.reset();
		}
		showing = true;
	}

	public void hide(){
		for(SimpleButton b : buttons){
			b.reset();
		}
		showing = false;
	}
	
	public void remove(){
		hide();
		soundMenu.hide();
		videoMenu.hide();
	}

	public boolean isShowing(){
		return showing || soundMenu.isShowing() || videoMenu.isShowing();
	}
}
