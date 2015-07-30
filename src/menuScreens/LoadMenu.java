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

	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;

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

		File folder = new File("savedGames/");
		File[] listOfFiles = folder.listFiles();

		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");

		if(listOfFiles.length > 3){
			File[] temp = new File[3];
			int counter = 5;
			for(int i = listOfFiles.length; i > listOfFiles.length - 3; i--){
				temp[counter] = listOfFiles[i];
				counter--;
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

		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();

		int counter = 0;

		for(int i = 0; i < buttons.size(); i++, counter++){
			SimpleButton b = buttons.get(i);
			b.move(buttonXOffset, buttonYOffset + (counter + 1) * buttonYGap);
			b.hover(mouseX, mouseY);
		}

		back.move(buttonXOffset, buttonYOffset + (4 * buttonYGap));
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
