package se.munhunger.workingTitle.graphics.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * A game gui component
 * 
 * @author munhunger
 * 		
 */
public interface Component
{
	/**
	 * Paints this component at its place as well as all of it's subcomponents.
	 * Respects the old color of the graphics object
	 * 
	 * @param g
	 *            the graphics object to paint with
	 */
	public void paintComponents(Graphics2D g);
	
	/**
	 * @return the bounds of this component
	 */
	public Rectangle getBounds();
	
	/**
	 * Event to be triggered when the mouse was clicked on this component
	 * 
	 * @param x
	 *            the x position of the mouse in relation to this component
	 * @param y
	 *            the y position of the mouse in relation to this component
	 * @param button
	 *            the button pressed {@link MouseEvent#BUTTON1},
	 *            {@link MouseEvent#BUTTON2}, {@link MouseEvent#BUTTON3}
	 */
	public void mousePressed(int x, int y, int button);
	
	/**
	 * Event to be triggered when the mouse was moved over this component
	 * 
	 * @param x
	 *            the x position of the mouse in relation to this component
	 * @param y
	 *            the y position of the mouse in relation to this component
	 * @param button
	 *            the button pressed {@link MouseEvent#BUTTON1},
	 *            {@link MouseEvent#BUTTON2}, {@link MouseEvent#BUTTON3}
	 */
	public void mouseMoved(int x, int y, int button);
	
	/**
	 * Event to be triggered when the mouse is dragged over this component
	 * 
	 * @param x
	 *            the x position of the mouse in relation to this component
	 * @param y
	 *            the y position of the mouse in relation to this component
	 * @param button
	 *            the button pressed {@link MouseEvent#BUTTON1},
	 *            {@link MouseEvent#BUTTON2}, {@link MouseEvent#BUTTON3}
	 */
	public void mouseDragged(int x, int y, int button);
	
	/**
	 * Event to be triggered when the mouse exits this component
	 */
	public void mouseExited();
}
