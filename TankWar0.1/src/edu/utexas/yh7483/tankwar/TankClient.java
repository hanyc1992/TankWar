package edu.utexas.yh7483.tankwar;

import java.awt.*;

public class TankClient extends Frame{

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	public void launchFrame(){
		this.setLocation(100, 100);
		this.setSize(800, 600);
		this.setVisible(true);
	}

}
