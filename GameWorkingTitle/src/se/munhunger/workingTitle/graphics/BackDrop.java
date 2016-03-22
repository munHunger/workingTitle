package se.munhunger.workingTitle.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import se.munhunger.workingTitle.util.Globals;

/**
 * Background space!
 * 
 * @author munhunger
 * 		
 */
public class BackDrop implements Paintable
{
	/**
	 * All possible colors of stars.
	 */
	private static Color[] starColors = new Color[] { new Color(0.9f, 0.9f, 0.9f, 0.5f),
			new Color(1f, 0.8f, 0.5f, 0.5f), new Color(0.9f, 0.9f, 0.5f, 0.5f), new Color(0.6f, 0.8f, 0.8f, 0.5f) };
			
	/**
	 * The different layers of backdrop.
	 */
	private BufferedImage[] backDrops = new BufferedImage[3];
	
	{
		for (int i = 0; i < backDrops.length; i++)
		{
			backDrops[i] = new BufferedImage(1025, 1025, BufferedImage.TYPE_INT_ARGB);
			Graphics2D imageGraphics = backDrops[i].createGraphics();
			
			for (int n = 0; n < Math.pow(7, i + 1); n++)
				drawStar(imageGraphics, Globals.random.nextInt(backDrops[i].getWidth()),
						Globals.random.nextInt(backDrops[i].getHeight()),
						Globals.random.nextInt(((backDrops.length - i) + 2) * 2));
						
			int radius = 2 * (i + 1);
			int size = radius * 2 + 1;
			float weight = 1.0f / (size * size);
			float[] data = new float[size * size];
			
			for (int n = 0; n < data.length; n++)
			{
				data[n] = weight;
			}
			
			Kernel kernel = new Kernel(size, size, data);
			ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
			
			backDrops[i] = op.filter(backDrops[i], null);
		}
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		for (int i = 0; i < backDrops.length; i++)
		{
			int windowWidth = 1600;
			int windowHeight = 1600;
			int currentX = ((int) xOffset / (i + 3)) % backDrops[i].getWidth();
			while (currentX < windowWidth)
			{
				int currentY = ((int) yOffset / (i + 3)) % backDrops[i].getWidth();
				while (currentY < windowHeight)
				{
					g2d.drawImage(backDrops[i], currentX, currentY, null);
					currentY += backDrops[i].getHeight();
				}
				currentX += backDrops[i].getWidth();
			}
		}
	}
	
	/**
	 * Draws a star at the given coordinate
	 * 
	 * @param g2d
	 *            the graphics object to paint with
	 * @param x
	 *            the x coordinate to paint at
	 * @param y
	 *            the y coordinate to paint at
	 * @param size
	 *            the size of each part of the star.... this could be pretty
	 *            arbitrary
	 */
	private void drawStar(Graphics2D g2d, int x, int y, int size)
	{
		g2d.setColor(starColors[Globals.random.nextInt(starColors.length)]);
		g2d.fillRect(x + size, y, size, size);
		g2d.fillRect(x, y + size, size, size);
		g2d.fillRect(x + size * 2, y + size, size, size);
		g2d.fillRect(x + size, y + size * 2, size, size);
	}
}