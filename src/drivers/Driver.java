package drivers;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Driver extends StateBasedGame{
	
	StateBasedGame game;

	public Driver(String name) {
		super(name);
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException{
		this.game = game;
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new TestLevel());
		
		this.enterState(0);
	}
	
	public static void main(String[] args){
		AppGameContainer container;
		try{
			container = new AppGameContainer(new Driver("TEST"));
			container.setDisplayMode(640, 480, false);
			container.setShowFPS(false);
			container.setTargetFrameRate(60);
			container.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
