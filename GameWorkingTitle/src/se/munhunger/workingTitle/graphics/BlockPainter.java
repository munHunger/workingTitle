package se.munhunger.workingTitle.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Helper for drawing some purdy squares
 * 
 * @author munhunger
 *		
 */
public class BlockPainter
{
	/**
	 * Draws a square.
	 * 
	 * @param g2d
	 *            the graphics component to draw with
	 * @param x
	 *            the x position of the upper left corner to draw
	 * @param y
	 *            the y position of the upper left corner to draw
	 * @param width
	 *            the width of the block to paint
	 * @param height
	 *            the height of the block to paint
	 * @param fg
	 *            the primary color of the block
	 * @param bg
	 *            the secondary color of the block
	 */
	public static void paintBlock(Graphics2D g2d, int x, int y, int width, int height, Color fg, Color bg)
	{
		Color old = g2d.getColor();
		float sixthWidth = width / 5;
		float sixthHeight = width / 5;
		
		g2d.setColor(fg);
		g2d.fillRect(x, y, width, height);
		g2d.setColor(bg);
		g2d.fillRect((int) (x + sixthWidth), (int) (y + sixthHeight), (int) (sixthWidth * 3), (int) (sixthHeight * 3));
		g2d.setColor(fg);
		g2d.fillRect((int) (x + sixthWidth * 2), (int) (y + sixthHeight * 2), (int) (sixthWidth), (int) (sixthHeight));
		g2d.setColor(old);
	}
	
	/**
	 * Draws a square.
	 * 
	 * @param g2d
	 *            the graphics component to draw with
	 * @param x
	 *            the x position of the upper left corner to draw
	 * @param y
	 *            the y position of the upper left corner to draw
	 * @param width
	 *            the width of the block to paint
	 * @param height
	 *            the height of the block to paint
	 * @param color
	 *            the primary color of the block
	 */
	public static void paintBlock(Graphics2D g2d, int x, int y, int width, int height, Color color)
	{
		paintBlock(g2d, x, y, width, height, color, color.darker());
	}
}
