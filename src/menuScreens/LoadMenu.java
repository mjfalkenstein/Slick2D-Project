package menuScreens;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import driver.Driver;
import utils.SaverLoader;
import utils.SimpleButton;

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

		g.drawString("Load Game", buttonXOffset + buttonWidth - g.getFont().getWidth("Select Resolution"), buttonYOffset);
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
	}

	@Override
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			for(SimpleButton b : buttons){
				if(b.hover(x, y)){
					SaverLoader.loadGame("savedGames/" + b.getText(), sbg);
				}
			}
			if(back.hover(x, y)){
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
