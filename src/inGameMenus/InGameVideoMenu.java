package inGameMenus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;

import utils.SimpleButton;

/**
 * This is a simple options menu with buttons that lead to other menus with the actual options
 */
public class InGameVideoMenu{

	SimpleButton toggleFPS, toggleFullscreen, resolutions, cancel;

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

	Color textColor = Color.lightGray;
	Color background = Color.black;

	RoundedRectangle body;

	boolean showing = false;

	InGameOptionsMenu options;

	GameContainer gc;

	TrueTypeFont font;
	float fontSize = 24f;
	
	int counter = 5;
	
	InGameResolutionsMenu resolutionsMenu;

	public InGameVideoMenu(GameContainer gc, InGameOptionsMenu options){
		this.options = options;
		this.gc = gc;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);

		toggleFPS = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Toggle FPS");
		toggleFullscreen = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Toggle Fullscreen");
		resolutions = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Set Resolution");
		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		buttons.add(toggleFPS);
		buttons.add(toggleFullscreen);
		buttons.add(resolutions);
		buttons.add(cancel);

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);

		resolutionsMenu = new InGameResolutionsMenu(gc, this);
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
			g.drawString("Video Options", (int)body.getX() + buttonXOffset + buttonWidth - g.getFont().getWidth("Video Options"), (int)body.getY() + buttonYOffset);
		}
		resolutionsMenu.draw(g, font);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(int cameraX, int cameraY, int mouseX, int mouseY, GameContainer gc){
		counter--;
		body.setLocation(cameraX + 20, cameraY + 20);
		body.setHeight(gc.getHeight() - 40);
		body.setWidth(gc.getWidth() - 40);
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
		
		resolutionsMenu.update(cameraX, cameraY, mouseX, mouseY, gc);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(showing && !options.showing && counter <= 0){
			if(button == 0){
				if(cancel.handleMouseInput(x, y)){
					for(SimpleButton b : buttons){
						b.reset();
					}
					options.show();
					hide();
				}else if(toggleFPS.handleMouseInput(x, y)){
					gc.setShowFPS(!gc.isShowingFPS());
				}else if(toggleFullscreen.handleMouseInput(x, y)){
					try {
						gc.setFullscreen(!gc.isFullscreen());
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}else if(resolutions.handleMouseInput(x, y)){
					for(SimpleButton b : buttons){
						b.reset();
					}
					hide();
					options.hide();
					resolutionsMenu.show();
				}
			}
		}
		resolutionsMenu.handleMouseInput(button, x, y);
	}

	public void show(){
		counter = 5;
		options.hide();
		showing = true;
	}

	public void hide(){
		showing = false;
	}

	public boolean isShowing(){
		return showing || resolutionsMenu.isShowing();
	}

}
