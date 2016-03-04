package se.munhunger.workingTitle.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.QuadTree;

/**
 * Main game class.
 * This is the starting point of the game
 * 
 * @author munhunger
 * 		
 */
public class Main 
{
	/**
	 * Main loop
	 * 
	 * @param args
	 *            no args are used
	 */
	public static void main(String[] args)
	{
		new Main();
	}
	
	/**
	 * constructor that launches everything important
	 */
	public Main()
	{
		Globals.worldRoot = new QuadTree<Entity>(0, 0, 2, 2);
		
		Entity spaceShip = new Ship(Ship.ShipType.SLIM);
		
		Globals.worldRoot = Globals.worldRoot.add(spaceShip.getSize());
		
		JFrame frame = new JFrame("Working Title");
		JPanel panel = new JPanel()
		{
			
			/**
			 * Serial version
			 */
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				this.setBackground(Color.BLACK);
				Globals.worldRoot.paint((Graphics2D) g, 0, 0, true);
				spaceShip.paint((Graphics2D) g, 0, 0, true);
				repaint();
			}
		};
		panel.addKeyListener((KeyListener) spaceShip);
		panel.addMouseMotionListener((MouseMotionListener)spaceShip);
		panel.setFocusable(true);
		panel.grabFocus();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
}
