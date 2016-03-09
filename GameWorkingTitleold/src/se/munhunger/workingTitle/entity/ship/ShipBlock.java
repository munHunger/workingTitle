package se.munhunger.workingTitle.entity.ship;

import se.munhunger.workingTitle.entity.Tile;

/**
 * Denotes an block that have health and can possibly withstand some damage
 * 
 * @author munhunger
 * 		
 */
public class ShipBlock extends Tile
{
	/**
	 * The health of this Armor block.
	 * Goes from 0(dead) to 1(fully healed)
	 */
	private float health = 1f;
	
	/**
	 * The modifier for how much damage to take from a hit.
	 * 0 being no damage taken and block is imortal.
	 * 1 being full damage and block does not have any armor effects.
	 */
	private float armorModifier = 0.25f;
	
	/**
	 * Basic constructor that creates a gray armor block
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public ShipBlock(int x, int y)
	{
		super(x, y, 0.25f, 0.25f, 0.25f);
	}
	
	/**
	 * Constructor
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param r
	 *            red component of the tiles color
	 * @param g
	 *            green component of the tiles color
	 * @param b
	 *            blue component of the tiles color
	 * @throws IllegalArgumentException
	 *             if r,g or b is not in the range 0<=component<=1
	 */
	protected ShipBlock(int x, int y, float r, float g, float b) throws IllegalArgumentException
	{
		super(x, y, r, g, b);
	}
}
