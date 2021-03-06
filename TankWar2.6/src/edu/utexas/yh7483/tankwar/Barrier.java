package edu.utexas.yh7483.tankwar;

import java.awt.*;

public class Barrier {
	
	public static final Color BARRIER_COLOR = Color.CYAN;
	
	int xpos, ypos, width, height;
	TankClient tc = null;
	
	public Barrier(int xpos, int ypos, int width, int height, TankClient tc) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
		this.tc = tc;
	}

	void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(BARRIER_COLOR);
		g.fillRect(xpos, ypos, width, height);
		g.setColor(c);
	}
	
	Rectangle getRec() {
		return new Rectangle(xpos, ypos, width, height);
	}
	
}
