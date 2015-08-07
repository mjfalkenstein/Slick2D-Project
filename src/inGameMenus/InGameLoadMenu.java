package inGameMenus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;

import utils.Notification;
import utils.SaverLoader;
import utils.SimpleButton;

/**
 * This class serves as a simple ui for the player to load a saved game
 */
public class InGameLoadMenu {

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonXGap, buttonYGap;

	Color textColor = Color.lightGray;
	Color background = Color.black;

	GameContainer gc;
	StateBasedGame sbg;

	SimpleButton cancel;

	Notification warning;
	SimpleButton b1, b2;

	String path;

	RoundedRectangle body;

	boolean showing = false;

	/**
	 * Constructor 
	 * 
	 * @param gc - the GameContainer
	 */
	public InGameLoadMenu(GameContainer gc){
		this.gc = gc;

		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		buttonXGap = (int)(gc.getWidth() * 0.2);

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);

		File folder = new File("savedGames/");
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);

		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		if(listOfFiles.length >= 6){
			File[] temp = new File[6];
			int counter = 0;
			for(int i = listOfFiles.length; i > listOfFiles.length - 6; i--, counter++){
				temp[counter] = listOfFiles[i-1];
			}
			listOfFiles = temp;
		}

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().contains(".sav")){
					buttons.add(new SimpleButton(0, 0, buttonWidth, buttonHeight, listOfFiles[i].getName()));
				}
			} 
		}

		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		warning = new Notification(0, 0, (int)body.getWidth()/3, (int)body.getHeight()/3, background, textColor, b1, b2, buttonYGap, "Load Game", "Are you sure you want to load file: \n" + path);
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

			g.drawString("Load Game", (int)body.getX() + buttonXOffset + buttonWidth - g.getFont().getWidth("Load Game"), (int)body.getY() + buttonYOffset);

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
					buttons.get(buttonCounter).hover(mouseX, mouseY);
				}
				buttonCounter++;
			}
		}
		cancel.move((int)body.getX() + buttonXOffset, (int)body.getY() + buttonYOffset + (yCounter * buttonYGap));
		cancel.hover(mouseX, mouseY);

		warning.move((int)(body.getX() + body.getWidth()/2 - warning.getWidth()/2), (int)(body.getY() + body.getHeight()/2 - warning.getHeight()/2));
		b1.hover(mouseX, mouseY);
		b2.hover(mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(showing){
			if(button == 0){			
				if(b2.handleMouseInput(x, y) && warning.isShowing()){
					warning.hide();
				}
				if(b1.handleMouseInput(x, y) && warning.isShowing()){
					for(SimpleButton b : buttons){
						b.reset();
						b1.reset();
						b2.reset();
					}
					SaverLoader.loadGame(gc, "savedGames/" + path, sbg);
				}
				for(SimpleButton b : buttons){
					if(b.handleMouseInput(x, y) && !warning.isShowing()){
						path = b.getText();
						warning.setBody("Are you sure you want to load file: \n" + path);
						warning.show();
					}
				}
				if(cancel.handleMouseInput(x, y)){
					for(SimpleButton b : buttons){
						b.reset();
					}
					cancel.reset();
					hide();
				}
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
