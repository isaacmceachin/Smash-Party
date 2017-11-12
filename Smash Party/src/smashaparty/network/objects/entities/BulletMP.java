package smashaparty.network.objects.entities;

import smashaparty.objects.entities.Bullet;

public class BulletMP extends Bullet{

	public BulletMP(String username, int bulletIndex, double startX, double startY, double toX, double toY) {
		super(username, bulletIndex, startX, startY, toX, toY);
	}
	
	public void tick(long tick){
		super.tick(tick);
	}
}
