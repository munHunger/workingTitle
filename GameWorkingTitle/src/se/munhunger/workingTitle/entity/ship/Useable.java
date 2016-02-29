package se.munhunger.workingTitle.entity.ship;

/**
 * A useable block is a block that can be triggered by the user
 * 
 * @author munhunger
 *		
 */
public interface Useable
{
	/**
	 * Trigger the event associated with this block.<br />
	 * This trigger should be fired by a user
	 */
	public void use();
}
