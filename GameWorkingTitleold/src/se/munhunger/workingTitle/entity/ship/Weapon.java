package se.munhunger.workingTitle.entity.ship;

import se.munhunger.workingTitle.entity.projectile.BasicFire;
import se.munhunger.workingTitle.entity.projectile.BasicFlight;
import se.munhunger.workingTitle.entity.projectile.CompleteProjectile;

/**
 * Denotes a basic weapon block that can possibly fire lasers?
 * 
 * @author munhunger
 *		
 */
public class Weapon extends ShipBlock
{
	/**
	 * Projectile related to this weapon block
	 */
	public CompleteProjectile projectile = new CompleteProjectile()
	{
		{
			fireComponents.add(new BasicFire());
			flightComponents.add(new BasicFlight());
		}
	};
	
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
