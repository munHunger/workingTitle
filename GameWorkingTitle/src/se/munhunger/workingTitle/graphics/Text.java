package se.munhunger.workingTitle.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import se.munhunger.workingTitle.util.Log;

/**
 * Helper class that manages the graphiccal parts of text. i.e. how to draw it
 * on the screen. It uses a bitmap for finding the character to draw
 * 
 * @author munhunger
 * 		
 */
public class Text
{
	/**
	 * The image that holds all character data
	 */
	public static BufferedImage fontImage = null;
	
	/**
	 * How many pixels to use to separate the letters in a text
	 */
	private static final int kearning = 1;
	
	/**
	 * enum that helps finding the characters in the fontimage
	 * 
	 * @author munhunger
	 * 		
	 */
	private enum Character
	{
		/**
		 * A character
		 */
		A('A', 0, 20),
		/**
		 * B character
		 */
		B('B', 22, 20),
		/**
		 * C character
		 */
		C('C', 44, 20),
		/**
		 * D character
		 */
		D('D', 66, 20),
		/**
		 * E character
		 */
		E('E', 88, 20),
		/**
		 * F character
		 */
		F('F', 110, 20),
		/**
		 * G character
		 */
		G('G', 132, 20),
		/**
		 * H character
		 */
		H('H', 154, 20),
		/**
		 * I character
		 */
		I('I', 176, 14),
		/**
		 * J character
		 */
		J('J', 193, 20),
		/**
		 * K character
		 */
		K('K', 215, 20),
		/**
		 * L character
		 */
		L('L', 237, 20),
		/**
		 * M character
		 */
		M('M', 259, 20),
		/**
		 * N character
		 */
		N('N', 281, 20),
		/**
		 * O character
		 */
		O('O', 303, 20),
		/**
		 * P character
		 */
		P('P', 325, 20),
		/**
		 * Q character
		 */
		Q('Q', 347, 20),
		/**
		 * R character
		 */
		R('R', 369, 20),
		/**
		 * S character
		 */
		S('S', 391, 20),
		/**
		 * T character
		 */
		T('T', 413, 20),
		/**
		 * U character
		 */
		U('U', 435, 20),
		/**
		 * V character
		 */
		V('V', 457, 20),
		/**
		 * W character
		 */
		W('W', 479, 20),
		/**
		 * X character
		 */
		X('X', 501, 20),
		/**
		 * Y character
		 */
		Y('Y', 523, 20),
		/**
		 * Z character
		 */
		Z('Z', 545, 20),
		/**
		 * 0 character
		 */
		ZERO('0', 567, 20),
		/**
		 * 1 character
		 */
		ONE('1', 589, 11),
		/**
		 * 2 character
		 */
		TWO('2', 603, 20),
		/**
		 * 3 character
		 */
		THREE('3', 625, 20),
		/**
		 * 4 character
		 */
		FOUR('4', 647, 20),
		/**
		 * 5 character
		 */
		FIVE('5', 669, 20),
		/**
		 * 6 character
		 */
		SIX('6', 691, 20),
		/**
		 * 7 character
		 */
		SEVEN('7', 713, 20),
		/**
		 * 8 character
		 */
		EIGHT('8', 735, 20),
		/**
		 * 9 character
		 */
		NINE('9', 757, 20),
		/**
		 * + character
		 */
		PLUS('+', 779, 20),
		/**
		 * - character
		 */
		MINUS('-', 801, 20),
		/**
		 * slash character
		 */
		SLASH('/', 823, 14),
		/**
		 * , character
		 */
		COMMA(',', 840, 11),
		/**
		 * . character
		 */
		DOT('.', 854, 9),
		/**
		 * ; character
		 */
		SEMICOLON(';', 865, 9),
		/**
		 * : character
		 */
		COLON(':', 876, 9),
		/**
		 * _ character
		 */
		UNDERSCORE('_', 887, 20),
		/**
		 * ' character
		 */
		APOSTROPHE('\'', 909, 9),
		/**
		 * " character
		 */
		QUOTATION('"', 920, 14),
		/**
		 * ! character
		 */
		EXLAMATION('!', 937, 9),
		/**
		 * # character
		 */
		HASHTAG('#', 948, 20),
		/**
		 * % character
		 */
		PERCENT('%', 970, 20);
		
		/**
		 * The character that this object is representing
		 */
		char character;
		/**
		 * The x coordinate of the character in the fontmap noted in pixels
		 */
		int x;
		/**
		 * The width of this character in pixels
		 */
		int width;
		
		/**
		 * constructor
		 * 
		 * @param character
		 *            the character that this object is representing
		 * @param x
		 *            the x coordinate of the character in the fontmap noted in
		 *            pixels
		 * @param width
		 *            the width of this character in pixels
		 * 			
		 */
		Character(char character, int x, int width)
		{
			this.character = character;
			this.x = x;
			this.width = width;
		}
	}
	
	static
	{
		try
		{
			fontImage = ImageIO.read(new File("res/fontClear.png"));
		}
		catch (IOException e)
		{
			Log.error(e, "Could not load font image", new Text());
		}
	}
	
	/**
	 * Defines in what way to display the text.
	 * 
	 * @author munhunger
	 * 		
	 */
	public static enum TextMode
	{
		/**
		 * If the text should grow to the right. static left
		 */
		LEFT,
		/**
		 * If the text should grow to both the left and to the right. static
		 * center
		 */
		CENTER,
		/**
		 * If the text should grow to the left. static right
		 */
		RIGHT;
	}
	
	/**
	 * Draws a TextString on the graphics object
	 * 
	 * @param g
	 *            the graphics object to draw with
	 * @param text
	 *            the text to draw
	 * @param x
	 *            the x position to lock on
	 * @param y
	 *            the y position to lock on
	 * @param mode
	 *            notes how the text should grow and how to use the lock
	 *            position
	 */
	public static void paintString(Graphics g, String text, int x, int y, TextMode mode)
	{
		text = text.toUpperCase();
		Character[] characters = new Character[text.length()];
		int width = 0;
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) == ' ')
			{
				characters[i] = null;
				width += 20;
				continue;
			}
			for (Character c : Character.values())
				if (c.character == text.charAt(i))
				{
					characters[i] = c;
					width += c.width + kearning;
					break;
				}
		}
		BufferedImage image = new BufferedImage(width, 20, BufferedImage.TYPE_INT_ARGB);
		Graphics fontGraphics = image.getGraphics();
		int currentPos = 0;
		for (Character c : characters)
		{
			if (c == null)
			{
				currentPos += 20;
				continue;
			}
			fontGraphics.drawImage(fontImage, currentPos, 0, currentPos + c.width, 20, c.x, 0, c.x + c.width, 20, null);
			currentPos += c.width + kearning;
		}
		switch (mode)
		{
			case LEFT:
				g.drawImage(image, x, y, null);
				break;
			case CENTER:
				g.drawImage(image, x - image.getWidth() / 2, y, null);
				break;
			case RIGHT:
				g.drawImage(image, x - image.getWidth(), y, null);
				break;
			default:
				break;
		}
	}
}
