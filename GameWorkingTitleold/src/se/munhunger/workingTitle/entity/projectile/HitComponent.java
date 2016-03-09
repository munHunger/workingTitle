package se.munhunger.workingTitle.entity.projectile;

/**
 * The action to take when hitting an object
 * 
 * @author munhunger
 *		
 */
@FunctionalInterface
public interface HitComponent
{
	/**
	 * Function to call when the projectile hits an entitiy
	 */
	public void hit();
}
