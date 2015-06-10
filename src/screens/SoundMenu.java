package screens;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

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
	
	Color background = Color.black;
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

	/**
	 * Called on program start-up
	 * 
	 * Used to initialize all necessary data for the screen to run
	 */
	public void init(GameContainer gc, StateBasedGame sbg)throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
		
		//loading the font
		try{
			InputStream is = ResourceLoader.getResourceAsStream("Squared Display.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
			awtFont = awtFont.deriveFont(fontSize);
			font = new TrueTypeFont(awtFont, false);
		}catch(Exception e){
			e.printStackTrace();
		}
		
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

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		mainMenu.backgroundAnimation.draw(g);
		
		g.setFont(font);
		
		g.setColor(textColor);
		g.drawString("Master Volume: " + (int)(master.getValue() * 100) + "%", master.getX(), master.getY()-g.getFont().getLineHeight());
		g.drawString("SFX Volume: " + (int)(sfx.getValue() * 100) + "%", sfx.getX(), sfx.getY()-g.getFont().getLineHeight());
		g.drawString("Music Volume: " + (int)(music.getValue() * 100) + "%", music.getX(), music.getY()-g.getFont().getLineHeight());
		
		for(SlideBar b : bars){
			b.draw(g);
		}
		
		back.draw(g, background, textColor);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse positionbarXOffset
	 */
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
	
	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
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

	/**
	 * The unique ID for this screen, must be different for all over BasicGameStates
	 */
	public int getID() {
		return Driver.SOUND_MENU;
	}

}