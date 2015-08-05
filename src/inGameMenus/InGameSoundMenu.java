package inGameMenus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;

import utils.SlideBar;
import utils.SimpleButton;

/**
 * This class is in in-game alternative to the standard Sound Menu
 */
public class InGameSoundMenu{

	SlideBar master, sfx, music;
	ArrayList<SlideBar> bars = new ArrayList<SlideBar>();
	int barWidth, barHeight, barGap, barXOffset, barYOffset;
	int buttonWidth, buttonHeight, buttonXOffset, buttonYOffset, buttonYGap;
	SimpleButton cancel;

	Color textColor = Color.lightGray;
	Color background = Color.black;

	RoundedRectangle body;

	boolean showing = false;

	float masterVolume, sfxVolume, musicVolume;
	
	InGameOptionsMenu options;

	/**
	 * Constructor 
	 * 
	 * @param gc - the GameContainer
	 */
	public InGameSoundMenu(GameContainer gc, InGameOptionsMenu options){
		this.options = options;
		
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

		cancel = new SimpleButton(0, 0, buttonWidth, buttonHeight, "Cancel");

		body = new RoundedRectangle(20, 20, gc.getWidth() - 40, gc.getHeight() - 40, 10);
	}

	/**
	 * Called once every frame
	 * 
	 * Used to draw everything to the screen
	 */
	public void draw(Graphics g, TrueTypeFont font){

		if(showing){
			Color c = Color.black;
			c.a = 0.95f;
			g.setColor(Color.black);
			g.setLineWidth(3);
			g.draw(body);
			g.setColor(c);
			g.fill(body);

			g.setFont(font);
			
			g.setColor(textColor);
			g.drawString("Master Volume: " + (int)(master.getValue() * 100) + "%", master.getX(), master.getY()-g.getFont().getLineHeight());
			g.drawString("SFX Volume: " + (int)(sfx.getValue() * 100) + "%", sfx.getX(), sfx.getY()-g.getFont().getLineHeight());
			g.drawString("Music Volume: " + (int)(music.getValue() * 100) + "%", music.getX(), music.getY()-g.getFont().getLineHeight());

			g.drawString("Sound Options", buttonXOffset + buttonWidth - g.getFont().getWidth("Sound Options"), buttonYOffset);

			for(SlideBar b : bars){
				b.draw(g);
			}

			cancel.draw(g, background, textColor);
		}
	}

	/**
	 * Called once every frame
	 * 
	 * Used to update all necessary data, ie mouse position
	 */
	public void update(int cameraX, int cameraY, int mouseX, int mouseY){
		
		int counter = 1;

		for(SlideBar b : bars){
			b.move(buttonXOffset - (barWidth - buttonWidth), barYOffset + (counter * (barGap + barHeight)));
			if(b.getValue() > 0.99){
				b.setValue(1);
			}
			counter++;
		}

		cancel.move(buttonXOffset, buttonYOffset + (4 * buttonYGap));
		cancel.hover(mouseX, mouseY);
	}

	/**
	 * Called upon mouse button release (as opposed to mouse button press)
	 * 
	 * Used as an event handler
	 */
	public void handleMouseInput(int button, int x, int y){
		if(button == 0){
			if(cancel.handleMouseInput(x, y)){
				cancel.reset();
				hide();
			}else if(master.hover(x, y)){
				masterVolume = master.getValue();
			}else if(sfx.hover(x, y)){
				sfxVolume = sfx.getValue();
			}else if(music.hover(x, y)){
				musicVolume = music.getValue();
			}
		}
	}

	public void show(){
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
