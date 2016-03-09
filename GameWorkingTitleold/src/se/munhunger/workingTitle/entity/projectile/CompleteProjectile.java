package se.munhunger.workingTitle.entity.projectile;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.ship.ShipBlock;

/**
 * Notes a projectile that have all the needed parts to create a projectile.
 * 
 * @author munhunger
 *		
 */
public abstract class CompleteProjectile extends Entity
{
	/**
	 * Components that should be triggered when the projectile is spawned
	 */
	public Collection<FireComponent> fireComponents = new ArrayList<FireComponent>();
	
	/**
	 * Components that should be triggered on each flight tick
	 */
	public Collection<FlightComponent> flightComponents = new ArrayList<FlightComponent>();
	
	/**
	 * Components that should be triggered when the projectile hits an object
	 */
	public Collection<HitComponent> hitComponents = new ArrayList<HitComponent>();
	
	/**
	 * Components that should be triggered when the projectile has traveled as far as it should and have not hit a target
	 */
	public Collection<DeathComponent> deathComponents = new ArrayList<DeathComponent>();
	
	{
		parts = parts.add(new ShipBlock(0, 0).getSize());
		validateSize();
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		super.paint(g2d, xOffset, yOffset, displace);
	}
}
