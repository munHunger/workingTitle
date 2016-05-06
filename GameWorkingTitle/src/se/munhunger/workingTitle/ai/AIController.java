package se.munhunger.workingTitle.ai;

import java.util.ArrayList;

import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.entity.ship.Ship.ShipType;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.Log;

/**
 * Manages all AI:s to make sure that everyone gets calculated on time
 * 
 * @author munhunger
 * 		
 */
public class AIController implements Runnable
{
	/**
	 * A list of all player ships.
	 * This will in turn indicate all ships that the AI will target
	 */
	public static ArrayList<Ship> playerShips = new ArrayList<>();
	
	/**
	 * A list of all AI:s to manage
	 */
	public static ArrayList<ShipAI> ai = new ArrayList<>();
	
	static
	{
		Ship s = new Ship(ShipType.SLIM, false);
		s.getSize().setX(150);
		s.getSize().setY(-150);
		Globals.worldRoot = Globals.worldRoot.add(s.getSize());
		ai.add(new ShipAI(s));
		
		new Thread(new AIController()).start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			for (ShipAI ai : ai)
				ai.step();
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				Log.error(e, "Could not sleep AI Controller thread", this);
			}
		}
	}
}
