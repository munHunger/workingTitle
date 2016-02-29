package se.munhunger.workingTitle.util;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Helper for keyboard actions, such as when the last time a button was pressed
 * 
 * @author munhunger
 * 		
 */
public class Keyboard
{
	/**
	 * List of all keys on the keyboard
	 */
	private static HashMap<Integer, Key> keys = new HashMap<>();
	
	/**
	 * Presses a button and updates its data
	 * 
	 * @param keyCode
	 *            should probably be in {@link KeyEvent}
	 */
	public static void press(int keyCode)
	{
		if (!keys.containsKey(keyCode))
			keys.put(keyCode, new Key());
		keys.get(keyCode).press();
	}
	
	/**
	 * Presses a button and updates its data
	 * 
	 * @param keyCode
	 *            should probably be in {@link KeyEvent}
	 * @param action
	 *            the action to take while this button is pressed
	 */
	public static void press(int keyCode, Runnable action)
	{
		if (!keys.containsKey(keyCode))
			keys.put(keyCode, new Key());
		boolean previouslyPressed = keys.get(keyCode).pressed;

		if(!previouslyPressed)
		{
			keys.get(keyCode).press();
			new Thread(new Runnable()
			{
				public void run()
				{
					while (keys.get(keyCode).pressed)
					{
						long startTime = System.currentTimeMillis();
						action.run();
						keys.get(keyCode).update();
						try
						{
							Thread.sleep(Math.max(0, 50-(System.currentTimeMillis()-startTime)));
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
	
	/**
	 * @param keyCode
	 *            the key to press
	 * @throws IllegalArgumentException
	 *             if the button has never been pressed before
	 */
	public static void release(int keyCode) throws IllegalArgumentException
	{
		if (!keys.containsKey(keyCode))
			throw new IllegalArgumentException("Cannot release a button that has never been pressed");
		keys.get(keyCode).release();
	}
	
	/**
	 * Get the time in milli seconds since the last time the button was pressed
	 * or -1 if never pressed
	 * 
	 * @param keyCode
	 * @return time in milli seconds since last press
	 */
	public static long getTimeSinceLast(int keyCode)
	{
		if (!keys.containsKey(keyCode))
			return -1;
		return System.currentTimeMillis() - keys.get(keyCode).lastPressed;
	}
	
	/**
	 * Represents a single key and hold some information about said key. Such as
	 * when it was pressed last
	 * 
	 * @author munhunger
	 * 		
	 */
	private static class Key
	{
		/**
		 * Time in milli seconds from when this button was clicked
		 */
		private long lastPressed;
		
		/**
		 * Whether or not this key is currently down
		 */
		private boolean pressed = false;
		
		/**
		 * Updates the time since the last keypress
		 */
		private void update()
		{
			lastPressed = System.currentTimeMillis();
		}
		
		/**
		 * Should be triggered everytime a button is pressed
		 */
		private void press()
		{
			update();
			pressed = true;
		}
		
		/**
		 * Setting the state of the button as released
		 */
		private void release()
		{
			update();
			pressed = false;
		}
		
	}
}
