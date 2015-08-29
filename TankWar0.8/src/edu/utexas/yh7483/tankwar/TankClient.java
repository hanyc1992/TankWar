package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	
	public static final int GAME_WEIGHT = 800;
	public static final int GAME_HEIGHT = 600;
	public static final int FLASH_TIME = 50;
	public static final int XSTART = 100;
	public static final int YSTART = 100;
	
	public static final Color BACKGROUND_COLOR = Color.GREEN;
	
	
	
	Image offScreenImage = null;
	Tank myTank = new Tank(XSTART, YSTART);

	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launchFrame();
		
		
	}
	
	public void launchFrame(){
		
		this.setLocation(100, 100);
		this.setSize(GAME_WEIGHT, GAME_HEIGHT);
		this.setBackground(BACKGROUND_COLOR);
		this.setTitle("TankWar");
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
		
	}
	
	public void paint(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, GAME_WEIGHT, GAME_HEIGHT);
		myTank.draw(g);
		g.setColor(c);
		
	}
	
	public void update(Graphics g) {
		
		if(offScreenImage == null)
			offScreenImage = this.createImage(GAME_WEIGHT, GAME_HEIGHT);
		Graphics gOffScreenImage = offScreenImage.getGraphics();
		paint(gOffScreenImage);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	private class PaintThread implements Runnable{

		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(FLASH_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressd(e);
		}
		
	}
	

}
