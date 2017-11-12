package smashaparty.network.packets;

import smashaparty.network.GameClient;
import smashaparty.network.GameServer;

public class Packet03Bullet extends Packet{

	private String username;
	private int bulletIndex;
	private double startX;
	private double startY;
	private double toX;
	private double toY;
	
	public Packet03Bullet(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.bulletIndex = Integer.parseInt(dataArray[1]);
		this.startX = Double.parseDouble(dataArray[2]);
		this.startY = Double.parseDouble(dataArray[3]);
		this.toX = Double.parseDouble(dataArray[4]);
		this.toY = Double.parseDouble(dataArray[5]);
	}
	
	public Packet03Bullet(String username, int bulletIndex, double startX, double startY, double toX, double toY) {
		super(03);
		this.username = username;
		this.bulletIndex = bulletIndex;
		this.startX = startX;
		this.startY = startY;
		this.toX = toX;
		this.toY = toY;
	}

	public void writeData(GameClient client) {
		client.sendData(getData());
	}
	
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}
	
	public byte[] getData() {
		return ("03"+this.username+","+this.bulletIndex+","+this.startX+","+this.startY+","+this.toX+","+this.toY).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public int getBulletIndex() {
		return bulletIndex;
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getToX() {
		return toX;
	}

	public double getToY() {
		return toY;
	}
}
