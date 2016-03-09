package se.munhunger.workingTitle.physics;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Extends SizedObject by adding force.
 * Force will keep an object in motion.
 * 
 * @author munhunger
 * @param <T> The object to wrap
 *		
 */
public class PhysicsObject<T> extends SizedObject<T>
{
	/**
	 * Constructor
	 * 
	 * @param object
	 *            the object to wrap
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param width
	 *            the width of the object
	 * @param height
	 *            the height of the object
	 */
	public PhysicsObject(T object, int x, int y, int width, int height)
	{
		super(object, x, y, width, height);
	}

	/**
	 * The force to act on the object.
	 * A high x force will result in a lot of movement in x axis
	 */
	private float xForce = 0f;
	
	/**
	 * The force to act on the object.
	 * A high y force will result in a lot of movement in y axis
	 */
	private float yForce = 0f;
	
	/**
	 * Acts the force on the object, thus moving in space
	 */
	@SuppressWarnings("unchecked")
	public void act()
	{
		Globals.worldRoot = Globals.worldRoot.updateObjectPosition((SizedObject<Entity>) this, () ->
		{
			super.setYf(super.getYf() + yForce);
			super.setXf(super.getXf() + xForce);
		});
	}
}
