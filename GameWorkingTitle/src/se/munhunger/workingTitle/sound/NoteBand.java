package se.munhunger.workingTitle.sound;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.Synthesizer;
import javax.swing.JPanel;

import se.munhunger.workingTitle.graphics.BlockPainter;
import se.munhunger.workingTitle.util.Log;

/**
 * A sheet of notes.
 * Holds information of what note to play, when and for how long
 * 
 * @author munhunger
 * 		
 */
@Deprecated
public class NoteBand
{
	/**
	 * The note sheet/band
	 */
	private Note[][] band;
	
	/**
	 * How far along the band has played
	 */
	private int progress = 0;
	
	/**
	 * Constructor
	 * 
	 * @param length
	 *            how many slots to create in the band
	 */
	public NoteBand(int length)
	{
		band = new Note[length][24];
	}
	
	/**
	 * Creates a graphical representation of the NoteBand.
	 * This component will also allow for changes in the band.
	 * 
	 * @return a swing component to represent this NoteBand
	 */
	public Component getSwingComponent()
	{
		int size = 50;
		JPanel panel = new JPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -7356317743848957725L;
			
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				super.setBackground(Color.GRAY);
				Graphics2D g2d = (Graphics2D) g;
				for (int x = 0; x < band.length; x++)
					for (int y = 0; y < band[x].length; y++)
						BlockPainter.paintBlock(g2d, x * size, y * size, size, size, Color.darkGray);
				for (int x = 0; x < band.length; x++)
				{
					for (int y = 0; y < band[x].length; y++)
					{
						if (band[x][y] != null)
							BlockPainter.paintBlock(g2d, x * size, y * size, size, size, Color.ORANGE);
					}
				}
				g.setColor(Color.CYAN);
				g.drawLine(progress * size, 0, progress * size, 1000);
				
				this.repaint();
			}
		};
		panel.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				int y = e.getY() / size;
				int x = e.getX() / size;
				if (band[x][y] == null)
					band[x][y] = new Note(y * 3 + 50, 100);
				else
					band[x][y] = null;
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		return panel;
	}
	
	/**
	 * Plays the band to the open synthesizer.
	 * Will repeat if desired
	 * 
	 * @param synth
	 *            the synth to play on
	 * @param repeat
	 *            true if the band should repeat
	 */
	public void playBand(Synthesizer synth, boolean repeat)
	{
		
		for (int x = progress; x < band.length; x++)
			for (int y = 0; y < band[x].length; y++)
			{
				Note n = band[x][y];
				if (n != null && (progress - 1 >= 0 && band[x - 1][y] == null))
					n.noteOn(synth);
			}
			
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			Log.error(e, "Could not sleep NoteBand", this);
		}
		
		for (int x = progress; x < band.length; x++)
			for (int y = 0; y < band[x].length; y++)
			{
				Note n = band[x][y];
				if (n != null && (progress + 1 < band.length && band[x + 1][y] == null))
					n.noteOff(synth);
			}
		progress++;
		boolean hasNotesLeft = false;
		for (int x = progress; x < band.length; x++)
			for (int y = 0; y < band[x].length; y++)
				if (band[x][y] != null)
				{
					hasNotesLeft = true;
					break;
				}
		if (!hasNotesLeft && repeat)
			progress = 0;
		if (progress >= band.length)
		{
			progress = 0;
			if (!repeat)
				return;
		}
		playBand(synth, repeat);
	}
}
