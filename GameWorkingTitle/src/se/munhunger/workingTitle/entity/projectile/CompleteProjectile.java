package se.munhunger.workingTitle.entity.projectile;

import java.util.Collection;

/**
 * Notes a projectile that have all the needed parts to create a projectile.
 * 
 * @author munhunger
 *		
 */
public interface CompleteProjectile
{
	/**
	 * Components that should be triggered when the projectile is spawned
	 */
	public Collection<FireComponent> fireComponents = null;
	
	/**
	 * Components that should be triggered on each flight tick
	 */
	public Collection<FlightComponent> flightComponents = null;
	
	/**
	 * Components that should be triggered when the projectile hits an object
	 */
	public Collection<HitComponent> hitComponents = null;
	
	/**
	 * Components that should be triggered when the projectile has traveled as far as it should and have not hit a target
	 */
	public Collection<DeathComponent> deathComponents = null;
}
