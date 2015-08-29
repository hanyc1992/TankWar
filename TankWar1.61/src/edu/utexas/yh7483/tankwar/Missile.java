
package edu.utexas.yh7483.tankwar;

import java.awt.*;

public class Missile {
	
	public static final int MISSIILE_RADIUS = 10;
	public static final int X_PACE = 15;
	public static final int Y_PACE = 15;
	
	public static final Color MISSIILE_COLOR = Color.BLACK;
	
	int xpos, ypos;
	Tank.Direction dir;
	boolean live = true;
	private TankClient tc = null;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int xpos, int ypos, Tank.Direction dir){
		this.xpos = xpos;
		this.ypos = ypos;
		this.dir = dir;
	}
	
	public Missile(int xpos, int ypos, Tank.Direction dir, TankClient tc){
		this(xpos, ypos, dir);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(live == false)
			return;
		move();
		
		Color c = g.getColor();
		g.setColor(MISSIILE_COLOR);
		g.fillOval(xpos, ypos, MISSIILE_RADIUS, MISSIILE_RADIUS);
		g.setColor(c);
		
	}
	
	public void move() {
		switch(dir) {
		case U: ypos -= Y_PACE; break;
		case D: ypos += Y_PACE; break;
		case L: xpos -= X_PACE; break;
		case R: xpos += X_PACE; break;
		case UL: ypos -= Y_PACE; xpos -= X_PACE; break;
		case UR: ypos -= Y_PACE; xpos += X_PACE; break;
		case DL: ypos += Y_PACE; xpos -= X_PACE; break;
		case DR: ypos += Y_PACE; xpos += X_PACE; break;
		default: break;
		}
		
		if (TankClient.isInBound(xpos, ypos, TankClient.GAME_WIDTH, TankClient.GAME_HEIGHT) == false) {
			live = false;
		}
		
	}
	
	Rectangle getRec() {
		return new Rectangle(xpos, ypos, MISSIILE_RADIUS, MISSIILE_RADIUS);
	}
	
	boolean hitTank(Tank enenmy) {
		if(enenmy.live == false)
			return false;
		return this.getRec().intersects(enenmy.getRec());
	}
	
	
}



