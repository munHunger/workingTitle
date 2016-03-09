package se.munhunger.workingTitle.entity.projectile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import se.munhunger.workingTitle.core.WorkManager;
import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Controll class that makes sure that all projectiles are handled correctly
 * 
 * @author munhunger
 * 		
 */
public class ProjectileController
{
	/**
	 * List of all projectiles that are active in the world
	 */
	private static Collection<SizedObject<Entity>> activeProjectiles = new ArrayList<SizedObject<Entity>>();
	
	/**
	 * The thread pool that will run all projectile calculations.<br />
	 * The reason for having this is to be able to split the work into different
	 * threads in a seamless way and being able to schedule work.<br />
	 */
	private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
	
	/**
	 * Add the pool to the WorkManager
	 */
	static
	{
		WorkManager.AddWorker(pool);
		pool.scheduleAtFixedRate(() ->
		{
			for(SizedObject<Entity> projectile : activeProjectiles)
			{
				CompleteProjectile cp = (CompleteProjectile)projectile.getObject();
				for(FlightComponent fl : cp.flightComponents)
				{
					fl.fly(projectile);
				}
			}
		} , 0, 5, TimeUnit.MILLISECONDS);
	}
	
	
	/**
	 * Adds a projectile to the controller
	 * 
	 * @param projectile
	 *            The projectile to handle
	 */
	public static void addProjectile(SizedObject<Entity> projectile)
	{
		CompleteProjectile cp = (CompleteProjectile)projectile.getObject();
		for (FireComponent comp : cp.fireComponents)
			comp.fire();
		activeProjectiles.add(projectile);
		Globals.worldRoot = Globals.worldRoot.add((SizedObject<Entity>)projectile);
	}
}
