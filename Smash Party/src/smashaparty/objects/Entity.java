package smashaparty.objects;

import java.awt.Graphics2D;

public abstract class Entity {
	
	public double x;
	public double y;
	public double speed;
	
	public Entity(double x, double y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	protected boolean dead = false;
	
	public abstract void tick(long tickNumber);
	
	public abstract void render(Graphics2D g);
	
}
