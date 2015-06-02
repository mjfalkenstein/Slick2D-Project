package drivers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TestLevel extends BasicGameState {

	public void init(GameContainer arg0, StateBasedGame arg1)throws SlickException {
		
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)throws SlickException {
		arg2.drawString("Hello World", 100, 100);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)throws SlickException {
		
	}

	public int getID() {
		return 0;
	}

}
