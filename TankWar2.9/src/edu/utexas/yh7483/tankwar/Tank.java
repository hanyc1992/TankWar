package edu.utexas.yh7483.tankwar;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Tank {

public static final int ROLL_MIN = 5;
public static final int ROLL_MAX = 15;

	public static final int EASY = 94;
	public static final int MEDIUM = 88;
	public static final int HARD = 85;
	public static final int CRAZY = 80;
	
	public static final int TANK_RADIUS = 50;
	public static final int X_PACE = 3;
	public static final int Y_PACE = 3;
	
	public static final int ENENY_LIFE_INIT = 40;
	public static final int MY_LIFE_INIT = 100;
	
	public static final Color MY_TANK_COLOR = Color.RED;
	public static final Color ENENMY_TANK_COLOR = Color.GRAY;
	public static final Color PT_COLOR = Color.WHITE;
	public static final Color BLOODBAR_OUT = Color.GRAY;
	public static final Color BLOODBAR_IN_MY = Color.RED;
	public static final Color BLOODBAR_IN_ENENMY = Color.BLUE;
	
	public static Random roll = new Random();
	
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImages = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif"))
		};
		
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
	}
	
	
	int xpos, ypos;
	private int xpos_old, ypos_old;
	TankClient tc = null;
	boolean isMe;
	boolean live = true;
	int life, lifeInitial;
	private BloodBar bBar = new BloodBar();
	
	private boolean bu = false, bd = false, bl = false, br = false;
	enum Direction {U, D, L, R, UL, UR, DL, DR, STOP};
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	private int step = 0;
	private Direction[] arrD = Direction.values();

	
	public Tank(int xpos, int ypos, boolean isMe) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.isMe = isMe;
		xpos_old = xpos;
		ypos_old = ypos;
		lifeInitial = (isMe)? MY_LIFE_INIT: ENENY_LIFE_INIT;
		life = lifeInitial;
	}
	
	public Tank(int xpos, int ypos, boolean isMe, TankClient tc) {
		this(xpos, ypos, isMe);
		this.tc = tc;
	}
	
	void stay() {
		xpos = xpos_old;
		ypos = ypos_old;
	}
	
	void updateOldPos() {
		xpos_old = xpos;
		ypos_old = ypos;
	}
	
	void getDirection(){
		if(isMe) {
			if(bu && !bd && !bl && !br) dir = Direction.U;
			else if(!bu && bd && !bl && !br) dir = Direction.D;
			else if(!bu && !bd && bl && !br) dir = Direction.L;
			else if(!bu && !bd && !bl && br) dir = Direction.R;
			else if(bu && !bd && bl && !br) dir = Direction.UL;
			else if(bu && !bd && !bl && br) dir = Direction.UR;
			else if(!bu && bd && bl && !br) dir = Direction.DL;
			else if(!bu && bd && !bl && br) dir = Direction.DR;
			else dir = Direction.STOP;
		} else {
			if(step == 0) {
				step = roll.nextInt(ROLL_MAX - ROLL_MIN + 1) + ROLL_MIN;
				int index = roll.nextInt(arrD.length);
				dir = arrD[index];
			} else {
				--step;
			}
			
			if(roll.nextInt(100) > TankClient.MODE) 
				fire();
		}
		
		if(dir != Direction.STOP)
			ptDir = dir;
	}
	
	public void move() {
		
		getDirection();
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
		
		if (xpos < 3) xpos = 3;
		if (xpos > TankClient.GAME_WIDTH - TANK_RADIUS -3) xpos = TankClient.GAME_WIDTH - TANK_RADIUS -3;
		if (ypos < 25) ypos = 25;
		if (ypos >TankClient.GAME_HEIGHT - TANK_RADIUS - 3) ypos = TankClient.GAME_HEIGHT - TANK_RADIUS -3;
		
	}
	
	/*
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
	*/
	public void draw(Graphics g) {
		
		if(live == false)
			return;
		
		move();
		hitBarriers(tc.arrWall);
		hitTanks(tc.arrEnenmy);
		hitTank(tc.myTank);
		hitAids(tc.arrAid);
		updateOldPos();
		/*
		Color c = g.getColor();
		Color tankColor = (isMe)? MY_TANK_COLOR: ENENMY_TANK_COLOR;
		g.setColor(tankColor);
		g.fillOval(xpos, ypos, TANK_RADIUS, TANK_RADIUS);
		drawPT(g);
		bBar.draw(g);
		g.setColor(c);
		*/
		switch(ptDir) {
		case L:
			g.drawImage(imgs.get("L"), xpos, ypos, null);
			break;
		case UL:
			g.drawImage(imgs.get("LU"), xpos, ypos, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), xpos, ypos, null);
			break;
		case UR:
			g.drawImage(imgs.get("RU"), xpos, ypos, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), xpos, ypos, null);
			break;
		case DR:
			g.drawImage(imgs.get("RD"), xpos, ypos, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), xpos, ypos, null);
			break;
		case DL:
			g.drawImage(imgs.get("LD"), xpos, ypos, null);
			break;
		}
		bBar.draw(g);
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(live == false) 
			return;
		
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
			fire(); break;
		case KeyEvent.VK_A:
			superFire(); break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(live == false)
			return;
		
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

	public void fire() {
		int x = xpos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		int y = ypos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		
		tc.arrMissile.add(new Missile(x, y, ptDir, tc, isMe));
	}
	
	public void fire(Direction d) {
		int x = xpos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		int y = ypos + TANK_RADIUS / 2 - Missile.MISSIILE_RADIUS / 2;
		
		tc.arrMissile.add(new Missile(x, y, d, tc, isMe));
	}
	
	public void superFire() {
		for (int i = 0; i < 8; ++i) {
			fire(arrD[i]);
		}
	}
	
	Rectangle getRec() {
		return new Rectangle(xpos, ypos, TANK_RADIUS, TANK_RADIUS);
	}
	
	boolean hitBarrier(Barrier wall) {
		if(this.getRec().intersects(wall.getRec()) && live) {
			stay();
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
	
	boolean hitTank(Tank that) {
		if(this.getRec().intersects(that.getRec()) && live && that.live && this != that) {
			this.stay();
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
	
	boolean hitAid(Aid that) {
		if(this.getRec().intersects(that.getRec()) && live && that.live && isMe) {
			this.life += Aid.AID_ENERGY;
			if(life > lifeInitial)
				life = lifeInitial;
			that.live = false;
			return true;
		}
		return false;
	}
	
	boolean hitAids(List<Aid> arrAid) {
		for(int i = 0; i < arrAid.size(); ++i){
			if(hitAid(arrAid.get(i))){
				return true;
			}
		}
		return false;
	}
	
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(BLOODBAR_OUT);
			g.drawRect(xpos, ypos - 10, TANK_RADIUS, 5);
			Color tankColor = (isMe)? BLOODBAR_IN_MY: BLOODBAR_IN_ENENMY;
			g.setColor(tankColor);
			g.fillRect(xpos, ypos - 10, TANK_RADIUS * life / lifeInitial, 5);
			g.setColor(c);
		}
	}
}

