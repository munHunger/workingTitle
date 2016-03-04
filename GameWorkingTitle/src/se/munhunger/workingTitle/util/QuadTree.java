package se.munhunger.workingTitle.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;

import se.munhunger.workingTitle.graphics.Paintable;

/**
 * A quadtree implementation working with rectangles and points. i.e. If circles
 * are desired, one must rasterize the circle with multiple rectangles.
 * It stores and queries against rectangles and points.<br />
 * The quadtree partitions space as required, and can potentially grow to have a
 * quite large overhead.
 * For this reason it is a good idea to set the initial size of the tree quite
 * small, just large enough to encompass the needs of the application.<br />
 * The tree selfprunes itself when needed. Meaning that when objects are
 * removed, parts of the tree might be removed aswell.<br />
 * It also self inflates, meaning that when a shape is added outside of the tree
 * it will create a new root. This means that some caution is necessary as
 * adding two shapes far away from each other can result in the tree inflating
 * more than desired
 * 
 * @author munhunger
 * 		
 * @param <T>
 *            the type of objects to store
 */
public class QuadTree<T> implements Paintable
{
	/**
	 * A list will all objects covered by this quadtree where the objects
	 * intersects the horizontal mid line.<br />
	 * Note that this horizontal list has precedence over the vertical list
	 */
	private ArrayList<SizedObject<T>> horizontal = new ArrayList<>();
	/**
	 * A list will all objects covered by this quadtree where the objects
	 * intersects the vertical mid line.<br />
	 * Note that the horizontal list has precedence over the vertical list
	 */
	private ArrayList<SizedObject<T>> vertical = new ArrayList<>();
	/**
	 * A line representing the horizontal mid line. This line is perfectly
	 * straight
	 */
	private Line2D horizontalMidLine;
	/**
	 * A line representing the vertical mid line. This line is perfectly
	 * straight
	 */
	private Line2D verticalMidLine;
	/**
	 * A wrapper to note the size of this QuadTree
	 */
	private SizedObject<QuadTree<T>> sizeWrapper;
	/**
	 * The parent node of this node.
	 * The parent should reasonably have this tree as one of its children.
	 */
	private QuadTree<T> parent = null;
	
	/**
	 * The upper left child of this tree.
	 */
	private SizedObject<QuadTree<T>> upperLeft;
	/**
	 * The upper right child of this tree.
	 */
	private SizedObject<QuadTree<T>> upperRight;
	/**
	 * The lower left child of this tree.
	 */
	private SizedObject<QuadTree<T>> lowerLeft;
	/**
	 * The lower right child of this tree.
	 */
	private SizedObject<QuadTree<T>> lowerRight;
	
	/**
	 * The amount of elements in this region of the tree
	 */
	private int size = 0;
	
	/**
	 * Creates a new empty QuadTree
	 * 
	 * @param x
	 *            the x-position of its upper left corner
	 * @param y
	 *            the y-position of its upper left corner
	 * @param width
	 *            the width of the QuadTree
	 * @param height
	 *            the height of the QuadTree
	 * @throws IllegalArgumentException
	 *             If the width or height are negative or not a perfect base 2
	 *             size, which is needed since the tree is working on pure
	 *             integers and without base 2 it might miss some edge cases.
	 */
	public QuadTree(int x, int y, int width, int height) throws IllegalArgumentException
	{
		if (width < 0 || height < 0)
			throw new IllegalArgumentException("Negative size is not allowed");
		if ((width & (width - 1)) != 0 || (height & (height - 1)) != 0)
			throw new IllegalArgumentException("Width or height is not a power of 2 integer");
		sizeWrapper = new SizedObject<QuadTree<T>>(this, x, y, width, height);
		
		int halfX = width / 2;
		int halfY = height / 2;
		horizontalMidLine = new Line2D.Double(x, y + halfY, x + width, y + halfY);
		verticalMidLine = new Line2D.Double(x + halfX, y, x + halfX, y + height);
		if (halfX > 0 && halfY > 0)
		{
			upperLeft = new SizedObject<QuadTree<T>>(null, x, y, halfX, halfY);
			upperRight = new SizedObject<QuadTree<T>>(null, x + halfX, y, halfX, halfY);
			lowerLeft = new SizedObject<QuadTree<T>>(null, x, y + halfY, halfX, halfY);
			lowerRight = new SizedObject<QuadTree<T>>(null, x + halfX, y + halfY, halfX, halfY);
		}
	}
	
	/**
	 * Creates a new empty quadtree with a set parent
	 * 
	 * @param x
	 *            the x-position of its upper left corner
	 * @param y
	 *            the y-position of its upper left corner
	 * @param width
	 *            the width of the QuadTree
	 * @param height
	 *            the height of the QuadTree
	 * @param parent
	 *            the parent tree
	 */
	private QuadTree(int x, int y, int width, int height, QuadTree<T> parent)
	{
		this(x, y, width, height);
		this.parent = parent;
	}
	
	/**
	 * @return the bounds of this tree
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(sizeWrapper.getX(), sizeWrapper.getY(), sizeWrapper.getWidth(), sizeWrapper.getHeight());
	}
	
	/**
	 * Get the amount of objects contained in this tree
	 * 
	 * @return the amount of objects in the tree
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Checks if this quadtree is empty
	 * 
	 * @return true iff there are no elements in this tree or in any of its
	 *         child nodes.
	 */
	public boolean isEmpty()
	{
		return size < 1;
	}
	
	/**
	 * Searches for all objects that intersects the searchArea. i.e. have any
	 * point in common with the searchArea.
	 * Note that this function also searches in parent nodes if required
	 * 
	 * @param searchArea
	 *            the area to search in
	 * @return a collection of all sized of all objects that intersects the
	 *         searcharea
	 */
	public Collection<SizedObject<T>> getIntersect(Rectangle searchArea)
	{
		if (!sizeWrapper.intersects(searchArea) && parent != null)
			return parent.getIntersect(searchArea);
		ArrayList<SizedObject<T>> result = new ArrayList<>();
		for (SizedObject<T> o : horizontal)
			if (o.intersects(searchArea))
				result.add(o);
		for (SizedObject<T> o : vertical)
			if (o.intersects(searchArea))
				result.add(o);
				
		if (upperLeft.intersects(searchArea) && upperLeft.getObject() != null)
			result.addAll(upperLeft.getObject().getIntersect(searchArea));
		else if (upperRight.intersects(searchArea) && upperRight.getObject() != null)
			result.addAll(upperRight.getObject().getIntersect(searchArea));
		else if (lowerLeft.intersects(searchArea) && lowerLeft.getObject() != null)
			result.addAll(lowerLeft.getObject().getIntersect(searchArea));
		else if (lowerRight.intersects(searchArea) && lowerRight.getObject() != null)
			result.addAll(lowerRight.getObject().getIntersect(searchArea));
		return result;
	}
	
	/**
	 * Searches for all objects that are completely enveloped by the searchArea.
	 * Note that this function also searches in parent nodes if required
	 * 
	 * @param searchArea
	 *            the area to search in
	 * @return a collection of all sized of all objects that intersects the
	 *         searcharea
	 */
	public Collection<SizedObject<T>> getContained(Rectangle searchArea)
	{
		if (!sizeWrapper.contains(searchArea) && parent != null)
			return parent.getContained(searchArea);
		ArrayList<SizedObject<T>> result = new ArrayList<>();
		for (SizedObject<T> o : horizontal)
			if (searchArea.contains(o.getBounds()))
				result.add(o);
		for (SizedObject<T> o : vertical)
			if (searchArea.contains(o.getBounds()))
				result.add(o);
				
		if (upperLeft.contains(searchArea) && upperLeft.getObject() != null)
			result.addAll(upperLeft.getObject().getContained(searchArea));
		else if (upperRight.contains(searchArea) && upperLeft.getObject() != null)
			result.addAll(upperRight.getObject().getContained(searchArea));
		else if (lowerLeft.contains(searchArea) && upperLeft.getObject() != null)
			result.addAll(lowerLeft.getObject().getContained(searchArea));
		else if (lowerRight.contains(searchArea) && upperLeft.getObject() != null)
			result.addAll(lowerRight.getObject().getContained(searchArea));
		return result;
	}
	
	/**
	 * Moves the object to a different place in the quadTree. This is done by
	 * removing the entry and then adding it back in on the new location.
	 * The new location can be the same part of the tree
	 * 
	 * @param element
	 *            object to move
	 * @param mover
	 *            A runnable object that will perform the actual movement. i.e.
	 *            this will change the x/y coordinates of the element
	 * @return The new(or possibly old) quadtree root
	 * @throws IllegalArgumentException
	 *             if the object is not contained in the tree
	 */
	public QuadTree<T> updateObjectPosition(SizedObject<T> element, Runnable mover) throws IllegalArgumentException
	{
		boolean removed = remove(element, true);
		if (removed)
		{
			mover.run();
			return add(element);
		}
		Log.error(null, "Could not move object in QuadTree, " + (removed ? "could remove, but not add back in"
				: "could not remove, element might not have been in list"), this);
		return this;
	}
	
	/**
	 * Removes the object from the tree.
	 * This action can cause the tree to prune itself, however it will never
	 * prune to the point of removing the final node
	 * 
	 * @param element
	 *            the element to search for, and to remove
	 * @param strict
	 *            if true the search will get objects that are the exact same
	 *            reference. If false, it will take the first object that is
	 *            equal to element
	 * @return true if an element was removed, false otherwise
	 */
	public boolean remove(SizedObject<T> element, boolean strict)
	{
		if (!sizeWrapper.contains(element))
		{
			if (parent != null)
			{
				Log.info("Could not remove as object was not in this node, trying again in parent", this);
				return parent.remove(element, strict);
			}
			else
			{
				Log.warn(null,
						"Element was not in this node, nor was it in any parent node. The object was outside the tree and could therefore not be removed",
						this);
			}
		}
		boolean removed = false;
		for (SizedObject<T> o : horizontal)
			if ((strict && o.equals(element)) || (!strict && o == element))
			{
				removed = horizontal.remove(o);
				break;
			}
		if (!removed)
			for (SizedObject<T> o : vertical)
				if ((strict && o.equals(element)) || (!strict && o == element))
				{
					removed = vertical.remove(o);
					break;
				}
				
		if (!removed)
		{
			Log.info("Need to go down into the tree to find object to remove", this);
			if (upperLeft.contains(element))
				return (upperLeft.getObject() != null) ? upperLeft.getObject().remove(element, strict) : false;
			else if (upperRight.contains(element))
				return (upperRight.getObject() != null) ? upperRight.getObject().remove(element, strict) : false;
			else if (lowerLeft.contains(element))
				return (lowerLeft.getObject() != null) ? lowerLeft.getObject().remove(element, strict) : false;
			else if (lowerRight.contains(element))
				return (lowerRight.getObject() != null) ? lowerRight.getObject().remove(element, strict) : false;
			else
				Log.error(null, "Could not remove object as it could not be contained anywhere", this);
		}
		if (removed)
		{
			size--;
			QuadTree<T> parent = this.parent;
			while (parent != null)
			{
				parent.size--;
				parent = parent.parent;
			}
		}
		
		if (isEmpty() && parent != null) // Prune
		{
			Log.info("Pruning tree", this);
			if (parent.upperLeft.getObject() == this)
				parent.upperLeft.setObject(null);
			else if (parent.upperRight.getObject() == this)
				parent.upperRight.setObject(null);
			else if (parent.lowerLeft.getObject() == this)
				parent.lowerLeft.setObject(null);
			else if (parent.lowerRight.getObject() == this)
				parent.lowerRight.setObject(null);
		}
		
		return removed;
	}
	
	/**
	 * Adds a new element to the tree.
	 * If the new element is outside the tree it will create a new root to
	 * encompass this new element. So use with caution as it can grow
	 * uncontrollably if not used properly
	 * 
	 * @param element
	 *            the element to add
	 * @return the root of the tree. This may or may not be a new root.
	 */
	public QuadTree<T> add(SizedObject<T> element)
	{
		size++;
		QuadTree<T> root = this;
		while (root.parent != null)
		{
			root = root.parent;
			root.size++;
		}
		return add(element, root);
	}
	
	/**
	 * Adds the element to the tree.
	 * If a new root is needed it will be created.
	 * 
	 * @param element
	 *            the element to add
	 * @param root
	 *            the root of the tree. It's parent should always be null
	 * @return the root of the tree
	 * @throws IllegalArgumentException
	 *             if the root argument was not the absolute root of the tree
	 */
	private QuadTree<T> add(SizedObject<T> element, QuadTree<T> root) throws IllegalArgumentException
	{
		if (root.parent != null)
			throw new IllegalArgumentException("root argument was not real root. root must be absolute root");
		if (!sizeWrapper.contains(element))
		{
			size--; // We don't add to this node
			if (parent != null)
			{
				Log.info("Letting parent node handle add", this);
				return parent.add(element, root);
			}
			else
			{
				Log.info("Need to create new parent node", this);
				if (element.getX() < verticalMidLine.getX1())
				{
					if (element.getY() > horizontalMidLine.getY1()) // Lower
																	// left
					{
						parent = new QuadTree<>(sizeWrapper.getX() - sizeWrapper.getWidth(), sizeWrapper.getY(),
								sizeWrapper.getWidth() * 2, sizeWrapper.getHeight() * 2);
						parent.upperRight.setObject(this);
						parent.size = size + 1;
						return parent.add(element, parent);
					}
					else // Upper left
					{
						parent = new QuadTree<>(sizeWrapper.getX() - sizeWrapper.getWidth(),
								sizeWrapper.getY() - sizeWrapper.getHeight(), sizeWrapper.getWidth() * 2,
								sizeWrapper.getHeight() * 2);
						parent.lowerRight.setObject(this);
						parent.size = size + 1;
						return parent.add(element, parent);
					}
				}
				else
				{
					if (element.getY() > horizontalMidLine.getY1()) // Lower
																	// right
					{
						parent = new QuadTree<>(sizeWrapper.getX(), sizeWrapper.getY(), sizeWrapper.getWidth() * 2,
								sizeWrapper.getHeight() * 2);
						parent.upperLeft.setObject(this);
						parent.size = size + 1;
						return parent.add(element, parent);
					}
					else // Upper right
					{
						parent = new QuadTree<>(sizeWrapper.getX(), sizeWrapper.getY() - sizeWrapper.getHeight(),
								sizeWrapper.getWidth() * 2, sizeWrapper.getHeight() * 2);
						parent.lowerLeft.setObject(this);
						parent.size = size + 1;
						return parent.add(element, parent);
					}
				}
			}
		}
		else if (element.intersects(horizontalMidLine))
		{
			horizontal.add(element);
		}
		else if (element.intersects(verticalMidLine))
		{
			vertical.add(element);
		}
		else if (upperLeft.contains(element))
		{
			populateChild(upperLeft).add(element);
		}
		else if (upperRight.contains(element))
		{
			populateChild(upperRight).add(element);
		}
		else if (lowerLeft.contains(element))
		{
			populateChild(lowerLeft).add(element);
		}
		else if (lowerRight.contains(element))
		{
			populateChild(lowerRight).add(element);
		}
		else
		{
			Log.error(null, "Could not add entity to Quad Tree", this);
		}
		return root;
	}
	
	/**
	 * Creates the child object node if such a node is missing
	 * 
	 * @param child
	 *            the child to alter
	 * @return the new(or old) quadTree, being a child of this node.
	 */
	private QuadTree<T> populateChild(SizedObject<QuadTree<T>> child)
	{
		if (child.getObject() == null)
		{
			child.setObject(new QuadTree<>(child.getX(), child.getY(), child.getWidth(), child.getHeight(), this));
		}
		return child.getObject();
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		float zoom = 1f;//Globals.zoom;
		Color oldColor = g2d.getColor();
		g2d.setColor(Color.GREEN);
		g2d.drawLine((int) ((horizontalMidLine.getX1() + xOffset) * (displace ? zoom : 1)),
				(int) ((horizontalMidLine.getY1() + yOffset) * (displace ? zoom : 1)),
				(int) ((horizontalMidLine.getX2() + xOffset) * (displace ? zoom : 1)),
				(int) ((horizontalMidLine.getY2() + yOffset) * (displace ? zoom : 1)));
		g2d.setColor(Color.BLUE);
		g2d.drawLine((int) ((verticalMidLine.getX1() + xOffset) * (displace ? zoom : 1)),
				(int) ((verticalMidLine.getY1() + yOffset) * (displace ? zoom : 1)),
				(int) ((verticalMidLine.getX2() + xOffset) * (displace ? zoom : 1)),
				(int) ((verticalMidLine.getY2() + yOffset) * (displace ? zoom : 1)));
		g2d.setColor(oldColor);
		
		if (upperLeft.getObject() != null)
			upperLeft.getObject().paint(g2d, xOffset, yOffset, displace);
		if (upperRight.getObject() != null)
			upperRight.getObject().paint(g2d, xOffset, yOffset, displace);
		if (lowerLeft.getObject() != null)
			lowerLeft.getObject().paint(g2d, xOffset, yOffset, displace);
		if (lowerRight.getObject() != null)
			lowerRight.getObject().paint(g2d, xOffset, yOffset, displace);
	}
	
}