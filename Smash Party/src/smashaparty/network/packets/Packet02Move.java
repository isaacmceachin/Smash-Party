package smashaparty.network.packets;

import smashaparty.network.GameClient;
import smashaparty.network.GameServer;

public class Packet02Move extends Packet{
	
	private String username;
	private double x, y;
	private int numSteps;
	private boolean isMoving;
	private int movingDir = 1;
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Double.parseDouble(dataArray[1]);
		this.y = Double.parseDouble(dataArray[2]);
		this.numSteps = Integer.parseInt(dataArray[3]);
		this.isMoving = Integer.parseInt(dataArray[4]) == 1;
		this.movingDir = Integer.parseInt(dataArray[5]);
	}
	
	public Packet02Move(String username, double x, double y, int numSteps, boolean isMoving, int movingDir) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;
		this.numSteps = numSteps;
		this.isMoving = isMoving;
		this.movingDir = movingDir;
	}
	
	public void writeData(GameClient client) {
		client.sendData(getData());
	}
	
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}
	
	public byte[] getData() {
		return ("02"+this.username+","+this.x+","+this.y+","+this.numSteps+","+(this.isMoving ? "1" : "0")+","+this.movingDir).getBytes();
	}

	public String getUsername(){
		return username;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}

	public int getNumSteps() {
		return numSteps;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public int getMovingDir() {
		return movingDir;
	}
}
