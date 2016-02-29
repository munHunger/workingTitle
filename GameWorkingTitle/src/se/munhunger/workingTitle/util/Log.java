package se.munhunger.workingTitle.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

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
	 * All the possible log levels.
	 * Each level notes how much to log.
	 * So INFO will log everything while FATAL will log almost nothing
	 * 
	 * @author munhunger
	 * 		
	 */
	private static enum LogLevel
	{
		/**
		 * Info messages are messages that note what is currently going on in
		 * the program. For example "Cleaning up world" or
		 * "Generating new entities" These messages are meant as a debugging
		 * tool for developers to get a feel of what is happening in the
		 * program.
		 */
		INFO(1),
		/**
		 * 
		 * Warning messages are messages that does not crash the program, but
		 * are indicators that something might have gone wrong and should be
		 * looked at when time exists.
		 */
		WARN(2),
		/**
		 * 
		 * Errors are classified as problems that is very likely to cause the
		 * program to crash, or at least have serious faults.
		 */
		ERROR(4),
		/**
		 * 
		 * Fatal level errors are errors that causes the game to crash and is
		 * such
		 * an important part that there should be a number one priority to fix
		 * whatever caused this error.
		 * i.e. this is a drop everything else and work on this kind of problem
		 */
		FATAL(8);
		
		/**
		 * The integer value of the level
		 */
		private int level = 0;
		
		/**
		 * basic constructor
		 * 
		 * @param level
		 *            will be used in comparison between levels. A high level
		 *            means that it will be printed more often than a low level
		 *            log
		 */
		private LogLevel(int level)
		{
			this.level = level;
		}
		
		/**
		 * @return the integer value of the level
		 */
		public int getLevel()
		{
			return level;
		}
	}
	
	/**
	 * The current log level. This will determine how much to print at runtime
	 */
	private static LogLevel logLevel = LogLevel.INFO;
	
	/**
	 * Flag to determine whether or not to print to the standard system output
	 */
	private static boolean printToStandardOut = true;
	
	/**
	 * Info level message.
	 * 
	 * @see LogLevel#INFO
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
		if (logLevel.getLevel() <= LogLevel.INFO.getLevel())
		{
			printMessage(null, message, context, LogLevel.INFO);
		}
	}
	
	/**
	 * Warning level message.
	 * 
	 * @see LogLevel#WARN
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
		if (logLevel.getLevel() <= LogLevel.WARN.getLevel())
		{
			printMessage(null, message, context, LogLevel.WARN);
		}
	}
	
	/**
	 * Error level message.
	 *
	 * @see LogLevel#ERROR
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
		if (logLevel.getLevel() <= LogLevel.ERROR.getLevel())
		{
			printMessage(null, message, context, LogLevel.ERROR);
		}
	}
	
	/**
	 * Fatal level message.
	 * 
	 * @see LogLevel#FATAL
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
		if (logLevel.getLevel() <= LogLevel.FATAL.getLevel())
		{
			printMessage(null, message, context, LogLevel.FATAL);
		}
	}
	
	/**
	 * Prints the message.
	 * It saves it to a file and writes it to standard out if configured
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
	 * @param level
	 *            the log level that this was called from. This will note the
	 *            severity of the problem
	 */
	private static void printMessage(Throwable t, String message, Object context, LogLevel level)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		switch (level)
		{
			case INFO:
				builder.append("INFO");
				break;
			case WARN:
				builder.append("WARN");
				break;
			case ERROR:
				builder.append("ERROR");
				break;
			case FATAL:
				builder.append("FATAL");
				break;
			default:
				break;
		}
		builder.append("] " + Calendar.getInstance().getTime().toString() + "\t");
		if (message != null)
			builder.append(message);
		builder.append("\n\r");
		if (context != null)
			builder.append(context.getClass().getName() + "\n\r");
		if (t != null)
			for (StackTraceElement trace : t.getStackTrace())
				builder.append(trace.getClassName() + "\t" + trace.getMethodName() + ":" + trace.getLineNumber());
		builder.append("-=-=-=-=-=-=-=-");
		
		String printString = builder.toString();
		if (printToStandardOut)
			System.out.println(printString);
		try (PrintWriter out = new PrintWriter(new FileOutputStream(new File("log"), true)))
		{
			out.println(printString);
		}
		catch (IOException e)
		{
			System.out.println("Could not write to log file");
			e.printStackTrace();
		}
	}
}
