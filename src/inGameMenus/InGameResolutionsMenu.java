package inGameMenus;

import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.geom.RoundedRectangle;
import org.lwjgl.opengl.Display;

import utils.ButtonComparator;
import utils.Notification;
import utils.SimpleButton;

/**
 * This is a simple options menu with buttons that lead to other menus with the actual options
 */
public class InGameResolutionsMenu{

	SimpleButton res1, res2, res3, res4, res5, res6, cancel;
	boolean showFPS = false;
	boolean fullscreen = true;

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap, buttonXGap;

	Color background;
	Color textColor = Color.lightGray;

	GameContainer gc;

	boolean addNewRes;

	Notification warning;
	SimpleButton b1, b2;

	String res;

	InGameVideoMenu options;

	boolean showing = false;

	int counter = 5;

	RoundedRectangle body;

	public InGameResolutionsMenu(GameContainer gc, InGameVideoMenu options){
		this.gc = gc;
		this.options = options;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		//generating list of supported resolutions and adding them to the list of buttons
		try{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			if(modes.length > 6){
				DisplayMode[] temp = new DisplayMode[6];
				for(int i = 0; i < 6; i++){
					temp[i] = modes[i];
				}
				modes = temp;
			}
			for (int i=0;i<modes.length;i++) {
				DisplayMode current = modes[i];
				String s = current.getWidth() + " x " + current.getHeight();
				buttons.add(new SimpleButton(0, 0, buttonWidth, buttonHeight, s));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		if(buttons.size() > 6){
			buttons.subList(buttons.size() - 5, buttons.size());
		}
		
		Collections.sort(buttons, new ButtonComparator());

		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		background = Color.black;

		warning = new Notification(0, 0, gc.getWidth()/3, gc.getHeight()/3, background, textColor, b1, b2, buttonYGap, "Change Resolution", "This will change your resolution are you sure you want to continue?");

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);
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

			cancel.draw(g, background, textColor);

			g.drawString("Select Resolution", (int)body.getX() + buttonXOffset + buttonWidth - g.getFont().getWidth("Select Resolution"), (int)body.getY() + buttonYOffset);

			warning.draw(g, font);
		}
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(int cameraX, int cameraY, int mouseX, int mouseY, GameContainer gc){
		
		body.setLocation(cameraX + 20, cameraY + 20);
		body.setHeight(gc.getHeight() - 40);
		body.setWidth(gc.getWidth() - 40);
		counter --;
		int xCounter = 0;
		int yCounter = 1;
		int buttonCounter = 0;

		buttonXOffset = (int)(body.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(body.getHeight() * 0.5f);
		buttonYGap = (int)(body.getHeight() * 0.075f);
		buttonXGap = (int)(body.getWidth() * 0.2);

		for(xCounter = 0; xCounter <= 1; xCounter++){
			for(yCounter = 1; yCounter <= 3; yCounter++){
				if(buttonCounter < buttons.size()){
					buttons.get(buttonCounter).move((int)body.getX() + buttonXOffset - (xCounter * (buttonXGap + 100)), (int)body.getY() + buttonYOffset + (yCounter * buttonYGap));
					if(!warning.isShowing()){
						buttons.get(buttonCounter).hover(mouseX, mouseY);
					}
				}
				buttonCounter++;
			}
		}
		cancel.move((int)body.getX() + buttonXOffset, (int)body.getY() + buttonYOffset + (yCounter * buttonYGap));
		if(!warning.isShowing()){
			cancel.hover(mouseX, mouseY);
		}
		
		warning.move((int)(body.getX() + body.getWidth()/2 - warning.getWidth()/2), (int)(body.getX() + body.getHeight()/2 - warning.getHeight()/2));
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(showing && !options.showing && counter < 0){
			if(button == 0){
				if(b2.handleMouseInput(x, y) && warning.isShowing()){
					warning.hide();
				}
				if(b1.handleMouseInput(x, y) && warning.isShowing()){
					String dimensions[] = res.split(" x ");
					for(SimpleButton resButton : buttons){
						resButton.reset();
						b1.reset();
						b2.reset();
					}
					try { 
						((AppGameContainer) gc).setDisplayMode(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), gc.isFullscreen());
						warning.hide();
					} catch (Exception e) {
						 warning.setHeader("Error");
						 warning.setBody("Unable to switch to resolution: " + dimensions[0] + " x " + dimensions[1]);
						 warning.show();
						 e.printStackTrace();
					}
				}
				if(cancel.handleMouseInput(x, y)){
					for(SimpleButton b : buttons){
						b.reset();
					}
					cancel.reset();
					hide();
				}
				for(SimpleButton b : buttons){
					if(b.handleMouseInput(x, y) && !warning.isShowing()){
						warning.show();
						res = b.getText();
					}
				}
			}
		}
	}

	public void show(){
		counter = 5;
		options.hide();
		showing = true;
	}

	public void hide(){
		options.show();
		showing = false;
	}

	public boolean isShowing(){
		return showing;
	}
}
