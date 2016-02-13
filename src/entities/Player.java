package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import utils.Inventory;
import utils.Item;
import entities.Entity;

/**
 * The player as it is represented on the screen
 */
public class Player extends Entity {

	boolean dead = false;
	Inventory inventory;

	/**
	 * Constructor
	 * 
	 * @param boundingBox - a Rectangle representing the borders of the Platform
	 * @param velocity - the initial velocity
	 */
	public Player(int x, int y, float width, float height) {
		super(x, y, width, height);
		inventory = new Inventory(new ArrayList<Item>());
	}

	@Override
	public void update(GameContainer gc, int delta) {

		handleInputs(gc);
	}

	@Override
	public void move(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		
	}
	
	/**
	 * Called from update(), used to handle all inputs
	 * 
	 * @param gc - GameConatiner
	 */
	public void handleInputs(GameContainer gc){
		Input input = gc.getInput();
		
		
	}
	
	/**
	 * Used to signify that the player has been killed
	 */
	public void kill(){
		dead = true;
	}
	
	public void revive(){
		dead = false;
	}
	
	public boolean isDead(){
		return dead;
	}

	public void addItem(Item item) {
//		if(inventory == null){
//			inventory = new Inventory(null, null);
//		}
		inventory.addItem(item);
	}
	
	public void removeItem(Item item){
		inventory.removeItem(item);
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public boolean has(Item item){
		return inventory.contains(item);
	}
}
