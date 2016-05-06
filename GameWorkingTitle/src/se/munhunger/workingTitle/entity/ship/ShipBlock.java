package se.munhunger.workingTitle.entity.ship;

import java.util.ArrayList;

import se.munhunger.workingTitle.entity.Entity;
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
	 * A list of all blocks that have been instanciated
	 */
	public static ArrayList<ShipBlock> possibleBlocks = new ArrayList<>();
	
	static
	{
		possibleBlocks.add(new ShipBlock(0, 0, null));
	}
	
	/**
	 * The health of this Armor block. Goes from 0(dead) to 1(fully healed)
	 */
	private float health = 1f;
	
	/**
	 * The modifier for how much damage to take from a hit. 0 being no damage
	 * taken and block is imortal. 1 being full damage and block does not have
	 * any armor effects.
	 */
	private float armorModifier = 0.25f;
	
	/**
	 * The parent entity.
	 * Can be used to find out the "real" position after rotating and moving the
	 * entity
	 */
	protected Entity parent;
	
	/**
	 * Basic constructor that creates a gray armor block
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param parent
	 *            The holding entity
	 */
	public ShipBlock(int x, int y, Entity parent)
	{
		super(x, y, 0.25f, 0.25f, 0.25f);
		this.parent = parent;
	}
	
	/**
	 * Damages this tile.
	 * It's armor modifier will be applied to the damage
	 * 
	 * @param damage
	 *            the amount of damage to deal
	 * @return true if the tile is "dead" i.e. health below 0
	 */
	public boolean damage(float damage)
	{
		health -= damage * armorModifier;
		return health < 0f;
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
	 * @param parent
	 *            The holding entity
	 * @throws IllegalArgumentException
	 *             if r,g or b is not in the range 0<=component<=1
	 */
	public ShipBlock(int x, int y, float r, float g, float b, Entity parent) throws IllegalArgumentException
	{
		super(x, y, r, g, b);
		this.parent = parent;
	}
	
	/**
	 * Clones the shipblock.
	 * This will be a shallow clone.
	 * The cloned shipblock will have a new x,y position and a new parent(or
	 * possibly the same)
	 * 
	 * @param x
	 * @param y
	 * @param ship
	 *            parent ship
	 * @return a cloned shipblock
	 */
	public ShipBlock clone(int x, int y, Ship ship)
	{
		return new ShipBlock(x, y, ship);
	}
}
