package se.munhunger.workingTitle.util;

import se.munhunger.workingTitle.entity.Entity;

/**
 * Globals class holds everything that should be considered global and visible
 * to every object in the game
 * 
 * @author munhunger
 * 		
 */
public class Globals
{
	/**
	 * This is the root of the entire world.
	 * It's reference should be kept up to date, i.e. if the root of the tree
	 * changes, then so should this reference
	 */
	public static QuadTree<Entity> worldRoot;
	
	/**
	 * Notes how much to zoom the game graphic wise
	 */
	public static float zoom = 10f;
}
