package se.munhunger.workingTitle.entity;

import se.munhunger.workingTitle.entity.ship.ShipBlock;

/**
 * An astroid building block
 * 
 * @author munhunger
 * 		
 */
public class AsteroidBlock extends ShipBlock
{
	/**
	 * Basic constructor that creates a plain asteroid block
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param parent
	 *            holding ship entity
	 */
	public AsteroidBlock(int x, int y, Entity parent)
	{
		super(x, y, 0.6f, 0.25f, 0f, parent);
	}
}
