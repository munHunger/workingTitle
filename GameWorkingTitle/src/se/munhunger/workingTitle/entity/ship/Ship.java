package se.munhunger.workingTitle.entity.ship;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.Tile;
import se.munhunger.workingTitle.entity.projectile.ProjectileController;
import se.munhunger.workingTitle.util.Globals;
import se.munhunger.workingTitle.util.Keyboard;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * A spaceship that can be controlled.
 * 
 * @author munhunger
 * 
 */
public class Ship extends Entity implements KeyListener, MouseMotionListener, MouseListener
{
	/**
	 * Denotes what type of ship to pre-generate
	 */
	public enum ShipType
	{
		/**
		 * A slim dart like space
		 */
		SLIM, /**
				 * A wide crescent like ship
				 */
		WIDE;
	}

	/**
	 * Speed of the ship. Notes how fast it can move through space
	 */
	private float speed = 0.05f;

	/**
	 * The mapping for keyboard key actions
	 */
	private static Map<Integer, Runnable> keyMaps = new HashMap<Integer, Runnable>();

	{
		keyMaps.put(KeyEvent.VK_W, () ->
		{
			Keyboard.press(KeyEvent.VK_W, () ->
			{
				Globals.worldRoot = Globals.worldRoot.updateObjectPosition(getSize(), () ->
				{
					getSize().setYf(getSize().getYf() - Keyboard.getTimeSinceLast(KeyEvent.VK_W) * speed);
					Globals.yOffset += Keyboard.getTimeSinceLast(KeyEvent.VK_W) * speed;
				});
			});
		});
		keyMaps.put(KeyEvent.VK_S, () ->
		{
			Keyboard.press(KeyEvent.VK_S, () ->
			{
				Globals.worldRoot = Globals.worldRoot.updateObjectPosition(getSize(), () ->
				{
					getSize().setYf(getSize().getYf() + Keyboard.getTimeSinceLast(KeyEvent.VK_S) * speed);
					Globals.yOffset -= Keyboard.getTimeSinceLast(KeyEvent.VK_S) * speed;
				});
			});
		});
		keyMaps.put(KeyEvent.VK_A, () ->
		{
			Keyboard.press(KeyEvent.VK_A, () ->
			{
				Globals.worldRoot = Globals.worldRoot.updateObjectPosition(getSize(), () ->
				{
					getSize().setXf(getSize().getXf() - Keyboard.getTimeSinceLast(KeyEvent.VK_A) * speed);
					Globals.xOffset += Keyboard.getTimeSinceLast(KeyEvent.VK_A) * speed;
				});
			});
		});
		keyMaps.put(KeyEvent.VK_D, () ->
		{
			Keyboard.press(KeyEvent.VK_D, () ->
			{
				Globals.worldRoot = Globals.worldRoot.updateObjectPosition(getSize(), () ->
				{
					getSize().setXf(getSize().getXf() + Keyboard.getTimeSinceLast(KeyEvent.VK_D) * speed);
					Globals.xOffset -= Keyboard.getTimeSinceLast(KeyEvent.VK_D) * speed;
				});
			});
		});
	}

	/**
	 * Basic constructor
	 * 
	 * @param type
	 *            the type to generate for the ship
	 */
	public Ship(ShipType type)
	{
		switch (type)
		{
			case WIDE:
				parts = parts.add(new ShipBlock(0, 0, this).getSize());
				parts = parts.add(new ShipBlock(0, 1, this).getSize());
				parts = parts.add(new ShipBlock(1, 2, this).getSize());
				parts = parts.add(new ShipBlock(2, 2, this).getSize());
				parts = parts.add(new ShipBlock(3, 2, this).getSize());
				parts = parts.add(new ShipBlock(4, 1, this).getSize());
				parts = parts.add(new ShipBlock(4, 0, this).getSize());
				parts = parts.add(new Weapon(1, 1, this).getSize());
				parts = parts.add(new Weapon(3, 1, this).getSize());
				break;
			case SLIM:
				parts = parts.add(new Weapon(1, 0, this).getSize());
				parts = parts.add(new ShipBlock(0, 1, this).getSize());
				parts = parts.add(new ShipBlock(2, 1, this).getSize());
				parts = parts.add(new ShipBlock(0, 2, this).getSize());
				parts = parts.add(new ShipBlock(1, 2, this).getSize());
				parts = parts.add(new ShipBlock(2, 2, this).getSize());
				break;
			default:
				break;
		}
		validateSize();
	}

	/**
	 * Action for firing the weapons from this ship
	 */
	private void fire()
	{
		for (SizedObject<Tile> t : parts.getIntersect(parts.getBounds()))
		{
			if (t.getObject() instanceof Weapon)
			{
				Weapon w = (Weapon) t.getObject();
				ProjectileController.addProjectile(w.getProjectile().getSize());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (keyMaps.containsKey(e.getKeyCode()))
			keyMaps.get(e.getKeyCode()).run();

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (keyMaps.containsKey(e.getKeyCode()))
			Keyboard.release(e.getKeyCode());
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		float xDiff = e.getX() - (getSize().getX() + getSize().getWidth() / 2 + Globals.xOffset);
		float yDiff = e.getY() - (getSize().getY() + getSize().getHeight() / 2 + Globals.yOffset);

		getSize().setRotation((float) (Math.atan2(-xDiff, yDiff) + Math.PI));
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			fire();
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
}
