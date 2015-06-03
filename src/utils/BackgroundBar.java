package utils;


import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * A single bar to be used in BackgroundBarAnimation
 */
public class BackgroundBar {

    float opacity = 1;
    float x, y;
    float height;
    float startOffset;
    float speed;
	int screenWidth;
    int screenHeight;
    
    Random r;

    Rectangle rect;

    /**
     * Creates a new Bar for the background
     * 
     * @param screenWidth - width of the screen
     * @param screenHeight - height of the screen
     */
    public BackgroundBar(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        r = new Random();
        x = r.nextInt(screenWidth) * 2 - screenWidth;
        y = r.nextInt(screenHeight/2);
        height = r.nextInt((int) (screenWidth * 0.05)) + 10;
        speed = r.nextFloat();
        opacity = r.nextFloat();
        startOffset = (float) (r.nextFloat() * Math.PI);
        rect = new Rectangle(x, y - height, screenWidth, height);
    }

    /**
     * Updates the position of the bar every frame
     */
    public void Act() {
        x = rect.getX();
        if (x > screenWidth) {
            rect.setX(-screenWidth);
            rect.setY(r.nextInt(screenHeight/2) - height);
        } else {
            rect.setX(rect.getX() + speed);
        }
    }

    /**
     * Draws the background bar
     * 
     * @param g - Slick2D graphics object
     * @param c - color of the bar
     */
    public void draw(Graphics g, Color c) {
        Color fill = new Color(c);
        fill.a = opacity;
        g.setColor(fill);
        g.fill(rect);
    }
}
