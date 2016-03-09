package se.munhunger.workingTitle.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.Tile;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.Log;
import se.munhunger.workingTitle.util.QuadTree;
import se.munhunger.workingTitle.util.SizedObject;

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
		Globals.worldRoot = new QuadTree<Entity>(0, 0, 512, 512);
		
		Entity spaceShip = new Ship(Ship.ShipType.WIDE);
		
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
				this.setBackground(new Color(10, 15, 15));
				Globals.worldRoot.paint((Graphics2D) g, 0, 0, true);
				//spaceShip.paint((Graphics2D) g, 0, 0, true);
				for (SizedObject<Entity> t : Globals.worldRoot.getAll())
				{
					System.out.println(t);
					t.getObject().paint((Graphics2D) g, 0, 0, true);
				}
				try
				{
					Thread.sleep(10);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
		};
		panel.addKeyListener((KeyListener) spaceShip);
		panel.addMouseMotionListener((MouseMotionListener) spaceShip);
		panel.addMouseListener((MouseListener) spaceShip);
		panel.setFocusable(true);
		panel.grabFocus();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
}
