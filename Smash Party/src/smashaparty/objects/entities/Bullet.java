package smashaparty.objects.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import smashaparty.Assets;
import smashaparty.objects.Entity;

public class Bullet extends Entity{

	public double startX;
	public double startY;
	public double toX;
	public double toY;
	
	public int ticked = 0;
	
	double direction;
	
	private BufferedImage image;
	
	public Bullet(String username, int bulletIndex, double startX, double startY, double toX, double toY) {
		super(startX, startY, 4); //4 is speed
		
		image = Assets.bullet;
		
		this.startX = startX;
		this.startY = startY;
		this.toX = toX;
		this.toY = toY;
		
		double yDiff = this.toY - this.y;
		double xDiff = this.toX - this.x; 
		
		this.direction = Math.atan2(yDiff,xDiff);
	}
	
	public void tick(long tickNumber) {
		ticked++;
		
		if(ticked >= 35){
			dead = true;
		}else {
		    x += (speed * Math.cos(direction));
			y += (speed * Math.sin(direction));
		}
	}
	
	public void render(Graphics2D g) {
		if(!dead) {
			AffineTransform t = new AffineTransform();
			t.translate(x-4, y-4);
			t.rotate(direction, 4, 4);
			t.scale(1, 1);
			g.drawImage(image, t, null);
		}
	}
	
	public static double calcRotationAngle(Point centerPt, Point targetPt){
	    double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
	    theta += Math.PI/2.0;
	    double angle = Math.toRadians(theta);
	    if (angle < 0) {
	        angle += 360;
	    }
	    return angle;
	}
}