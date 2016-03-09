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
@FunctionalInterface
public interface FlightComponent
{
	/**
	 * Action to take every tick when this projectile should move
	 * @param size the bouning of the projectile
	 */
	public void fly(SizedObject<Entity> size);
}
