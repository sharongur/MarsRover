package com.sharongur.marsrover.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.sharongur.marsrover.model.Render;

public class Menu extends MouseAdapter implements Render {

	public void mousePressed(MouseEvent e){
		
	}
	
	public void mouseReleased(MouseEvent e){
		
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(((int)Game.WIDTH / 2), (int)Game.HEIGHT-10, 100, 64);
	}
	
}
