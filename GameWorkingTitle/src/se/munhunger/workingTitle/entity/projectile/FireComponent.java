package se.munhunger.workingTitle.entity.projectile;

/**
 * Action to take at the birth of a projectile
 * @author munhunger
 *
 */
@FunctionalInterface
public interface FireComponent
{
	/**
	 * Function to call when spawning a projectile.<br />
	 * This should be the function that is creating the projectile
	 */
	public void fire();
}
