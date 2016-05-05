package se.munhunger.workingTitle.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import se.munhunger.workingTitle.graphics.Paintable;
import se.munhunger.workingTitle.graphics.ui.Text;
import se.munhunger.workingTitle.graphics.ui.Text.TextMode;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.Log;
import se.munhunger.workingTitle.util.QuadTree;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Represents an object that can be held inside the world
 * 
 * @author munhunger
 * 		
 */
public class Entity implements Paintable
{
	/**
	 * A tree of all the parts contained in this Entity
	 */
	protected QuadTree<Tile> parts;
	
	/**
	 * Size wrapper to keep the general size of this object
	 */
	private SizedObject<Entity> size;
	
	/**
	 * Notes the amount of resources in this entity<br />
	 * Resources is a type of currency that can be used to "buy" new parts for
	 * the entity
	 */
	private int resources = 0;
	
	/**
	 * Simple constructor
	 */
	public Entity()
	{
		parts = new QuadTree<>(0, 0, 2, 2);
	}
	
	/**
	 * @return a new entity object that represents a very basic spaceship
	 */
	@Deprecated
	public static Entity generateBasicShipStructure()
	{
		Entity ship = new Entity();
		Tile t = new Tile(0, 0, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(0, 1, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(1, 2, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(2, 2, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(3, 2, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(4, 1, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(4, 0, 0.2f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		
		t = new Tile(1, 1, 0.75f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		t = new Tile(3, 1, 0.75f, 0.2f, 0.2f);
		ship.parts = ship.parts.add(t.getSize());
		ship.size = new SizedObject<Entity>(ship, 0, 0, 4, 3);
		return ship;
	}
	
	/**
	 * Calculates what size the entity has based on its tiles
	 */
	protected void validateSize()
	{
		int lowX = Integer.MAX_VALUE;
		int highX = Integer.MIN_VALUE;
		
		int lowY = Integer.MAX_VALUE;
		int highY = Integer.MIN_VALUE;
		for (SizedObject<Tile> t : parts.getAll())
		{
			lowX = Math.min(lowX, t.getX());
			lowY = Math.min(lowY, t.getY());
			
			highX = Math.max(highX, t.getX());
			highY = Math.max(highY, t.getY());
		}
		if (size == null)
			size = new SizedObject<Entity>(this, 0, 0, 0, 0);
		size.setX(lowX);
		size.setY(lowY);
		size.setWidth((int) ((Math.max(1, highX - lowX) + 1) * Globals.zoom));
		size.setHeight((int) ((Math.max(1, highY - lowY) + 1) * Globals.zoom));
	}
	
	/**
	 * @return the sizeWrapper of this object
	 */
	public SizedObject<Entity> getSize()
	{
		return size;
	}
	
	/**
	 * @return The quadtree that holds information about what tiles are in this
	 *         entity and where they are
	 */
	public QuadTree<Tile> getParts()
	{
		return parts;
	}
	
	/**
	 * A collection of all texts to display above the entity, and when the text
	 * was added.
	 */
	private Collection<Entry<String, Long>> texts = new ArrayList<>();
	
	/**
	 * A lock to make sure that only one thread is working on {@link #texts} at
	 * a time
	 */
	private Semaphore textLock = new Semaphore(1);
	
	/**
	 * Adds a text to display above the entity
	 * 
	 * @param text
	 *            the text to display
	 */
	public void addText(String text)
	{
		try
		{
			textLock.acquire();
			texts.add(new AbstractMap.SimpleEntry<String, Long>(text, System.currentTimeMillis()));
			textLock.release();
		}
		catch (InterruptedException e)
		{
			Log.error(e, "Interrupted while trying to add text to entity", this);
		}
	}
	
	/**
	 * Adds resources to this entity.
	 * This will also add a text above the entity saying that it got(or lost)
	 * some new
	 * resources
	 * 
	 * @param amount
	 *            the amount of resources to add
	 */
	public void addResources(int amount)
	{
		resources += amount;
		addText((amount > 0 ? "+" : "-") + amount + " resource" + (Math.abs(amount) > 1 ? "s" : ""));
	}
	
	/**
	 * @return the amount of resources held by this object
	 */
	public int getResources()
	{
		return resources;
	}
	
	/**
	 * Sets the amount of resources for this object to hold
	 * 
	 * @param amount
	 */
	public void setResources(int amount)
	{
		this.resources = amount;
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		g2d.drawRect((int) (size.getXf() + xOffset), (int) (size.getYf() + yOffset), (size.getWidth()),
				(size.getHeight()));
		parts.getIntersect(parts.getBounds());
		BufferedImage image = new BufferedImage(((size.getWidth() + 1)), ((size.getHeight() + 1)),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D imageGraphics = image.createGraphics();
		for (SizedObject<Tile> t : parts.getAll())
		{
			t.getObject().paint(imageGraphics, 0, 0, displace);
		}
		AffineTransform oldAT = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		int x = (int) (size.getXf() + xOffset);
		int y = (int) (size.getYf() + yOffset);
		at.setToRotation(size.getRotations(), x + (image.getWidth() / 2), y + (image.getHeight() / 2));
		at.translate(x, y);
		g2d.setTransform(at);
		g2d.drawImage(image, 0, 0, null);
		g2d.setTransform(oldAT);
		
		try
		{
			textLock.acquire();
			long currentTime = System.currentTimeMillis();
			Iterator<Entry<String, Long>> it = texts.iterator();
			while (it.hasNext())
			{
				Entry<String, Long> entry = it.next();
				if (currentTime - entry.getValue() > 2000)
					it.remove();
				Text.paintString(g2d, entry.getKey(), (int) (xOffset + size.getX() + size.getWidth() / 2),
						(int) (yOffset + size.getY() - 10 - (currentTime - entry.getValue()) / 100), TextMode.CENTER);
			}
			textLock.release();
		}
		catch (InterruptedException e)
		{
			Log.error(e, "Interrupted while trying to add text to entity", this);
		}
	}
}
