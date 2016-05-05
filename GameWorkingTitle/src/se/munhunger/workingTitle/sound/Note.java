package se.munhunger.workingTitle.sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

import se.munhunger.workingTitle.util.Log;

/**
 * A single note.
 * 
 * @author munhunger
 * 		
 */
@Deprecated
public class Note
{
	/**
	 * Constructor
	 * 
	 * @param note
	 *            The note to play. 0-127 (60:middle C)
	 * @param duration
	 *            The duration to play the note in milliseconds
	 */
	public Note(int note, int duration)
	{
		this.note = note;
		this.duration = duration;
	}
	
	/**
	 * The note to play.
	 * 0-127(60:middle C)
	 */
	private int note;
	/**
	 * The duration to play the note in milliseconds
	 */
	private int duration;
	
	/**
	 * @return the note octave 0-127
	 */
	public int getNote()
	{
		return note;
	}
	
	/**
	 * Starts playing a note on an open synthesizer
	 * 
	 * @param synth
	 *            the synth to play on
	 */
	public void noteOn(Synthesizer synth)
	{
		MidiChannel[] channels = synth.getChannels();
		
		channels[0].noteOn(note, 100);
	}
	
	/**
	 * Stops playing the note on the open synthesizer
	 * 
	 * @param synth
	 *            the synth to play on
	 */
	public void noteOff(Synthesizer synth)
	{
		MidiChannel[] channels = synth.getChannels();
		channels[0].noteOff(note);
	}
	
	/**
	 * Plays the note on an open synthesizer
	 * 
	 * @param synth
	 *            the synth to play on
	 */
	public void playNote(Synthesizer synth)
	{
		MidiChannel[] channels = synth.getChannels();
		
		channels[0].noteOn(note, 500);
		try
		{
			Thread.sleep(duration);
		}
		catch (InterruptedException e)
		{
			Log.error(e, "Could not sleep thread", SoundController.class);
		}
		channels[0].noteOff(500);
	}
}
