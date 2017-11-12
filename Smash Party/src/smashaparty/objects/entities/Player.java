package smashaparty.objects.entities;

import java.awt.Graphics2D;

import smashaparty.Assets;
import smashaparty.Game;
import smashaparty.InputHandler;
import smashaparty.animate.Animation;
import smashaparty.network.packets.Packet02Move;
import smashaparty.network.packets.Packet03Bullet;

public class Player extends Mob{

	private InputHandler inputhandler;
	protected String username;
	
	//private BufferedImage image;
	
	private int bulletCount = 0;
	
	private Animation face = new Animation();
	
	private long lastTime = 0;
	
	public Player(String username, double x, double y, InputHandler inputhandler) {
		super(username, x, y, 2); //2 is speed
		
		//image = Assets.player;
		
		speed = 1.5;
		
		this.username = username;
		this.inputhandler = inputhandler;
		this.x = x;
		this.y = y;
		
		int eachDuration = 5;
		face.addFrame(Assets.s0, eachDuration);
		face.addFrame(Assets.s1, eachDuration);
		face.addFrame(Assets.s2, eachDuration);
		face.addFrame(Assets.s3, eachDuration);
		face.addFrame(Assets.s4, eachDuration);
		face.addFrame(Assets.s5, eachDuration);
		face.addFrame(Assets.s6, eachDuration);
		face.addFrame(Assets.s7, eachDuration);
		face.addFrame(Assets.s8, eachDuration);
		face.addFrame(Assets.s9, eachDuration);
		face.addFrame(Assets.s10, eachDuration);
		face.addFrame(Assets.s11, eachDuration);
		face.addFrame(Assets.s12, eachDuration);
		face.addFrame(Assets.s13, eachDuration);
		face.addFrame(Assets.s14, eachDuration);
		face.addFrame(Assets.s15, eachDuration);
		face.addFrame(Assets.s16, eachDuration);
		face.addFrame(Assets.s17, eachDuration);
		face.addFrame(Assets.s18, eachDuration);
		face.addFrame(Assets.s19, eachDuration);
	}
			
	public void tick(long tickNumber) {
		double xa = 0;
		double ya = 0;
		
		face.update(tickNumber - lastTime);
		lastTime = tickNumber;
		
		if(inputhandler != null){
			if(inputhandler.up.isPressed()){ya-=speed;}
			if(inputhandler.down.isPressed()){ya+=speed;}
			if(inputhandler.left.isPressed()){xa-=speed;}
			if(inputhandler.right.isPressed()){xa+=speed;}
		
			/*if(inputhandler.up.isPressed()){ya--;}
			if(inputhandler.down.isPressed()){ya++;}
			if(inputhandler.left.isPressed()){xa--;}
			if(inputhandler.right.isPressed()){xa++;}*/
			
			if(inputhandler.clicked){
				Bullet b = new Bullet(this.getUsername(), bulletCount, this.x + 16, this.y + 16, inputhandler.clickX, inputhandler.clickY);
				Game.game.addEntity(b);
				
				Packet03Bullet packet = new Packet03Bullet(username, bulletCount, this.x + 16, this.y +16, inputhandler.clickX, inputhandler.clickY);
				packet.writeData(Game.game.socketClient);
				
				bulletCount++;
				inputhandler.clicked = false;
			}
		}
		
		if(xa != 0 || ya != 0){
			move(xa, ya);
			isMoving = true;
			
			Packet02Move packet = new Packet02Move(this.getUsername(), this.x, this.y, this.numSteps, this.isMoving, this.movingDir);
			packet.writeData(Game.game.socketClient);
		}else{
			isMoving = false;
		}
	}

	public void render(Graphics2D g) {
		//g.drawOval(x, y, 20, 20);
        
        /*AffineTransform t = new AffineTransform();
        t.translate(x, y); // x/y set here, ball.x/y = double, ie: 10.33
        t.scale(1, 1); // scale = 1 
        g.drawImage(image, t, null);*/
		
		g.drawImage(face.getImage(), (int)x,(int) y, null);
		//g.drawImage(image, (int)x,(int) y, null);
	}

	
	public boolean hasCollided(double xa, double ya) {
		return false;
	}

	public String getUsername() {
		return username;
	}
}
