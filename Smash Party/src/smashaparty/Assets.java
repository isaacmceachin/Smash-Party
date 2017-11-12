package smashaparty;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {

	public static BufferedImage player, bullet;
	
	public static BufferedImage s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19;
	
	public Assets() {
		try {
			player = ImageIO.read(Assets.class.getResourceAsStream("/player.png"));
			bullet = ImageIO.read(Assets.class.getResourceAsStream("/bullet.png"));
			
			s0 = ImageIO.read(Assets.class.getResourceAsStream("/0.png"));
			s1 = ImageIO.read(Assets.class.getResourceAsStream("/1.png"));
			s2 = ImageIO.read(Assets.class.getResourceAsStream("/2.png"));
			s3 = ImageIO.read(Assets.class.getResourceAsStream("/3.png"));
			s4 = ImageIO.read(Assets.class.getResourceAsStream("/4.png"));
			s5 = ImageIO.read(Assets.class.getResourceAsStream("/5.png"));
			s6 = ImageIO.read(Assets.class.getResourceAsStream("/6.png"));
			s7 = ImageIO.read(Assets.class.getResourceAsStream("/7.png"));
			s8 = ImageIO.read(Assets.class.getResourceAsStream("/8.png"));
			s9 = ImageIO.read(Assets.class.getResourceAsStream("/9.png"));
			s10 = ImageIO.read(Assets.class.getResourceAsStream("/10.png"));
			s11 = ImageIO.read(Assets.class.getResourceAsStream("/11.png"));
			s12 = ImageIO.read(Assets.class.getResourceAsStream("/12.png"));
			s13 = ImageIO.read(Assets.class.getResourceAsStream("/13.png"));
			s14 = ImageIO.read(Assets.class.getResourceAsStream("/14.png"));
			s15 = ImageIO.read(Assets.class.getResourceAsStream("/15.png"));
			s16 = ImageIO.read(Assets.class.getResourceAsStream("/16.png"));
			s17 = ImageIO.read(Assets.class.getResourceAsStream("/17.png"));
			s18 = ImageIO.read(Assets.class.getResourceAsStream("/18.png"));
			s19 = ImageIO.read(Assets.class.getResourceAsStream("/19.png"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
