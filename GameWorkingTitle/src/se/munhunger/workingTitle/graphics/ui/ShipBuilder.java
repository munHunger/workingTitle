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

import se.munhunger.workingTitle.entity.Tile;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.entity.ship.ShipBlock;
import se.munhunger.workingTitle.graphics.BlockPainter;
import se.munhunger.workingTitle.util.DiamondSquareFractal;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.SizedObject;

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
	 * If true, this builder will be rendered
	 */
	private boolean visible = false;
	
	/**
	 * @return true if the builder is supposed to be rendered
	 */
	public boolean isVisible()
	{
		return visible;
	}
	
	/**
	 * Sets the visibility of the builder
	 * 
	 * @param visible
	 *            true if the builder should be rendered
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
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
	private static BufferedImage backdrop = null;
	
	static
	{
		generateBackdrop();
	}
	
	/**
	 * Function to pregenerate a backdrop
	 */
	private static void generateBackdrop()
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
	 * This shipblock indicates what block type to place onto the ship
	 */
	private ShipBlock selectedType = new ShipBlock(0, 0, null);
	
	/**
	 * A button used to indicated what block to place onto the ship
	 * 
	 * @see #selectedType
	 */
	private Button selectedBlockButton = new Button(0, 700, 225, 70, "");
	/**
	 * The panel for where to put all the tools
	 */
	private Panel buildingPanel = new Panel(50, 75, 250, 800);
	
	{
		for (int i = 0; i < ShipBlock.possibleBlocks.size(); i++)
		{
			final ShipBlock block = ShipBlock.possibleBlocks.get(i);
			BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
			BlockPainter.paintBlock((Graphics2D) image.getGraphics(), 0, 0, image.getWidth(), image.getHeight(),
					block.color);
			Button b = new Button((i * 75) % 250, i * 75 / 250 * 75, 70, 70, image);
			b.setAction(() ->
			{
				selectedType = block;
				updateSelectionButton();
			});
			buildingPanel.addComponent(b);
		}
		updateSelectionButton();
		buildingPanel.addComponent(selectedBlockButton);
	}
	
	/**
	 * Changes the image on {@link #selectedBlockButton} to reflect the value of
	 * {@link #selectedType}
	 */
	private void updateSelectionButton()
	{
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
		BlockPainter.paintBlock((Graphics2D) image.getGraphics(), 0, 0, image.getWidth(), image.getHeight(),
				selectedType.color);
		selectedBlockButton.setImage(image);
	}
	
	/**
	 * Paints the builder, the ship and all of its gui components if it is
	 * visible.
	 * 
	 * @see #visible
	 * @param g
	 */
	public void paintBuilder(Graphics2D g)
	{
		if (!visible)
			return;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		Color oldColor = g.getColor();
		if (backdrop == null)
			generateBackdrop();
		g.drawImage(backdrop, 0, 0, null);
		
		float zoom = 5f;
		ship.parts.getIntersect(ship.parts.getBounds());
		BufferedImage image = new BufferedImage((int) (((ship.size.getWidth() + 1)) * zoom),
				(int) (((ship.size.getHeight() + 1)) * zoom), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D imageGraphics = image.createGraphics();
		for (SizedObject<Tile> t : ship.parts.getAll())
		{
			t.getObject().paint(imageGraphics, 0, 0, true, zoom);
		}
		g.drawImage(image, (int) (325 + Globals.zoom * zoom), (int) (100 + Globals.zoom * zoom), null);
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
		if (!visible)
			return;
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mousePressed((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
		else
		{
			if (e.getX() > 300 && e.getY() > 75)
			{
				int x = (int) ((e.getX() - 325 - Globals.zoom * 5f) / Globals.zoom / 5f);
				int y = (int) ((e.getY() - 100 - Globals.zoom * 5f) / Globals.zoom / 5f);
				ShipBlock block = selectedType.clone(x, y, ship);
				if (e.getButton() == MouseEvent.BUTTON1)
					ship.addBlock(block);
				else if (e.getButton() == MouseEvent.BUTTON3)
				{
					System.out.println("REMOVE");
					ship.parts.remove(block.getSize(), true);
				}
			}
		}
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
		if (!visible)
			return;
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mouseDragged((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (!visible)
			return;
		if (buildingPanel.getBounds().contains(e.getPoint()))
			buildingPanel.mouseMoved((int) (e.getX() - buildingPanel.getBounds().getX()),
					(int) (e.getY() - buildingPanel.getBounds().getY()), e.getButton());
		else
			buildingPanel.mouseExited();
	}
}
