package se.munhunger.workingTitle.core;

import java.util.concurrent.ExecutorService;

import se.munhunger.workingTitle.util.Log;

/**
 * Will keep track of all components that needs to work.<br />
 * For example the physics pool, the general game pool, network pool....
 * 
 * @author munhunger
 * 
 */
public class WorkManager
{
	/**
	 * Adds a worker to this manager
	 * 
	 * @param worker
	 *            the threadpool to manage
	 */
	public static void AddWorker(ExecutorService worker)
	{
		// TODO something reasonable
		Log.info("Worker registered.", new WorkManager());
	}
}
