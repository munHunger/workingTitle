package se.munhunger.workingTitle.ai;

import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.util.Globals;

/**
 * This will be a single AI for a single ship
 * 
 * @author munhunger
 * 		
 */
public class ShipAI
{
	/**
	 * This is the ship that the AI is controlling.
	 */
	private Ship ship;
	
	/**
	 * Creates a new basic AI controlling a single ship
	 * 
	 * @param ship
	 */
	public ShipAI(Ship ship)
	{
		this.ship = ship;
	}
	
	/**
	 * Calculates the correct move and takes it.
	 */
	public void step()
	{
		Ship target = AIController.playerShips.get(0);
		float dX = target.getSize().getXf() - ship.getSize().getXf();
		float dY = target.getSize().getYf() - ship.getSize().getYf();
		double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
		ship.getSize().setRotation((float) (Math.atan2(-dX, dY) + Math.PI));
		dX /= length;
		dY /= length;
		dX *= 250 * ship.getSpeed();
		dY *= 250 * ship.getSpeed();
		final float deltaY = dY;
		final float deltaX = dX;
		Globals.worldRoot = Globals.worldRoot.updateObjectPosition(ship.getSize(), () ->
		{
			ship.getSize().setYf(ship.getSize().getYf() + deltaY / 10f);
			ship.getSize().setXf(ship.getSize().getXf() + deltaX / 10f);
		});
		
		// ship.fire();
	}
}
