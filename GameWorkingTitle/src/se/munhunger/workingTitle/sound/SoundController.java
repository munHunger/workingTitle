package se.munhunger.workingTitle.sound;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;

/**
 * Helper class that plays and manages sound effects.
 * 
 * @author munhunger
 * 		
 */
public class SoundController
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		NoteBand band = new NoteBand(40);
		JFrame frame = new JFrame("Notes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(band.getSwingComponent());
		frame.setSize(600, 600);
		frame.setVisible(true);
		
		try
		{
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			band.playBand(synth, true);
			synth.close();
		}
		catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
