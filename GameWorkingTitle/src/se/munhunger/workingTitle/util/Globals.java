package se.munhunger.workingTitle.util;

import java.util.Random;

import javax.swing.JComponent;

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
	 * This is the root of the entire world. It's reference should be kept up to
	 * date, i.e. if the root of the tree changes, then so should this reference
	 */
	public static QuadTree<Entity> worldRoot;
	
	/**
	 * Notes how much to zoom the game graphic wise
	 */
	public static float zoom = 10f;
	
	/**
	 * Graphical offset
	 */
	public static float xOffset = 100f;
	/**
	 * Graphical offset
	 */
	public static float yOffset = 100f;
	/**
	 * Random object. Should be used for all random needs. This is usefull, as
	 * it can take a seed
	 */
	public static Random random = new Random(42L);
	
	/**
	 * The component to paint the world onto
	 */
	public static JComponent canvas;
	
}
