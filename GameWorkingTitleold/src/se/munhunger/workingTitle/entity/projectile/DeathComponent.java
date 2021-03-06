package se.munhunger.workingTitle.entity.projectile;

/**
 * The action to take when the energy has gone out of the projectile and it
 * should be removed from the world. This interface makes it possible to go out
 * with a bang.
 * 
 * @author munhunger
 * 		
 */
@FunctionalInterface
public interface DeathComponent
{
	/**
	 * Function to call when the projectile has run out of energy and missed a target
	 */
	public void die();
}
