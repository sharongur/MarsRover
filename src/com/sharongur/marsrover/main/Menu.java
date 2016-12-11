package com.sharongur.marsrover.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.model.Render;
import com.sharongur.marsrover.model.Spawner;
import com.sharongur.marsrover.model.State;

public class Menu extends MouseAdapter implements Render {
	
	private Game game;
	private Handler handler;
	private Spawner spawner;
	
	private Font menuHeader = new Font("ariel", 1 , 50);
	private Font menuBoxes = new Font("ariel", 1 , 30);
	
	public Menu(Game game, Handler handler, Spawner spawner) {
		this.game = game;
		this.handler = handler;
		this.spawner = spawner;
	}

	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		if(game.gameState == State.Menu){
			// start game
			if(mouseOver(mx, my, 210, 150, 200 ,64)){
				game.gameState = State.Game;
			}
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				System.exit(1);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		if(game.gameState == State.Menu){
			
			g.setFont(menuHeader);
			g.setColor(Color.white);
			g.drawString("Menu", 240, 70);
	
			g.setFont(menuBoxes);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 270, 190);
	
			g.setFont(menuBoxes);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Quit", 270, 290);
	
		}else{
			
		}
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx> x && mx < x +width){
			if (my > y && my < y + height){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
