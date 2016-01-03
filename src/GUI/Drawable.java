package GUI;

import java.awt.Dimension;
import java.awt.Graphics;

public interface Drawable {
	/**
	 * Method used for drawing to a java.awt.Graphics object.
	 * @param g Graphics to draw to
	 * @param d How much the Drawable will be scaled up.
	 */
	public void draw(Graphics g, Dimension d);
	public Dimension getDimension();
}
