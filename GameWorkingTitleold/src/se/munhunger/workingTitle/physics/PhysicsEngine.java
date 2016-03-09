package se.munhunger.workingTitle.physics;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import se.munhunger.workingTitle.core.WorkManager;

/**
 * An engine that acts on all physics objects
 * 
 * @author munhunger
 * 		
 */
public class PhysicsEngine
{
	/**
	 * The thread pool that will run all physics calculations.<br />
	 * All Engines should "report" to this pool.<br />
	 * The reason for having this is to be able to split the work into different
	 * threads in a seamless way.<br />
	 */
	private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
	
	/**
	 * Add the pool to the WorkManager
	 */
	static
	{
		WorkManager.AddWorker(pool);
	}
	
	/**
	 * A list of all objects to act on.
	 */
	public ArrayList<PhysicsObject<?>> objects = new ArrayList<>();
	
	/**
	 * The work that is scheduled for this engine instance
	 */
	private ScheduledFuture<?> workFeature;
	
	/**
	 * @return the work feature that this engine instance is running
	 */
	public ScheduledFuture<?> getFeature()
	{
		return workFeature;
	}
	
	/**
	 * Constructor
	 */
	public PhysicsEngine()
	{
		workFeature = pool.scheduleAtFixedRate(() ->
		{
			for (PhysicsObject<?> o : objects)
			{
				o.act();
			}
		} , 0, 50, TimeUnit.MILLISECONDS);
	}
}
