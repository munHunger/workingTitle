package se.munhunger.workingTitle.entity.projectile;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * A basic flight projectile, just moving forward and doing nothing special
 * 
 * @author munhunger
 *		
 */
public class BasicFlight implements FlightComponent
{
	@Override
	public void fly(SizedObject<Entity> projectile)
	{
		//System.out.println(String.format("I AM FLYING FROM %d,%d", projectile.getX(), projectile.getY()));
		Globals.worldRoot = Globals.worldRoot.updateObjectPosition(projectile, () ->
		{
			//projectile.setXf(projectile.getXf()+1.5f);
			//projectile.setYf(projectile.getYf()+0.5f);
		});
	}
}
