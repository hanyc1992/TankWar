package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{

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
		
	}
	
	public void paint(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
		
	}
	
	

}
