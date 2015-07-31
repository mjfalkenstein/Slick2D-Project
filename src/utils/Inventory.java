package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * A class to represent the player's inventory, including drawing and handling max size
 */
public class Inventory {

	ArrayList<Item> items;
	Rectangle background;
	private int maxSize = 7;
	private boolean visible = false;
	float x, y;

	/**
	 * Constructor
	 * 
	 * @param gc - GameContainer
	 * @param items - list of items already in the inventory
	 */
	public Inventory(ArrayList<Item> items){
		this.items = items;
		background = new Rectangle(10, 10, 1000, 104);
		x = 0;
		y = 0;
	}

	/**
	 * Called every frame, handles positioning
	 * 
	 * @param gc - GameContainer
	 * @param delta - time since last frame
	 */
	public void update(GameContainer gc, int delta){
		gc.getInput();
		background.setLocation(10, 10);
		background.setWidth(gc.getWidth() - 20);
		background.setHeight(104);
		if(gc.getInput().isKeyDown(Input.KEY_Q)){
			visible = true;
		}else{
			visible = false;
		}
		
		background.setLocation(x + 10, y + 10);
	}

	/**
	 * Called every frame, handles drawing the inventory and everything in it
	 * 
	 * @param g - current Graphics context
	 */
	public void draw(Graphics g){
		if(visible){
			g.setColor(Color.orange);
			g.fill(background);
			for(Item item : items){
				item.isShowing = true;
				item.draw(g);
			}
		}else{
			for(Item item : items){
				item.isShowing = false;
			}
		}
	}

	/**
	 * Add a new item to the inventory, if the inventory isn't already full
	 * 
	 * @param item - Item to add 
	 */
	public void addItem(Item item){
		if(items.size() < maxSize - 1){
			items.add(item);
		}
	}
	
	/**
	 * Removes the given item from the inventory
	 * 
	 * @param item - Item to remove
	 */
	public void removeItem(Item item){
		items.remove(item);
	}

	/**
	 * Instantly moves the visual representation of the inventory to the new coordinates
	 * 
	 * @param x - new X
	 * @param y - new Y
	 */
	public void move(int x, int y){
		this.x = x;
		this.y = y;
		for(int i = 0; i < items.size(); i++){
			items.get(i).move(background.getX() + i * items.get(i).maxWidth + items.get(i).maxWidth/2, background.getY() + items.get(i).maxHeight/2);
		}
	}

	/**
	 * Clears everything in the inventory
	 */
	public void reset(){
		items.clear();
	}

	/**
	 * Returns whether or not the player's inventory contains an Item
	 * 
	 * @param i - Item we're searching for
	 * @return - true if the inventory contains the Item, false otherwise
	 */
	public boolean contains(Item i){
		return items.contains(i);
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public String toString(){
		String s = "";
		for(Item i : items){
			s += i.toString();
		}
		return s;
	}
}