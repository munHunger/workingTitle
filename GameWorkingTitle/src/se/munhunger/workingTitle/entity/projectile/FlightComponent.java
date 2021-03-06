package se.munhunger.workingTitle.entity.projectile;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * The action to take during each projectile tick when it is in flight. i.e
 * moving forward with some energy left and without hitting an object
 * 
 * @author munhunger
 * 		
 */
public interface FlightComponent extends ProjectileComponent
{
	/**
	 * Action to take every tick when this projectile should move
	 * 
	 * @param size
	 *            the bouning of the projectile
	 */
	public void fly(SizedObject<Entity> size);
	
	/**
	 * @return the energy amount of this projectile. This will result in damage,
	 *         i.e. more energy more damage
	 */
	public float getEnergy();
}
