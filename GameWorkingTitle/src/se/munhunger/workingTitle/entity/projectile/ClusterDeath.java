package se.munhunger.workingTitle.entity.projectile;

/**
 * Basic death component
 * 
 * @author munhunger
 * 		
 */
public class ClusterDeath implements DeathComponent
{
	
	/**
	 * Bool to determine if this component has run its course
	 */
	private boolean drained = false;
	/**
	 * The energy to use in this component. This will note how many particles to
	 * spawn
	 */
	private float energy = 1f;
	/**
	 * The parent projectile. Used to determine where to spawn the new
	 * projectiles
	 */
	private CompleteProjectile cp;
	
	/**
	 * Basic constructor
	 * 
	 * @param cp
	 *            the parent projectile
	 */
	public ClusterDeath(CompleteProjectile cp)
	{
		this.cp = cp;
	}
	
	@Override
	public void die()
	{
		for (int i = 0; i < energy * 6; i++)
		{
			CompleteProjectile projectile = new CompleteProjectile()
			{
				{
					fireComponents.add(new BasicFire());
					flightComponents.add(new BasicFlight(0.4f, 2f));
				}
			};
			projectile.getSize().setXf(cp.getSize().getXf());
			projectile.getSize().setYf(cp.getSize().getYf());
			projectile.getSize().setRotation((float) (Math.PI / 3) * i);
			
			ProjectileController.addProjectile(projectile.getSize());
		}
		drained = true;
	}
	
	@Override
	public boolean isDrained()
	{
		return drained;
	}
	
}
