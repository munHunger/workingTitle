package se.munhunger.workingTitle.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.munhunger.workingTitle.ai.AIController;
import se.munhunger.workingTitle.entity.Asteroid;
import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.graphics.BackDrop;
import se.munhunger.workingTitle.graphics.ui.ShipBuilder;
import se.munhunger.workingTitle.graphics.ui.Text;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.QuadTree;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Main game class. This is the starting point of the game
 * 
 * @author munhunger
 * 		
 */
public class WorkingTitle implements KeyListener
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
	 * The players ship
	 */
	private Entity spaceShip = new Ship(Ship.ShipType.WIDE, true);
	
	/**
	 * The ShipBuilder for the player
	 */
	ShipBuilder builder = new ShipBuilder((Ship) spaceShip);
	
	/**
	 * Main panel that holds everything
	 */
	JPanel panel;
	
	/**
	 * constructor that launches everything important
	 */
	public WorkingTitle()
	{
		Globals.worldRoot = new QuadTree<Entity>(0, 0, 512, 512);
		
		Globals.worldRoot = Globals.worldRoot.add(spaceShip.getSize());
		
		Globals.worldRoot = Globals.worldRoot.add(new Asteroid(100, 100).getSize());
		Globals.worldRoot = Globals.worldRoot.add(new Asteroid(200, -256).getSize());
		Globals.worldRoot = Globals.worldRoot.add(new Asteroid(-250, 0).getSize());
		
		JFrame frame = new JFrame("Working Title");
		final BackDrop backDrop = new BackDrop();
		AIController.playerShips.add((Ship) spaceShip);
		panel = new JPanel()
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
				backDrop.paint((Graphics2D) g, Globals.xOffset, Globals.yOffset, true, 1f);
				// Globals.worldRoot.paint((Graphics2D) g, 0, 0, true);
				// spaceShip.paint((Graphics2D) g, 0, 0, true);
				for (SizedObject<Entity> t : Globals.worldRoot.getAll())
				{
					// System.out.println(t);
					t.getObject().paint((Graphics2D) g, Globals.xOffset, Globals.yOffset, true, 1f);
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
				Text.paintString(g, spaceShip.getResources() + " Resources", 10, 10, Text.TextMode.LEFT);
				builder.paintBuilder((Graphics2D) g);
				repaint();
			}
		};
		Globals.canvas = panel;
		panel.addKeyListener((KeyListener) spaceShip);
		panel.addKeyListener(this);
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
	
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_E)
		{
			builder.setVisible(!builder.isVisible());
			panel.removeMouseListener(builder.isVisible() ? (Ship) spaceShip : builder);
			panel.addMouseListener(builder.isVisible() ? builder : (Ship) spaceShip);
			if (builder.isVisible())
			{
				panel.removeKeyListener((Ship) spaceShip);
				panel.removeMouseMotionListener((Ship) spaceShip);
			}
			else
			{
				panel.addKeyListener((Ship) spaceShip);
				panel.addMouseMotionListener((Ship) spaceShip);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
