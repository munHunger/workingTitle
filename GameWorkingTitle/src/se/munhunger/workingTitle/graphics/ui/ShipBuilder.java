package se.munhunger.workingTitle.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.graphics.BlockPainter;
import se.munhunger.workingTitle.util.DiamondSquareFractal;

/**
 * GUI for building your ship
 * 
 * @author munhunger
 * 		
 */
public class ShipBuilder implements MouseListener, MouseMotionListener
{
	/**
	 * The ship to build in this builder
	 */
	private Ship ship;
	
	/**
	 * Constructor that gives the builder an object to build on
	 * 
	 * @param ship
	 *            the ship to build
	 */
	public ShipBuilder(Ship ship)
	{
		this.ship = ship;
	}
	
	/**
	 * The image to draw underneath the shipbuilder to give some indication that
	 * the user is in the shipbuilding mode
	 */
	private BufferedImage backdrop = null;
	
	/**
	 * Function to pregenerate a backdrop
	 */
	private void generateBackdrop()
	{
		int gridSize = 50;
		int width = 2000;// Globals.canvas.getWidth();
		int height = 2000;// Globals.canvas.getHeight();
		backdrop = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = backdrop.createGraphics();
		g2d.setColor(new Color(0.1f, 0.6f, 0.7f, 0.7f));
		for (int i = 0; i < height; i += gridSize)
			g2d.drawLine(0, i, width, i);
		for (int i = 0; i < width; i += gridSize)
			g2d.drawLine(i, 0, i, height);
		int fractalSize = Math.min(width, height) / gridSize;
		fractalSize = (int) ((fractalSize == 0) ? 0 : Math.pow(2, 32 - Integer.numberOfLeadingZeros(fractalSize - 1)));
		
		DiamondSquareFractal fractal = new DiamondSquareFractal(fractalSize + 1, true);
		for (int x = 0; x < fractal.getWidth(); x++)
			for (int y = 0; y < fractal.getHeight(); y++)
			{
				BlockPainter.paintBlock(g2d, x * gridSize, y * gridSize, gridSize, gridSize,
						new Color(0.3f, 0.15f, 0.8f, 1f - fractal.getValue(x, y)));
			}
			
		int radius = 5;
		int size = radius * 2 + 1;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];
		
		for (int n = 0; n < data.length; n++)
		{
			data[n] = weight;
		}
		
		Kernel kernel = new Kernel(size, size, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
		
		backdrop = op.filter(backdrop, null);
	}
	
	/**
	 * The panel for where to put all the tools
	 */
	private Panel buildingPanel = new Panel(50, 75, 250, 800);
	
	{
		buildingPanel.addComponent(new Button(0, 0, 150, 50, "button"));
	}
	
	/**
	 * @param g
	 */
	public void paintBuilder(Graphics2D g)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		Color oldColor = g.getColor();
		if (backdrop == null)
			generateBackdrop();
			
		g.drawImage(backdrop, 0, 0, null);
		buildingPanel.paintComponents(g);
		g.setColor(oldColor);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mousePressed((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mouseDragged((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mouseMoved((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
	}
}
