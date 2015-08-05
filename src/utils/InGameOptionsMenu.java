package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;

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

	StateBasedGame sbg;
	float fontSize = 24f;

	RoundedRectangle body;

	boolean showing = false;

	/**
	 * Constructor 
	 * 
	 * @param gc - the GameContainer
	 * @param sbg - the StateBasedGame
	 */
	public InGameOptionsMenu(GameContainer gc, StateBasedGame sbg){
		this.sbg = sbg;

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
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void draw(Graphics g){
		if(showing){
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
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(button == 0){
			if(cancel.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				showing = false;
			}else if(videoOptions.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				//
			}else if(soundOptions.hover(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				//
			}
		}
	}

	public void show(){
		showing = true;
	}

	public void hide(){
		showing = false;
	}

	public boolean isShowing(){
		return showing;
	}
}
