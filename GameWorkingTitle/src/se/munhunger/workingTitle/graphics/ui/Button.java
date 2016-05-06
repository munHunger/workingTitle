package se.munhunger.workingTitle.graphics.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Button representation
 * 
 * @author munhunger
 * 		
 */
public class Button implements Component
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
	 * The text to draw inside the button
	 */
	private String text = "";
	
	/**
	 * The image to draw inside the button
	 */
	private BufferedImage image;
	/**
	 * The action to perform when the button is pressed
	 */
	private Runnable action;
	
	/**
	 * Basic constructor for a button that displays a text
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 */
	public Button(int x, int y, int width, int height, String text)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	/**
	 * Basic constructor for a button that displays an image
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param image
	 */
	public Button(int x, int y, int width, int height, BufferedImage image)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}
	
	/**
	 * Sets the image to draw
	 * 
	 * @param image
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	/**
	 * Sets the action to perform when the button is pressed
	 * 
	 * @param r
	 */
	public void setAction(Runnable r)
	{
		this.action = r;
	}
	
	/**
	 * Flag to indicate wether or not the mouse is hovering this button
	 */
	private boolean hover = false;
	
	@Override
	public void paintComponents(Graphics2D g)
	{
		Color oldColor = g.getColor();
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(x, y, width, height, 15, 15);
		g.setColor(hover ? Color.GREEN : Color.GRAY);
		g.drawRoundRect(x + 4, y + 4, width - 8, height - 8, 15, 15);
		if (image != null)
			g.drawImage(image, x + width / 2 - image.getWidth() / 2, y + height / 2 - image.getHeight() / 2, null);
		else
			Text.paintString(g, text, x + width / 2, y + 15, Text.TextMode.CENTER);
		g.setColor(oldColor);
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public void mousePressed(int x, int y, int button)
	{
		if (action != null)
			action.run();
	}
	
	@Override
	public void mouseMoved(int x, int y, int button)
	{
		hover = true;
	}
	
	@Override
	public void mouseDragged(int x, int y, int button)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited()
	{
		hover = false;
	}
}
