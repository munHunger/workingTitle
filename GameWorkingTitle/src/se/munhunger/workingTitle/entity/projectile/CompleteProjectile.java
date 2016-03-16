package se.munhunger.workingTitle.entity.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;

import se.munhunger.workingTitle.entity.Entity;
import se.munhunger.workingTitle.entity.ship.Ship;
import se.munhunger.workingTitle.entity.ship.ShipBlock;
import se.munhunger.workingTitle.graphics.BlockPainter;
import se.munhunger.workingTitle.util.Log;
import se.munhunger.workingTitle.util.SizedObject;

/**
 * Notes a projectile that have all the needed parts to create a projectile.
 * 
 * @author munhunger
 * 
 */
public abstract class CompleteProjectile extends Entity
{
	/**
	 * The projectile state. Will note what action to take each tick
	 * 
	 * @author munhunger
	 * 
	 */
	public enum State
	{
		/**
		 * Initial component that will be triggered at start
		 */
		FIRE,
		/**
		 * Component to trigger when moving the projectile
		 */
		FLIGHT,
		/**
		 * Notes when the projectile has hit a target
		 */
		HIT,
		/**
		 * When the projectile is out of energy and has missed all targets
		 */
		DEATH,
		/**
		 * When the particle should be removed from the world
		 */
		REMOVE;
	}
	
	/**
	 * The current projectile state.
	 */
	public State currentState = State.FIRE;
	
	/**
	 * Moves the projectile to the next state. After a finite amount of calls
	 * this will change the state to {@link State#REMOVE}
	 */
	public void advance()
	{
		switch (currentState)
		{
			case FIRE:
				currentState = State.FLIGHT;
				break;
			case FLIGHT:
				currentState = State.DEATH;
				break;
			case HIT:
				currentState = State.REMOVE;
				break;
			case DEATH:
				currentState = State.REMOVE;
				break;
			default:
				currentState = State.REMOVE;
				break;
		}
	}
	
	/**
	 * The ship that fired this projectile
	 */
	public Ship from;
	
	/**
	 * Components that should be triggered when the projectile is spawned
	 */
	public Collection<FireComponent> fireComponents = new ArrayList<FireComponent>();
	
	/**
	 * Components that should be triggered on each flight tick
	 */
	public Collection<FlightComponent> flightComponents = new ArrayList<FlightComponent>();
	
	/**
	 * Components that should be triggered when the projectile hits an object
	 */
	public Collection<HitComponent> hitComponents = new ArrayList<HitComponent>();
	
	/**
	 * Components that should be triggered when the projectile has traveled as
	 * far as it should and have not hit a target
	 */
	public Collection<DeathComponent> deathComponents = new ArrayList<DeathComponent>();
	
	{
		parts = parts.add(new ShipBlock(0, 0, 0.1f, 0.6f, 0.8f, this).getSize());
		validateSize();
		Log.info("Projectile has been created and size has been validated. Size:" + super.getSize(), this);
	}
	
	@Override
	public void paint(Graphics2D g2d, float xOffset, float yOffset, boolean displace)
	{
		super.paint(g2d, xOffset, yOffset, displace);
		SizedObject<?> size = getSize();
		AffineTransform oldAT = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		int x = (int) (size.getXf() + xOffset);
		int y = (int) (size.getYf() + yOffset);
		at.setToRotation(size.getRotations(), x + (5), y + (5));
		at.translate(x, y);
		g2d.setTransform(at);
		double period = (size.getXf() + size.getYf()) / 20;
		for (int i = 2; i < 7; i++)
			BlockPainter.paintBlock(g2d, (int) (Math.sin(period + i * 2) * 15), i * 15, 5 / (i / 2), 5 / (i / 2),
					new Color(0.1f, 0.6f, 0.8f));
		g2d.setTransform(oldAT);
	}
}
