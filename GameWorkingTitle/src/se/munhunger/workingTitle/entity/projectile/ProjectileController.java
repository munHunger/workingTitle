package se.munhunger.workingTitle.entity.projectile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import se.munhunger.workingTitle.core.WorkManager;
import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.Tile;
import se.munhunger.workingTitle.entity.ship.ShipBlock;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.Log;
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
	 * Buffer where projectiles should be added into.
	 * These will be moved to {@link #activeProjectiles} by the controller
	 */
	private static Collection<SizedObject<Entity>> newProjectileBuffer = new ArrayList<SizedObject<Entity>>();
	
	/**
	 * The thread pool that will run all projectile calculations.<br />
	 * The reason for having this is to be able to split the work into different
	 * threads in a seamless way and being able to schedule work.<br />
	 */
	// TODO Change back to private once debugging is done
	public static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
	
	/**
	 * Add the pool to the WorkManager
	 */
	static
	{
		Log.info("Creating Projectile controller", new ProjectileController());
		WorkManager.AddWorker(pool);
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					long startTime = System.currentTimeMillis();
					Log.info("ProjectileController starting on schedule " + startTime, new ProjectileController());
					Iterator<SizedObject<Entity>> it = activeProjectiles.iterator();
					while (it.hasNext())
					{
						SizedObject<Entity> projectile = it.next();
						Log.info("Working on active projectile", new ProjectileController());
						CompleteProjectile cp = (CompleteProjectile) projectile.getObject();
						switch (cp.currentState)
						{
							case FLIGHT:
								if (cp.flightComponents.isEmpty())
									cp.advance();
								for (FlightComponent fl : cp.flightComponents)
								{
									if (!fl.isDrained())
										fl.fly(projectile);
									else
										cp.advance();
								}
								Collection<SizedObject<Entity>> intersect = Globals.worldRoot
										.getIntersect(projectile.getBounds());
								for (SizedObject<Entity> entity : intersect)
								{
									if (!(entity.getObject() instanceof CompleteProjectile)
											&& entity.getObject() != cp.from)
									{
										Collection<SizedObject<Tile>> hitTiles = cp.getParts().getIntersect(
												projectile.getRotations(), projectile.getWidth() / 2f,
												projectile.getHeight() / 2f, entity.getObject().getParts(), 1,
												entity.getRotations(), entity.getXf() - projectile.getXf(),
												entity.getYf() - projectile.getYf(),
												entity.getXf() + entity.getWidth() / 2 - projectile.getXf(),
												entity.getYf() + entity.getHeight() / 2 - projectile.getYf());
										int resources = 0;
										for (SizedObject<Tile> t : hitTiles)
										{
											if (t.getObject() instanceof ShipBlock)
											{
												ShipBlock block = (ShipBlock) t.getObject();
												for (FlightComponent fl : cp.flightComponents)
													if (block.damage(fl.getEnergy() * 5f))
													{
														resources++;
														entity.getObject().getParts().remove(t, true);
														break;
													}
											}
											t.getObject().color = t.getObject().color.darker();
										}
										if (resources > 0)
											cp.from.addResources(resources);
										if (!hitTiles.isEmpty())
											cp.currentState = CompleteProjectile.State.HIT;
									}
								}
								break;
							case HIT:
								if (cp.hitComponents.isEmpty())
									cp.advance();
								for (HitComponent hi : cp.hitComponents)
								{
								}
								break;
								
							case DEATH:
								if (cp.deathComponents.isEmpty())
									cp.advance();
								for (DeathComponent de : cp.deathComponents)
								{
									if (!de.isDrained())
										de.die();
									else
										cp.advance();
								}
								break;
							case REMOVE:
								it.remove();
								Globals.worldRoot.remove(projectile, true);
								break;
							default:
								Log.error(null, "Projectile in state that it should not be in", this);
						}
					}
					activeProjectiles.addAll(newProjectileBuffer);
					long stopTime = System.currentTimeMillis();
					Log.info("ProjectileController completing on schedule " + stopTime, new ProjectileController());
					
					if (stopTime - startTime < 30)
					{
						try
						{
							Thread.sleep(30 - (stopTime - startTime));
						}
						catch (InterruptedException e)
						{
							Log.fatal(e, "Could not sleep projectile controll thread", new ProjectileController());
						}
					}
					else
						Log.warn(null, "Could not keep up with projectile tick rate", new ProjectileController());
				}
			}
			
		});
		t.start();
	}
	
	/**
	 * Adds a projectile to the controller
	 * 
	 * @param projectile
	 *            The projectile to handle
	 */
	public static void addProjectile(SizedObject<Entity> projectile)
	{
		Globals.worldRoot = Globals.worldRoot.add(projectile);
		CompleteProjectile cp = (CompleteProjectile) projectile.getObject();
		for (FireComponent comp : cp.fireComponents)
			comp.fire();
		cp.advance();
		newProjectileBuffer.add(projectile);
	}
}
