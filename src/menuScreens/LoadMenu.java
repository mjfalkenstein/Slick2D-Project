package menuScreens;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import driver.Driver;
import utils.Notification;
import utils.SaverLoader;
import utils.SimpleButton;

/**
 * This class serves as a simple ui for the player to load a saved game
 */
public class LoadMenu extends BasicGameState {

	MainMenu mainMenu;

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonXGap, buttonYGap;

	Color textColor = Color.lightGray;
	Color background;

	int mouseX, mouseY;

	StateBasedGame sbg;

	TrueTypeFont font;
	float fontSize = 24f;

	SimpleButton back;

	Notification warning;
	SimpleButton b1, b2;
	
	String path;

	public LoadMenu(MainMenu mainMenu){
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
		buttonXGap = (int)(gc.getWidth() * 0.2);

		File folder = new File("savedGames/");
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);

		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

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
				buttons.add(new SimpleButton(0, 0, buttonWidth, buttonHeight, listOfFiles[i].getName()));
			} 
		}
		
		background = mainMenu.background;
		
		b1 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Confirm");
		b2 = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		warning = new Notification(0, 0, gc.getWidth()/3, gc.getHeight()/3, background, textColor, b1, b2, buttonYGap, "Load Game", "Are you sure you want to load file: \n" + path);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		mainMenu.backgroundAnimation.draw(g);

		background = mainMenu.background;

		g.setFont(mainMenu.font);

		for(SimpleButton b : buttons){
			b.draw(g, background, textColor);
		}

		back.draw(g, background, textColor);

		g.drawString("Load Game", buttonXOffset + buttonWidth - g.getFont().getWidth("Load Game"), buttonYOffset);

		warning.draw(g, mainMenu.font);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
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

	@Override
	public void mouseReleased(int button, int x, int y){
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
				SaverLoader.loadGame("savedGames/" + path, sbg);
			}
			for(SimpleButton b : buttons){
				if(b.handleMouseInput(x, y) && !warning.isShowing()){
					path = b.getText();
					warning.setBody("Are you sure you want to load file: \n" + path);
					warning.show();
				}
			}
			if(back.handleMouseInput(x, y)){
				for(SimpleButton b : buttons){
					b.reset();
				}
				back.reset();
				sbg.enterState(Driver.MAIN_MENU);
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}
}
