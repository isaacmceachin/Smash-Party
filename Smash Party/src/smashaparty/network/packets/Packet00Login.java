package smashaparty.network.packets;

import smashaparty.network.GameClient;
import smashaparty.network.GameServer;

public class Packet00Login extends Packet{

	private String username;
	private double x;
	private double y;
	
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		//this.x = Integer.parseInt(dataArray[1]);
		//this.y = Integer.parseInt(dataArray[2]);
		
		this.x = Double.parseDouble(dataArray[1]);
		this.y = Double.parseDouble(dataArray[2]);
	}
	
	public Packet00Login(String username, double x, double y) {
		super(00);
		this.username = username;
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("00"+this.username+","+this.getX()+","+this.getY()).getBytes();
	}

	public String getUsername(){
		return this.username;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
}
