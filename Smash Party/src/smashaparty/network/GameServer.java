package smashaparty.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import smashaparty.Game;
import smashaparty.network.objects.entities.BulletMP;
import smashaparty.network.objects.entities.PlayerMP;
import smashaparty.network.packets.Packet;
import smashaparty.network.packets.Packet.PacketTypes;
import smashaparty.network.packets.Packet00Login;
import smashaparty.network.packets.Packet01Disconnect;
import smashaparty.network.packets.Packet02Move;
import smashaparty.network.packets.Packet03Bullet;

public class GameServer extends Thread{

	private DatagramSocket socket;
	private int port;
	
	private Game game;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	
	public GameServer(Game game, int port) {
		this.game = game;
		this.port = port;
		try{
			this.socket = new DatagramSocket(this.port);
		}catch(SocketException e){
			e.printStackTrace();
		}
		
		//DatagramSocket socket = new DatagramSocket(null);
		//socket.bind(new InetSocketAddress(InetAddress.getByName("your.ip.add.ress"),5005);
	}
	
	public void run(){
		//https://stackoverflow.com/questions/9709956/java-udp-message-exchange-works-over-localhost-but-not-internet
		
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
				System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet00Login)packet).getUsername()+" has connected");
				PlayerMP player = new PlayerMP(((Packet00Login)packet).getUsername(), 100, 100, address, port);
				this.addConnection(player, ((Packet00Login)packet));
				
				break;
				
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("["+address.getHostAddress()+":"+port+"] "+((Packet01Disconnect)packet).getUsername()+" has left");
				this.removeConnection(((Packet01Disconnect)packet));
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

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for(PlayerMP p : this.connectedPlayers){
			if(player.getUsername().equalsIgnoreCase(p.getUsername())){
				if(p.ipAddress == null){
					p.ipAddress = player.ipAddress;
				}
				
				if(p.port == -1){
					p.port = player.port;
				}
				alreadyConnected = true;
			}else{
				Packet00Login newLogin = new Packet00Login(player.getUsername(), player.x, player.x);
				sendData(newLogin.getData(), p.ipAddress, p.port);
				
				newLogin = new Packet00Login(p.getUsername(), p.x, p.y);
				sendData(newLogin.getData(), player.ipAddress, player.port);
			}
		}
		
		if(!alreadyConnected){
			this.connectedPlayers.add(player);
		}
	}

	public void removeConnection(Packet01Disconnect packet){
		PlayerMP player = getPlayerMP(packet.getUsername());
		if(player != null){
			this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
			packet.writeData(this);
		}
	}
	
	private void handleMove(Packet02Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            PlayerMP player = this.connectedPlayers.get(index);
            player.x = packet.getX();
            player.y = packet.getY();
            player.setMoving(packet.isMoving());
            player.setMovingDir(packet.getMovingDir());
            player.setNumSteps(packet.getNumSteps());
            
            packet.writeData(this);
        }
	}
	
	private void handleBullet(Packet03Bullet packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			BulletMP bulletmp = new BulletMP(packet.getUsername(), packet.getBulletIndex(), packet.getStartX(), packet.getStartY(), packet.getToX(), packet.getToY());
			
			game.addEntity(bulletmp);
			
			packet.writeData(this);
		}
	}
	
	public PlayerMP getPlayerMP(String username){
		for(PlayerMP p : connectedPlayers){
			if(username.equalsIgnoreCase(p.getUsername())){
				return p;
			}
		}
		
		return null;
	}
	
	public int getPlayerMPIndex(String username){
		int index = 0;
		for(PlayerMP p : connectedPlayers){
			if(username.equalsIgnoreCase(p.getUsername())){
				break;
			}
			index++;
		}
		return index;
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try{
			socket.send(packet);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for(PlayerMP p : connectedPlayers){
			sendData(data, p.ipAddress, p.port);
		}
	}
}
