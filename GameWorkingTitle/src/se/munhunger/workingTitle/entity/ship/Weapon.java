package se.munhunger.workingTitle.entity.ship;

import se.munhunger.workingTitle.entity.projectile.BasicFire;
import se.munhunger.workingTitle.entity.projectile.BasicFlight;
import se.munhunger.workingTitle.entity.projectile.ClusterDeath;
import se.munhunger.workingTitle.entity.projectile.CompleteProjectile;
import se.munhunger.workingTitle.util.Globals;

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
	@Deprecated
	public CompleteProjectile projectile = new CompleteProjectile()
	{
		{
			fireComponents.add(new BasicFire());
			flightComponents.add(new BasicFlight());
		}
	};
	
	/**
	 * @return The projectile from this weapon
	 */
	public CompleteProjectile getProjectile()
	{
		CompleteProjectile projectile = new CompleteProjectile()
		{
			{
				fireComponents.add(new BasicFire());
				flightComponents.add(new BasicFlight(0.7f, 4f));
				deathComponents.add(new ClusterDeath(this));
				from = (Ship) parent;
			}
		};
		projectile.getSize().setXf((float) (parent.getSize().getXf()
				+ Math.cos(parent.getSize().getRotations()) * (getSize().getXf() - 1) * Globals.zoom));
		projectile.getSize().setYf((float) (parent.getSize().getYf()
				+ Math.sin(parent.getSize().getRotations()) * (getSize().getYf() - 1) * Globals.zoom));
		projectile.getSize().setRotation(parent.getSize().getRotations());
		return projectile;
	}
	
	/**
	 * @return The amount of energy required to fire this weapon
	 */
	public float energyRequired()
	{
		return 2f;
	}
	
	/**
	 * Basic constructor that creates a plain Weapon block
	 * 
	 * @param x
	 * the x position
	 * @param y
	 * the y position
	 * @param parent
	 * holding ship entity
	 */
	public Weapon(int x, int y, Ship parent)
	{
		super(x, y, 1f, 0f, 0f, parent);
	}
	
}
