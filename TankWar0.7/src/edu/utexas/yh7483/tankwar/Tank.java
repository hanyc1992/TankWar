package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;

public class Tank {
	
	public static final int TANK_RADIUS = 30;
	public static final int TANK_PACE = 3;
	
	int xpos, ypos;
	
	public Tank(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(xpos, ypos, TANK_RADIUS, TANK_RADIUS);
		g.setColor(c);
		
	}
	
	public void keyPressd(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			ypos -= TANK_PACE; break;
		case KeyEvent.VK_DOWN:
			ypos += TANK_PACE; break;
		case KeyEvent.VK_LEFT:
			xpos -= TANK_PACE; break;
		case KeyEvent.VK_RIGHT:
			xpos += TANK_PACE; break;
		default:
			break;
		}
	}
}
