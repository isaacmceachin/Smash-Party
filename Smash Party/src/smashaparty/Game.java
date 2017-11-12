package smashaparty;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import smashaparty.network.GameClient;
import smashaparty.network.GameServer;
import smashaparty.network.objects.entities.PlayerMP;
import smashaparty.network.packets.Packet00Login;
import smashaparty.objects.Entity;
import smashaparty.objects.entities.Player;

public class Game extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 250;//160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Smash Party";
	
	public static Game game;
	public static Assets assets;
	public JFrame frame;
	
	public boolean running = false;
	public long tickCount = 0;
	
	public InputHandler input;
	public WindowHandler windowHandler;
	
	public GameClient socketClient;
	public GameServer socketServer;
	
	private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
	private CopyOnWriteArrayList<Entity> entitiesToAdd = new CopyOnWriteArrayList<Entity>();
	public Player player;
	
	public Game() {
		//setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		//setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		//setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		//frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init() {
		game = this;
		assets = new Assets();
		
		windowHandler = new WindowHandler(this);
		input = new InputHandler(this);
		frame.addKeyListener(input);
		
		player = new PlayerMP("local-"+System.currentTimeMillis(), 100, 100, input, null, -1);
		this.addEntity(player);
		
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
		if(socketServer != null){
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		loginPacket.writeData(socketClient);
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
		
		if(JOptionPane.showConfirmDialog(this, "Run Game Server?") == 0){
			socketServer = new GameServer(this, 6789);
			socketServer.start();
		}
		
		socketClient = new GameClient(this, "localhost", 6789);
		socketClient.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			if(shouldRender) {
				frames++;
				repaint();
				//render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				frame.setTitle(ticks + " ticks | " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public synchronized void tick() {
		tickCount++;
		
		Iterator<Entity> entity_iterator = entities.iterator();
		while (entity_iterator.hasNext()) {
            Entity entity = entity_iterator.next();
            entity.tick(tickCount);
        }
		
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
	}
	
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		g.clearRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		Iterator<Entity> entity_iterator = entities.iterator();
		while (entity_iterator.hasNext()) {
            Entity entity = entity_iterator.next();
            entity.render(g);
        }
	}
	
	public CopyOnWriteArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void addEntity(Entity e) {
		entitiesToAdd.add(e);
	}
	
	public void removePlayerMP(String username) {
		this.getEntities().remove(getPlayerMPIndex(username));
	}
	
	public int getPlayerMPIndex(String username) {
		int index = 0;
		Iterator<Entity> entity_iterator = entities.iterator();
		while (entity_iterator.hasNext()) {
            Entity entity = entity_iterator.next();
            if(entity instanceof PlayerMP && ((PlayerMP) entity).getUsername().equals(username)){
				break;
			}
            index++;
        }
		return index;
	}
	
	public void movePlayer(String username, double x, double y, int numSteps, boolean isMoving, int movingDir){
		int index = getPlayerMPIndex(username);
        PlayerMP player = (PlayerMP) this.getEntities().get(index);
        player.x = x;
        player.y = y;
        player.setMoving(isMoving);
        player.setMovingDir(movingDir);
        player.setNumSteps(numSteps);
	}
}
