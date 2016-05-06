package se.munhunger.workingTitle.graphics.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Holder of graphical data
 * 
 * @author munhunger
 * 		
 */
public class Panel implements Component
{
	/**
	 * The x-coordinate
	 */
	private int x;
	/**
	 * The y-coordinate
	 */
	private int y;
	/**
	 * The width of the component.
	 * Components can be placed outside of this.
	 * This is generally just a guide to show the border of the panel
	 */
	private int width;
	/**
	 * The height of the component.
	 * Components can be placed outside of this.
	 * This is generally just a guide to show the border of the panel
	 */
	private int height;
	/**
	 * A list of all components that this panel holds
	 */
	private ArrayList<Component> children = new ArrayList<Component>();
	
	/**
	 * Adds a component to this panel.
	 * This component will be painted when the panel is painted
	 * 
	 * @param c
	 */
	public void addComponent(Component c)
	{
		children.add(c);
	}
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Panel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paintComponents(Graphics2D g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(x, y, width, height, 15, 15);
		g.setColor(Color.GRAY);
		g.drawRoundRect(x + 4, y + 4, width - 8, height - 8, 15, 15);
		BufferedImage image = new BufferedImage(width - 20, height - 20, BufferedImage.TYPE_4BYTE_ABGR);
		for (Component c : children)
			c.paintComponents((Graphics2D) image.getGraphics());
		g.drawImage(image, x + 10, y + 10, null);
		g.setColor(oldColor);
	}
	
	@Override
	public void mousePressed(int x, int y, int button)
	{
		for (Component c : children)
			if (c.getBounds().contains(x, y))
				c.mousePressed(x - c.getBounds().x, y - c.getBounds().y, button);
	}
	
	@Override
	public void mouseMoved(int x, int y, int button)
	{
		for (Component c : children)
			if (c.getBounds().contains(x, y))
				c.mouseMoved(x - c.getBounds().x, y - c.getBounds().y, button);
			else
				c.mouseExited();
	}
	
	@Override
	public void mouseDragged(int x, int y, int button)
	{
		for (Component c : children)
			if (c.getBounds().contains(x, y))
				c.mouseDragged(x - c.getBounds().x, y - c.getBounds().y, button);
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public void mouseExited()
	{
		for (Component c : children)
			c.mouseExited();
	}
}
