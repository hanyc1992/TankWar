package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.util.List;

public class Missile {
	
	public static final int MISSIILE_RADIUS = 10;
	public static final int X_PACE = 15;
	public static final int Y_PACE = 15;
	public static final int MISSILE_ENERGY = 10;
	
	public static final Color MY_MISSIILE_COLOR = Color.GREEN;
	public static final Color ENENMY_MISSIILE_COLOR = Color.LIGHT_GRAY;
	
	
	int xpos, ypos;
	Tank.Direction dir;
	boolean live = true;
	private TankClient tc = null;
	boolean isMe;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int xpos, int ypos, Tank.Direction dir){
		this.xpos = xpos;
		this.ypos = ypos;
		this.dir = dir;
	}
	
	public Missile(int xpos, int ypos, Tank.Direction dir, TankClient tc, boolean isMe){
		this(xpos, ypos, dir);
		this.tc = tc;
		this.isMe = isMe;
	}
	
	public void draw(Graphics g) {
		if(live == false)
			return;
		move();
		hitTanks(tc.arrEnenmy);
		hitTank(tc.myTank);
		hitBarriers(tc.arrWall);
		
		Color c = g.getColor();
		Color missileColor = (isMe)? MY_MISSIILE_COLOR: ENENMY_MISSIILE_COLOR;
		g.setColor(missileColor);
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
		if(this.getRec().intersects(enenmy.getRec()) && enenmy.live && live && isMe != enenmy.isMe) {
			enenmy.life -= MISSILE_ENERGY;
			if(enenmy.life <= 0){
				enenmy.live = false;
			}

			this.live = false;
			tc.arrExplode.add(new Explode(xpos, ypos,tc));
			return true;
		}
		return false;
	}
	
	boolean hitTanks(List<Tank> arrEnenmy) {
		for(int i = 0; i < arrEnenmy.size(); ++i){
			if(hitTank(arrEnenmy.get(i))){
				return true;
			}
		}
		return false;
	}
	
	boolean hitBarrier(Barrier wall) {
		if(this.getRec().intersects(wall.getRec()) && live) {
			live = false;
			return true;
		}
		return false;
	}
	
	boolean hitBarriers(List<Barrier> arrWall) {
		for(int i = 0; i < arrWall.size(); ++i){
			if(hitBarrier(arrWall.get(i))){
				return true;
			}
		}
		return false;
	}
	
}



