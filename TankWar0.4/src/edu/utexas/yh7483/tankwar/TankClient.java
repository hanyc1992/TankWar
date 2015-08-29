package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	
	int xpos = 100;
	int ypos = 100;

	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}
	
	public void launchFrame(){
		
		this.setLocation(100, 100);
		this.setSize(800, 600);
		this.setBackground(Color.GREEN);
		this.setTitle("TankWar");
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(xpos, ypos, 30, 30);
		g.setColor(c);
		ypos += 3;
		
	}
	
	private class PaintThread implements Runnable{

		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	

}
