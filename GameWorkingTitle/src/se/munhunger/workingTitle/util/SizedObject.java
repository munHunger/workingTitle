package se.munhunger.workingTitle.util;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * A wrapper for an object that adds the functionality of having a size in a
 * more physical manner. i.e. an object with a width and height. It also has the
 * added bonus of having a position.
 * 
 * @author munhunger
 * 
 * @param <T>
 * the type of the wrapped object
 */
public class SizedObject<T>
{
	/**
	 * Rectangle will Note the bounds of the object. It holds all needed
	 * information such as x/y position and width/height
	 */
	private Rectangle outline = null;
	
	/**
	 * The x position of the object. This is on a micro level beside the
	 * position of the outline and can be used for minute positioning
	 */
	private float x;
	
	/**
	 * The y position of the object. This is on a micro level beside the
	 * position of the outline and can be used for minute positioning
	 */
	private float y;
	
	/**
	 * Rotation of object noted in radians
	 */
	private float rotation;
	/**
	 * The object that is being wrapped
	 */
	private T object = null;
	
	/**
	 * Constructor
	 * 
	 * @param object
	 * the object to wrap
	 * @param x
	 * x-coordinate
	 * @param y
	 * y-coordinate
	 * @param width
	 * the width of the object
	 * @param height
	 * the height of the object
	 */
	public SizedObject(T object, int x, int y, int width, int height)
	{
		this.object = object;
		outline = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks if this object contains other
	 * 
	 * @param other
	 * the object to check against
	 * @return true iff this SizedObject completely envelops the other
	 * SizedObject
	 */
	public boolean contains(SizedObject<?> other)
	{
		return outline.contains(other.outline);
	}
	
	/**
	 * Checks if this objects contains the rectangle
	 * 
	 * @param bounds
	 * the rectangle to check
	 * @return true iff this object completely envelops the bounds object
	 */
	public boolean contains(Rectangle bounds)
	{
		return outline.contains(bounds);
	}
	
	/**
	 * Checks if this object contains the point with the coordinates x,y
	 * 
	 * @param x
	 * x-coordinate
	 * @param y
	 * y-coordinate
	 * @return true iff this objects contains the x,y coordinate
	 */
	public boolean contains(int x, int y)
	{
		return outline.contains(x, y);
	}
	
	/**
	 * Checks if this object is intersected by other
	 * 
	 * @param other
	 * the object to check against
	 * @return true iff this object is intersected by other, i.e. if they have
	 * any point in common.
	 */
	public boolean intersects(SizedObject<?> other)
	{
		return outline.intersects(other.outline);
	}
	
	/**
	 * Checks if this object is intersected by bounds
	 * 
	 * @param bounds
	 * the object to check against
	 * @return true iff this object is intersected by bounds, i.e. if they have
	 * any point in common.
	 */
	public boolean intersects(Rectangle bounds)
	{
		return outline.intersects(bounds);
	}
	
	/**
	 * Checks if the line intersects this object
	 * 
	 * @param line
	 * @return true iff this object intersects the line
	 */
	public boolean intersects(Line2D line)
	{
		return outline.intersectsLine(line);
	}
	
	/**
	 * Checks if a line constructed by (x1,y1) to (x2,y2) intersects this object
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return true iff this object intersects the line
	 */
	public boolean intersects(int x1, int y1, int x2, int y2)
	{
		return outline.intersectsLine(new Line2D.Double(x1, y1, x2, y2));
	}
	
	/**
	 * @return the wrapped object
	 */
	public T getObject()
	{
		return object;
	}
	
	/**
	 * Sets the wrapped object
	 * 
	 * @param object
	 * the object to set
	 */
	public void setObject(T object)
	{
		this.object = object;
	}
	
	/**
	 * @return this objects x-coordinate
	 */
	public int getX()
	{
		return outline.x;
	}
	
	/**
	 * @return this objects y-coordinate
	 */
	public int getY()
	{
		return outline.y;
	}
	
	/**
	 * @return this objects x-coordinate in float.
	 */
	public float getXf()
	{
		return x;
	}
	
	/**
	 * @return this objects y-coordinate in float
	 */
	public float getYf()
	{
		return y;
	}
	
	/**
	 * @return the rotation of this object in radians
	 */
	public float getRotations()
	{
		return rotation;
	}
	
	/**
	 * @return this objects height
	 */
	public int getHeight()
	{
		return outline.height;
	}
	
	/**
	 * @return this objects width
	 */
	public int getWidth()
	{
		return outline.width;
	}
	
	/**
	 * Sets the height of this object
	 * 
	 * @param height
	 */
	public void setHeight(int height)
	{
		outline.height = height;
	}
	
	/**
	 * Sets the width of this object
	 * 
	 * @param width
	 */
	public void setWidth(int width)
	{
		outline.width = width;
	}
	
	/**
	 * Sets the size of this object
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height)
	{
		setHeight(height);
		setWidth(width);
	}
	
	/**
	 * Sets the x position of this object
	 * 
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
		outline.x = x;
	}
	
	/**
	 * Sets the y position of this object
	 * 
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
		outline.y = y;
	}
	
	/**
	 * Sets the x position of this object this should be close to the bounds x
	 * position It does also alter the position of the bounds to an integer
	 * rounded x
	 * 
	 * @param x
	 */
	public void setXf(float x)
	{
		this.x = x;
		outline.x = (int) x;
	}
	
	/**
	 * Sets the y position of this object this should be close to the bounds y
	 * position It does also alter the position of the bounds to an integer
	 * rounded y
	 * 
	 * @param y
	 */
	public void setYf(float y)
	{
		this.y = y;
		outline.y = (int) y;
	}
	
	/**
	 * Sets the position of this object
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the rotation of the object
	 * 
	 * @param r
	 * rotation in radians
	 */
	public void setRotation(float r)
	{
		rotation = r;
	}
	
	/**
	 * Sets the bounds for this object. i.e. position and size
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height)
	{
		setSize(width, height);
		setPosition(x, y);
	}
	
	/**
	 * @return the outline(or bounds) of this sized object
	 */
	public Rectangle getBounds()
	{
		return outline;
	}
	
	@Override
	public String toString()
	{
		return String.format("%f,%f %dx%d\t", x, y, getWidth(), getHeight()) + object;
	}
}
