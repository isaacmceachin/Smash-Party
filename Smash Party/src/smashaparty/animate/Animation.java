package smashaparty.animate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;
	private boolean ranOnce = false;
	private long timesRan = 0;
	
	public Animation(){
		
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;
		
		synchronized(this){
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	public synchronized void addFrame(BufferedImage image, long duration){
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	public synchronized void update(long elapsedTime){
		if(frames.size() > 1){
			animTime += elapsedTime;
			if(animTime >= totalDuration){
				animTime = animTime % totalDuration;
				currentFrame = 0;
				
				ranOnce = true;
				timesRan++;
			}
			while(animTime > getFrame(currentFrame).endTime){
				currentFrame++;
			}
		}
	}
	
	public boolean ranOnce() {
		return ranOnce;
	}

	public long getTimesRan() {
		return timesRan;
	}

	public synchronized BufferedImage getImage(){
		if (frames.size() == 0){
			return null;
		}else{
			return getFrame(currentFrame).image;
		}
	}
	
	private AnimFrame getFrame(int i){
		return (AnimFrame) frames.get(i);
	}
	
	private class AnimFrame{
		BufferedImage image;
		long endTime;
		
		public AnimFrame(BufferedImage image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}