package se.munhunger.workingTitle.util;

/**
 * Logging utility that saves prints to a log file.
 * It can be configured to ignore or print different levels of messages.
 * It also has the ability to also print to standard out if so configured.
 * Exceptions should be caught and sent to this class
 * 
 * @author munhunger
 * 		
 */
public class Log
{
	/**
	 * Info level message.
	 * Info messages are messages that note what is currently going on in the
	 * program. For example "Cleaning up world" or "Generating new entities".
	 * These messages are meant as a debugging tool for developers to get a feel
	 * of what is happening in the program.
	 * 
	 * @param message
	 *            the message to write. This should be clear enough to note what
	 *            went wrong and where.
	 * @param context
	 *            the object that is calling this class. Most likely this will
	 *            be called with "this"
	 */
	public static void info(String message, Object context)
	{
	}
	
	/**
	 * Warning level message.
	 * Warning messages are messages that does not crash the program, but are
	 * indicators that something might have gone wrong and should be looked at
	 * when time exists.
	 * 
	 * @param t
	 *            the exception that might have been thrown.
	 *            Note that this can be null, if the error message is to be
	 *            shown without an exception being cast
	 * @param message
	 *            the message to write. This should be clear enough to note what
	 *            went wrong and where.
	 * @param context
	 *            the object that is calling this class. Most likely this will
	 *            be called with "this"
	 */
	public static void warn(Throwable t, String message, Object context)
	{
	}
	
	/**
	 * Error level message.
	 * Errors are classified as problems that is very likely to cause the
	 * program to crash, or at least have serious faults.
	 * 
	 * @param t
	 *            the exception that might have been thrown.
	 *            Note that this can be null, if the error message is to be
	 *            shown without an exception being cast
	 * @param message
	 *            the message to write. This should be clear enough to note what
	 *            went wrong and where.
	 * @param context
	 *            the object that is calling this class. Most likely this will
	 *            be called with "this"
	 */
	public static void error(Throwable t, String message, Object context)
	{
	}
	
	/**
	 * Fatal level message.
	 * Fatal level errors are errors that causes the game to crash and is such
	 * an important part that there should be a number one priority to fix
	 * whatever caused this error.
	 * i.e. this is a drop everything else and work on this kind of problem
	 * 
	 * @param t
	 *            the exception that might have been thrown.
	 *            Note that this can be null, if the error message is to be
	 *            shown without an exception being cast
	 * @param message
	 *            the message to write. This should be clear enough to note what
	 *            went wrong and where.
	 * @param context
	 *            the object that is calling this class. Most likely this will
	 *            be called with "this"
	 */
	public static void fatal(Throwable t, String message, Object context)
	{
	}
	
}
