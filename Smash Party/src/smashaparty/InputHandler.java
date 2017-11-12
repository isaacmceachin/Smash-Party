package smashaparty;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener{
	
	public InputHandler(Game game){
		game.addKeyListener(this);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public class Key{
		private int numTimesPressed = 0;
		private boolean pressed = false;
		
		public boolean isPressed(){
			return pressed;
		}
		
		public int getNumTimesPressed(){
			return numTimesPressed;
		}
		
		public void toggle(boolean isPressed){
			pressed = isPressed;
			if(isPressed) numTimesPressed++;
		}
	}
	
	public List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	public boolean clicked = false;
	public int clickX = 0;
	public int clickY = 0;
	
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}
	
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}
	
	public void toggleKey(int keyCode, boolean isPressed){
		if(keyCode == KeyEvent.VK_W){ up.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_S){ down.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_A){ left.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_D){ right.toggle(isPressed);}
		
		/*
		if(keyCode == KeyEvent.VK_UP){ up.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_DOWN){ down.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_LEFT){ left.toggle(isPressed);}
		if(keyCode == KeyEvent.VK_RIGHT){ right.toggle(isPressed);}
		*/
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println("pressed("+e.getPoint().x+","+e.getPoint().y+")");
		
		clicked = true;
		clickX = e.getPoint().x;
		clickY = e.getPoint().y;
	}
	
	public void mouseClicked(MouseEvent e) {
		//System.out.println("click("+e.getPoint().x+","+e.getPoint().y+")");
		
		//clicked = true;
		//clickX = e.getPoint().x;
		//clickY = e.getPoint().y;
	}
	
	public void mouseDragged(MouseEvent e) {
		//System.out.println("dragged("+e.getPoint().x+","+e.getPoint().y+")");
	}
	
	public void keyTyped(KeyEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}