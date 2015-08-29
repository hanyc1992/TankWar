package edu.utexas.yh7483.tankwar;

import java.awt.*;

public class Explode {
	int xpos, ypos;
	boolean live = true;
	private TankClient tc;
	private int step = 0;
	private int[] diameter = {2,8,18,32,50,72,98,72,32,8,0};
	
	public static final Color EXPLODE_COLOR = Color.ORANGE;
	
	Explode(int xpos, int ypos, TankClient tc){
		this.xpos = xpos;
		this.ypos = ypos;
		this.tc = tc;
	}
	
	void draw(Graphics g){
		if(live ==false)
			return;
		if(step >= diameter.length) {
			live = false;
			step = 0;
		}
		Color c = g.getColor();
		g.setColor(EXPLODE_COLOR);
		g.fillOval(xpos + Tank.TANK_RADIUS / 2 - diameter[step] / 2, ypos + Tank.TANK_RADIUS / 2 - diameter[step] / 2, diameter[step], diameter[step]);
		g.setColor(c);
		++step;
	}
}
