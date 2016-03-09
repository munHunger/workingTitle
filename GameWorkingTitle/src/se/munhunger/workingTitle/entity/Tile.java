package se.munhunger.workingTitle.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import se.munhunger.workingTitle.graphics.BlockPainter;
import se.munhunger.workingTitle.graphics.Paintable;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Represents the smallest part of the world. These are the building blocks, the
 * atoms!
 * 
 * @author munhunger
 * 
 */
public class Tile implements Paintable
{
	/**
	 * Wrapper to keep track of the size of this tile
	 */
	private SizedObject<Tile> size;

	/**
	 * The color of this tile. It should be possible to draw this object
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param r
	 *            red component of the tiles color
	 * @param g
	 *            green component of the tiles color
	 * @param b
	 *            blue component of the tiles color
	 * @throws IllegalArgumentException
	 *             if r,g or b is not in the range 0<=component<=1
	 */
	public Tile(int x, int y, float r, float g, float b) throws IllegalArgumentException
	{
		if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1)
			throw new IllegalArgumentException("Color is out of range, should be in the range [0,1]");
		color = new Color(r, g, b);
		size = new SizedObject<Tile>(this, x, y, 1, 1);
	}

	/**
	 * @return the size wrapper for this object
	 */
	public SizedObject<Tile> getSize()
	{
		return size;
	}

	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		float zoom = Globals.zoom;
		int x = (int) ((size.getX() + xOffset) * (displace ? zoom : 1));
		int y = (int) ((size.getY() + yOffset) * (displace ? zoom : 1));
		int width = (int) (1 * zoom);
		int height = (int) (1 * zoom);
		BlockPainter.paintBlock(g2d, x, y, width, height, color);
	}

}
