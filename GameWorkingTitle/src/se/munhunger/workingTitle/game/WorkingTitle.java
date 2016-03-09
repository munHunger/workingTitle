package se.munhunger.workingTitle.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.graphics.BackDrop;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.QuadTree;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Main game class. This is the starting point of the game
 * 
 * @author munhunger
 * 
 */
public class WorkingTitle
{
	/**
	 * Main loop
	 * 
	 * @param args
	 *            no args are used
	 */
	public static void main(String[] args)
	{
		new WorkingTitle();
	}

	/**
	 * constructor that launches everything important
	 */
	public WorkingTitle()
	{
		Globals.worldRoot = new QuadTree<Entity>(0, 0, 512, 512);

		Entity spaceShip = new Ship(Ship.ShipType.WIDE);

		Globals.worldRoot = Globals.worldRoot.add(spaceShip.getSize());

		JFrame frame = new JFrame("Working Title");
		final BackDrop backDrop = new BackDrop();
		JPanel panel = new JPanel()
		{

			/**
			 * Serial version
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				this.setBackground(new Color(10, 15, 15));
				backDrop.paint((Graphics2D) g, Globals.xOffset, Globals.yOffset, true);
				// Globals.worldRoot.paint((Graphics2D) g, 0, 0, true);
				// spaceShip.paint((Graphics2D) g, 0, 0, true);
				for (SizedObject<Entity> t : Globals.worldRoot.getAll())
				{
					// System.out.println(t);
					t.getObject().paint((Graphics2D) g, Globals.xOffset, Globals.yOffset, true);
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
		panel.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentShown(ComponentEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e)
			{
				Globals.xOffset = e.getComponent().getWidth() / 2;
				Globals.yOffset = e.getComponent().getHeight() / 2;
			}

			@Override
			public void componentMoved(ComponentEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e)
			{
				// TODO Auto-generated method stub

			}
		});
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
}
