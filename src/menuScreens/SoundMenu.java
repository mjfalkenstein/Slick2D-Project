package menuScreens;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SlideBar;
import utils.SimpleButton;
import driver.Driver;

public class SoundMenu extends BasicGameState{
	
	MainMenu mainMenu;
	SlideBar master, sfx, music;
	ArrayList<SlideBar> bars = new ArrayList<SlideBar>();
	int barWidth, barHeight, barGap, barXOffset, barYOffset;
	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;
	SimpleButton back;
	
	Color background;
	Color textColor = Color.lightGray;
	
	float masterVolume, sfxVolume, musicVolume;
	
	Font awtFont;
	TrueTypeFont font;
	
	float fontSize = 24f;
	
	GameContainer gc;
	StateBasedGame sbg;
	
	/**
	 * Constructor
	 * 
	 * @param mainMenu - an instance of the Main Menu
	 */
	public SoundMenu(MainMenu mainMenu){
		this.mainMenu = mainMenu;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
		
		buttonWidth = 220;
		buttonHeight = 30;
		buttonXOffset = (int)(gc.getWidth() * 0.9f - 200);
		buttonYOffset = (int)(gc.getHeight() * 0.5f);
		buttonYGap = (int)(gc.getHeight() * 0.075f);
		
		barWidth = gc.getWidth() * 1/3;
		barHeight = gc.getHeight() * 1/50;
		barGap = barHeight * 2;
		barXOffset = gc.getWidth() * 5/10;
		barYOffset = gc.getHeight() * 1/2;
		
		master = new SlideBar(0, 0, barWidth, barHeight);
		sfx = new SlideBar(0, 0, barWidth, barHeight);
		music = new SlideBar(0, 0, barWidth, barHeight);
		
		bars.add(master);
		bars.add(sfx);
		bars.add(music);
		
		back = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Back");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		mainMenu.backgroundAnimation.draw(g);

		background = mainMenu.background;
		
		g.setFont(mainMenu.font);
		
		g.setColor(textColor);
		g.drawString("Master Volume: " + (int)(master.getValue() * 100) + "%", master.getX(), master.getY()-g.getFont().getLineHeight());
		g.drawString("SFX Volume: " + (int)(sfx.getValue() * 100) + "%", sfx.getX(), sfx.getY()-g.getFont().getLineHeight());
		g.drawString("Music Volume: " + (int)(music.getValue() * 100) + "%", music.getX(), music.getY()-g.getFont().getLineHeight());

		g.drawString("Sound Options", buttonXOffset + buttonWidth - g.getFont().getWidth("Sound Options"), buttonYOffset);
		
		for(SlideBar b : bars){
			b.draw(g);
		}
		
		back.draw(g, background, textColor);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
		int counter = 1;
		
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		
		for(SlideBar b : bars){
			b.move(buttonXOffset - (barWidth - buttonWidth), barYOffset + (counter * (barGap + barHeight)));
			if(b.getValue() > 0.99){
				b.setValue(1);
			}
			counter++;
		}
		
		back.move(buttonXOffset, buttonYOffset + (4 * buttonYGap));
		back.hover(mouseX, mouseY);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y){
		if(button == 0){
			if(back.hover(x, y)){
				back.reset();
				sbg.enterState(Driver.OPTIONS_MENU);
			}else if(master.hover(x, y)){
				masterVolume = master.getValue();
			}else if(sfx.hover(x, y)){
				sfxVolume = sfx.getValue();
			}else if(music.hover(x, y)){
				musicVolume = music.getValue();
			}
		}
	}

	@Override
	public int getID() {
		return Driver.SOUND_MENU;
	}

}
