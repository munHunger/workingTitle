package se.munhunger.workingTitle.entity.ship;

/**
 * Denotes a basic weapon block that can possibly fire lasers?
 * @author munhunger
 *
 */
public class Weapon extends ShipBlock
{


	/**
	 * Basic constructor that creates a plain Weapon block
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public Weapon(int x, int y)
	{
		super(x, y, 1f, 0f, 0f);
	}

}
