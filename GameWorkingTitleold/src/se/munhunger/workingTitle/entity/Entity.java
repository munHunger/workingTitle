package se.munhunger.workingTitle.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import se.munhunger.workingTitle.graphics.Paintable;
import se.munhunger.workingTitle.util.Globals;
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
		for (SizedObject<Tile> t : parts.getIntersect(parts.getBounds()))
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
		size.setWidth(highX - lowX);
		size.setHeight(highY - lowY);
	}
	
	/**
	 * @return the sizeWrapper of this object
	 */
	public SizedObject<Entity> getSize()
	{
		return size;
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		float zoom = Globals.zoom;
		parts.getIntersect(parts.getBounds());
		BufferedImage image = new BufferedImage((int) ((size.getWidth()+1) * zoom), (int) ((size.getHeight()+1) * zoom),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D imageGraphics = image.createGraphics();
		for (SizedObject<Tile> t : parts.getAll())
		{
			t.getObject().paint(imageGraphics, 0, 0, displace);
		}
		AffineTransform at = new AffineTransform();
		int x = (int) (size.getXf() + xOffset);
		int y = (int) (size.getYf() + yOffset);
		at.setToRotation(size.getRotations(), x+(image.getWidth() / 2), y+(image.getHeight() / 2));
		at.translate(x, y);
		g2d.setTransform(at);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
}
