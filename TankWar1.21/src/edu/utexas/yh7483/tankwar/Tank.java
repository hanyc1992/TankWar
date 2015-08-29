package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;

public class Tank {
	
	public static final int TANK_RADIUS = 50;
	public static final int X_PACE = 3;
	public static final int Y_PACE = 3;
	
	public static final Color TANK_COLOR = Color.RED;
	public static final Color PT_COLOR = Color.BLACK;
	
	
	
	int xpos, ypos;
	TankClient tc = null;
	
	private boolean bu = false, bd = false, bl = false, br = false;
	enum Direction {U, D, L, R, UL, UR, DL, DR, STOP};
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;

	
	public Tank(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public Tank(int xpos, int ypos, TankClient tc) {
		this(xpos, ypos);
		this.tc = tc;
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
	}
	
	private void drawPT(Graphics g) {
		Color c = g.getColor();
		g.setColor(PT_COLOR);
		switch(ptDir) {
		case U: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos + TANK_RADIUS / 2, ypos);
			break;
		case D: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS);
			break;
		case L: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos, ypos + TANK_RADIUS / 2);
			break;
		case R: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos + TANK_RADIUS, ypos + TANK_RADIUS / 2);
			break;
		case UL: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos, ypos);
			break;
		case UR: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos + TANK_RADIUS, ypos);
			break;
		case DL: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos, ypos + TANK_RADIUS);
			break;
		case DR: 
			g.drawLine(xpos + TANK_RADIUS / 2, ypos + TANK_RADIUS / 2, xpos + TANK_RADIUS, ypos + TANK_RADIUS);
			break;
		default: break;
		}
		g.setColor(c);
	}
	
	public void draw(Graphics g) {
		getDirection();
		move();
		
		Color c = g.getColor();
		g.setColor(TANK_COLOR);
		g.fillOval(xpos, ypos, TANK_RADIUS, TANK_RADIUS);
		drawPT(g);
		g.setColor(c);
		
	}
	
	public void keyPressd(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			bu = true; break;
		case KeyEvent.VK_DOWN:
			bd = true; break;
		case KeyEvent.VK_LEFT:
			bl = true; break;
		case KeyEvent.VK_RIGHT:
			br = true; break;
		case KeyEvent.VK_CONTROL:
			tc.myMissile = fire(); break;
		default:
			break;
		}
	}
	
	void getDirection(){
		if(bu && !bd && !bl && !br) dir = Direction.U;
		else if(!bu && bd && !bl && !br) dir = Direction.D;
		else if(!bu && !bd && bl && !br) dir = Direction.L;
		else if(!bu && !bd && !bl && br) dir = Direction.R;
		else if(bu && !bd && bl && !br) dir = Direction.UL;
		else if(bu && !bd && !bl && br) dir = Direction.UR;
		else if(!bu && bd && bl && !br) dir = Direction.DL;
		else if(!bu && bd && !bl && br) dir = Direction.DR;
		else dir = Direction.STOP;
		
		if(dir != Direction.STOP)
			ptDir = dir;
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			bu = false; break;
		case KeyEvent.VK_DOWN:
			bd = false; break;
		case KeyEvent.VK_LEFT:
			bl = false; break;
		case KeyEvent.VK_RIGHT:
			br = false; break;
		default:
			break;
		}
	}

	public Missile fire() {
		int x = xpos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		int y = ypos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		
		return new Missile(x, y, ptDir);
	}
	
}
