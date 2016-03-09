package se.munhunger.workingTitle.entity.ship;

/**
 * @author munhunger
 * 		
 */
public class Generator extends ShipBlock
{
	/**
	 * How much energy this block is currently holding
	 */
	private float buffer = 0f;
	
	/**
	 * The last time some block drew energy from this generator
	 */
	private long lastDraw = System.currentTimeMillis();
	
	/**
	 * How much energy this object can possibly store
	 */
	private float maxStorage = 4f;
	
	/**
	 * Basic constructor that creates a plain generator block
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param parent
	 *            holding ship entity
	 */
	public Generator(int x, int y, Ship parent)
	{
		super(x, y, 0.25f, 1f, 0.25f, parent);
	}
	
	/**
	 * @return the max amount of energy from this generator.<br />
	 *         Note that this depletes the generator.
	 */
	public float drawMax()
	{
		autoRefill();
		float ret = buffer;
		buffer = 0f;
		return ret;
	}
	
	/**
	 * Tries to draw an amount of energy from this block
	 * 
	 * @param energy
	 *            the amount of energy to draw
	 * @return true iff this generator can deliver said energy
	 */
	public boolean draw(float energy)
	{
		autoRefill();
		if (buffer >= energy)
		{
			buffer -= energy;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds energy to the buffer
	 * 
	 * @param energy
	 *            the amount of energy to add. Any energy that won't fit will be
	 *            discarded
	 */
	public void refill(float energy)
	{
		autoRefill();
		buffer = Math.min(maxStorage, buffer + energy);
	}
	
	/**
	 * Fills the buffer.
	 * This would represent the generator generating energy
	 */
	private void autoRefill()
	{
		buffer = Math.min(maxStorage, buffer + (System.currentTimeMillis() - lastDraw) / 1000f);
		lastDraw = System.currentTimeMillis();
	}
}
