package smashaparty.objects.entities;

import smashaparty.objects.Entity;

public abstract class Mob extends Entity{
	
	protected String name;
	protected int numSteps;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	
	public Mob(String name, double x, double y, double speed) {
		super(x, y, speed);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;//speed;
	}
	
	public void move(double  xa, double ya){
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		
		if(!hasCollided(xa, ya)){
			if(ya < 0){
				movingDir = 0;
			}
			if(ya > 0){
				movingDir = 1;
			}
			if(xa < 0){
				movingDir = 2;
			}
			if(xa > 0){
				movingDir = 3;
			}
			
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(double xa, double ya);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		return false;
	}
	
	public String getName(){
		return this.name;
	}

	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}
}
