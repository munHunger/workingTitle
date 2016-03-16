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
	/**
	 * The amount of energy to spend.
	 * This will reflect how many ticks it should be in this state
	 */
	private float energy = 1f;
	/**
	 * How far to travel each step
	 */
	private float speed = 4f;
	
	/**
	 * Empty constructor to create a basic flight component with default values
	 */
	public BasicFlight()
	{
	
	}
	
	/**
	 * Constructor to create a basic flight component with some given starting
	 * values
	 * 
	 * @param energy
	 * {@link #energy}
	 * @param speed
	 * {@link #speed}
	 */
	public BasicFlight(float energy, float speed)
	{
		this.energy = energy;
		this.speed = speed;
	}
	
	@Override
	public void fly(SizedObject<Entity> projectile)
	{
		Globals.worldRoot = Globals.worldRoot.updateObjectPosition(projectile, () ->
		{
			projectile.setXf(projectile.getXf() + (float) (Math.cos(projectile.getRotations() - Math.PI / 2) * speed));
			projectile.setYf(projectile.getYf() + (float) (Math.sin(projectile.getRotations() - Math.PI / 2) * speed));
		});
		
		energy -= 0.005f;
	}
	
	@Override
	public boolean isDrained()
	{
		return energy < 0f;
	}
}
