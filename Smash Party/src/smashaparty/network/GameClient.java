package smashaparty.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import smashaparty.Game;
import smashaparty.network.objects.entities.BulletMP;
import smashaparty.network.objects.entities.PlayerMP;
import smashaparty.network.packets.Packet;
import smashaparty.network.packets.Packet.PacketTypes;
import smashaparty.network.packets.Packet00Login;
import smashaparty.network.packets.Packet01Disconnect;
import smashaparty.network.packets.Packet02Move;
import smashaparty.network.packets.Packet03Bullet;

public class GameClient extends Thread{

	private DatagramSocket socket;
	private int port;
	private InetAddress ipAddress;
	
	private Game game;
	
	public GameClient(Game game, String ipAddress, int port) {
		this.game = game;
		try{
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
			this.port = port;
		}catch(SocketException e){
			e.printStackTrace();
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		
		//DatagramSocket socket = new DatagramSocket(null);
		//socket.bind(new InetSocketAddress(InetAddress.getByName("your.ip.add.ress"),5005);
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				socket.receive(packet);
			}catch(IOException e){
				e.printStackTrace();
			}
			
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		
		Packet packet;
		
		switch(type){
			case INVALID:
			default:
				break;
			
			case LOGIN:
				packet = new Packet00Login(data);
				this.handleLogin(((Packet00Login)packet), address, port);	
				break;
				
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet01Disconnect)packet).getUsername()+" has left the world");
				game.removePlayerMP(((Packet01Disconnect)packet).getUsername());
				break;
				
			case MOVE:
				packet = new Packet02Move(data);
				this.handleMove(((Packet02Move)packet));
				break;
				
			case BULLET:
				packet = new Packet03Bullet(data);
				this.handleBullet(((Packet03Bullet)packet));
				break;
		}
	}

	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("["+address.getHostAddress()+":"+port+"] "+packet.getUsername()+" has joined the game");
		
		PlayerMP player = new PlayerMP(packet.getUsername(), packet.getX(), packet.getY(), address, port);
		game.addEntity(player);
	}

	private void handleMove(Packet02Move packet) {
		this.game.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getNumSteps(), packet.isMoving(), packet.getMovingDir());
	}
	
	private void handleBullet(Packet03Bullet packet) {
		BulletMP bulletmp = new BulletMP(packet.getUsername(), packet.getBulletIndex(), packet.getStartX(), packet.getStartY(), packet.getToX(), packet.getToY());
		game.addEntity(bulletmp);
	}

	public void sendData(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try{
			socket.send(packet);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
