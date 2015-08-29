

package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final int FLASH_TIME = 50;
	public static final int ELIMINATE_TIME = 200;
	public static final int XSTART = 100;
	public static final int YSTART = 100;
	
	public static final Color BACKGROUND_COLOR = Color.GREEN;
	
	
	Image offScreenImage = null;
	Tank myTank = new Tank(XSTART, YSTART, true, this);
	List<Missile> arrMissile = new ArrayList<Missile>();
	List<Explode> arrExplode = new ArrayList<Explode>();
	List<Tank> arrEnenmy = new ArrayList<Tank>();
	
	

	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}
	
	public void launchFrame(){
		
		addEnenmy(3);
		
		this.setLocation(100, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
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
		new Thread(new MissileEliminate()).start();
		new Thread(new ExplodeEliminate()).start();
		new Thread(new EnenmyEliminate()).start();
		
		
	}
	
	public static boolean isInBound(int xpos, int ypos, int xlim, int ylim){
		if (xpos > 0 && xpos < xlim && ypos > 0 && ypos < ylim)
			return true;
		else
			return false;
	}
	
	public void paint(Graphics g) {
		
		myTank.draw(g);
		
		drawAllMissile(g);
		drawAllExplode(g);
		drawAllEnenmy(g);
		
		g.drawString("arrMissile count:" + arrMissile.size(), 10, 50);
		g.drawString("arrExplode count:" + arrExplode.size(), 200, 50);
		g.drawString("arrEnenmy count:" + arrEnenmy.size(), 400, 50);
		
	}
	
	private void drawAllMissile(Graphics g) {
		for(int i=0; i<arrMissile.size(); i++) {
			Missile m = arrMissile.get(i);
			m.hitTanks(arrEnenmy);
			m.draw(g);
		}
	}
	
	private void drawAllExplode(Graphics g) {
		for(int i=0; i<arrExplode.size(); i++) {
			Explode e = arrExplode.get(i);
			
			e.draw(g);
		}
	}
	
	private void drawAllEnenmy(Graphics g) {
		for(int i=0; i<arrEnenmy.size(); i++) {
			Tank e = arrEnenmy.get(i);
			
			e.draw(g);
		}
	}
	
	public void update(Graphics g) {
		
		if(offScreenImage == null)
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		Graphics gOffScreenImage = offScreenImage.getGraphics();
		
		Color c = gOffScreenImage.getColor();
		gOffScreenImage.setColor(BACKGROUND_COLOR);
		gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreenImage.setColor(c);
		
		paint(gOffScreenImage);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	public void addEnenmy(int num) {
		for(int i = 0; i < num; ++i) {
			int x = (int)(Math.random() * GAME_WIDTH);
			int y = (int)(Math.random() * GAME_HEIGHT);
			arrEnenmy.add(new Tank(x, y, false, this));
		}
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
			myTank.keyPressed(e);
		}
		
	}
	
	private class MissileEliminate implements Runnable {

		public void run() {
			while(true){
				try {
					Thread.sleep(ELIMINATE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < arrMissile.size(); ++i)
					if(arrMissile.get(i).isLive() == false)
						arrMissile.remove(i);
			}
		}
		
	}
	
	private class ExplodeEliminate implements Runnable {

		public void run() {
			while(true){
				try {
					Thread.sleep(ELIMINATE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < arrExplode.size(); ++i)
					if(arrExplode.get(i).live == false)
						arrExplode.remove(i);
			}
		}
		
	}
	
	private class EnenmyEliminate implements Runnable {

		public void run() {
			while(true){
				try {
					Thread.sleep(ELIMINATE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < arrEnenmy.size(); ++i)
					if(arrEnenmy.get(i).live == false)
						arrEnenmy.remove(i);
			}
		}
		
	}

}

