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
	public Color color; // TODO change to private. this is public for debugging
						// purpose
	
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
		size = new SizedObject<Tile>(this, x, y, (int) Globals.zoom, (int) Globals.zoom);
	}
	
	/**
	 * @return the size wrapper for this object
	 */
	public SizedObject<Tile> getSize()
	{
		return size;
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace, float zoom)
	{
		int x = (int) ((size.getX() + xOffset) * (displace ? Globals.zoom * zoom : 1));
		int y = (int) ((size.getY() + yOffset) * (displace ? Globals.zoom * zoom : 1));
		int width = (int) (size.getWidth() * zoom);
		int height = (int) (size.getHeight() * zoom);
		BlockPainter.paintBlock(g2d, x, y, width, height, color);
	}
	
}
