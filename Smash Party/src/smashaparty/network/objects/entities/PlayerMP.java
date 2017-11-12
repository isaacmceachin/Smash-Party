package smashaparty.network.objects.entities;

import java.net.InetAddress;

import smashaparty.InputHandler;
import smashaparty.objects.entities.Player;

public class PlayerMP extends Player{

	public InetAddress ipAddress;
	public int port;
	
	public PlayerMP(String username, double x, double y, InputHandler input, InetAddress ipAddress, int port) {
		super(username, x, y, input);
		this.username = username;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public PlayerMP(String username, double x, double y, InetAddress ipAddress, int port) {
		super(username, x, y, null);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public void tick(long tick){
		super.tick(tick);
	}
}
