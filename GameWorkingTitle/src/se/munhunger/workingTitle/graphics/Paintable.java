package se.munhunger.workingTitle.graphics;

import java.awt.Graphics2D;

/**
 * Interface to denote that the object can be painted in a meaningful way
 * 
 * @author munhunger
 *		
 */
public interface Paintable
{
	/**
	 * Paints the object in it's appropriate place in the world
	 * It will ignore any previous color settings of the graphics object, and it
	 * will try to avoid side effects such as changing the color of the graphics
	 * object
	 * 
	 * @param g2d
	 *            the graphics object to use to paint with
	 * @param xOffset
	 *            the amount of pixels to offset this tile by in its' x
	 *            coordinate
	 * @param yOffset
	 *            the amount of pixels to offset this tile by in its' y
	 *            coordinate
	 * @param zoom
	 *            how many times to multiply the size of this object.
	 * @param displace
	 *            if true the zoom will displace its x and y position
	 */
	public void paint(Graphics2D g2d, float xOffset, float yOffset, float zoom, boolean displace);
}
