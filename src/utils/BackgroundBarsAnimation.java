package utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


/**
 * A background animation
 * 
 * Creates a number of BackgroundBars and handles their movement and drawing
 */
public class BackgroundBarsAnimation{
    ArrayList<BackgroundBar> bars;
    int maxBars = 15;
    int numBars;
    double time;

    Color bgColor;
    Color barColor;

    /**
     * Creates a new BackgroundBarsAnimation
     * 
     * @param gc - the game container
     * @param c - color of the bars
     */
    public BackgroundBarsAnimation(GameContainer gc, Color c) {
        bars = new ArrayList<BackgroundBar>();
        barColor = c;
        
        while (numBars < maxBars) {
            bars.add(new BackgroundBar(gc.getWidth(), gc.getHeight()));
            numBars++;
        }
    }

    /**
     * Draws the animation
     * 
     * @param g - the graphics object, required to draw the bars
     */
    public void draw(Graphics g) {
        for (BackgroundBar b : bars) {
            b.Act();
            b.draw(g, barColor);
        }
    }
}