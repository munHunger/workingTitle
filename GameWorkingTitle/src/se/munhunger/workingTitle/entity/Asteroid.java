package se.munhunger.workingTitle.entity;

import se.munhunger.workingTitle.util.DiamondSquareFractal;

/**
 * A semi static Asteroid object
 * 
 * @author munhunger
 * 		
 */
public class Asteroid extends Entity
{
	/**
	 * Generates a bulk asteroid
	 * 
	 * @param xCoord
	 *            x coordinate of the asteroid
	 * @param yCoord
	 *            y coordinate of the asteroid
	 */
	public Asteroid(int xCoord, int yCoord)
	{
		DiamondSquareFractal fractal = new DiamondSquareFractal(17, true);
		for (int x = 0; x < fractal.getWidth(); x++)
			for (int y = 0; y < fractal.getHeight(); y++)
				if (fractal.getValue(x, y) < 0.8f)
					parts = parts.add(new AsteroidBlock(x, y, this).getSize());
		validateSize();
		getSize().setXf(xCoord);
		getSize().setYf(yCoord);
		System.out.println(String.format("X:%d\tY:%d\t%dx%d", getSize().getX(), getSize().getY(), getSize().getWidth(),
				getSize().getHeight()));
	}
}
