package com.sharongur.marsrover.main;

import java.awt.Color;
import java.awt.Graphics;

import com.sharongur.marsrover.model.Render;

public class HUD implements Render{

	public static int HEALTH = 100;
	public boolean displayText = false;
	private int greenValue = 255;
	
	
	@Override
	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 200);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = HEALTH * 2;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int)Game.TEXT_HEIGHT / 4, (int)Game.TEXT_HEIGHT / 4, 200, 16);
		g.setColor(new Color(100,greenValue, 20));
		g.fillRect((int)Game.TEXT_HEIGHT / 4, (int)Game.TEXT_HEIGHT / 4, HEALTH * 2, 16);
		g.setColor(Color.white);
		g.drawRect((int)Game.TEXT_HEIGHT / 4, (int)Game.TEXT_HEIGHT / 4, 200, 16);
	
	}

	public void displayCollisionText() {
				
	}

}
