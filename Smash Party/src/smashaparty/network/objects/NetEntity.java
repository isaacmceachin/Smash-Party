package smashaparty.network.objects;

import java.awt.Graphics2D;

import smashaparty.objects.Entity;

public class NetEntity extends Entity{

	public NetEntity(double x, double y, double speed) {
		super(x, y, speed);
	}
	public void tick(long tickNumber) {}
	public void render(Graphics2D g) {}
}
