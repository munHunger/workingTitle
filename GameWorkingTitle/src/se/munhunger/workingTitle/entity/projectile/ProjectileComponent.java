package se.munhunger.workingTitle.entity.projectile;

/**
 * A component of a projectile
 * 
 * @author munhunger
 * 		
 */
public interface ProjectileComponent
{
	/**
	 * @return true if the component has run it's course and is out of energy
	 */
	public boolean isDrained();
}
