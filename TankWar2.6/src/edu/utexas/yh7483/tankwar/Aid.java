package edu.utexas.yh7483.tankwar;

import java.awt.*;

public class Aid {
	public static final Color AID_COLOR = Color.MAGENTA;
	public static final int AID_ENERGY = 40;
	public static final int AID_RADIUS = 10;
	
	int xpos, ypos, width, height;
	boolean live = true;
	TankClient tc = null;
	
	public Aid(int xpos, int ypos, TankClient tc) {
		super();
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = AID_RADIUS;
		this.height = AID_RADIUS;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(live ==false)
			return;
		
		Color c = g.getColor();
		g.setColor(AID_COLOR);
		g.fillRect(xpos, ypos, width, height);
		g.setColor(c);
	}
	
	Rectangle getRec() {
		return new Rectangle(xpos, ypos, width, height);
	}
}
