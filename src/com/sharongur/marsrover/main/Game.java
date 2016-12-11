package com.sharongur.marsrover.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.handler.KeyInput;
import com.sharongur.marsrover.model.Render;
import com.sharongur.marsrover.model.Spawner;
import com.sharongur.marsrover.model.State;

public class Game extends Canvas implements Runnable,Render {

	private static final long serialVersionUID = 1127822139570571210L;

	public static float WIDTH = 640, HEIGHT = WIDTH / 12 * 9 ,  TEXT_HEIGHT = 40;
	
	public static State gameState;
	public final Color gameBackgroundColor = Color.black;
	
	private Thread thread;
	private boolean running = false;
	
	private Menu menu;
	private Spawner spawner;
	private HUD hud;
	
	Handler handler;
	
	public Game(){
		handler = new Handler();
		hud = new HUD();
		gameState = State.Menu;
		spawner = new Spawner(handler, this, hud);
		menu = new Menu(this, handler, spawner);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);

		spawner.startGame();
			
		new Window(WIDTH,HEIGHT,"Mars Rover!", this);
		
	}
	

	
	public void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public void stop(){
		try{
			thread.join();
			running = false;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 64.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
	
		while (running){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			while (delta >=1){
				tick();
				delta--;
			}
			if(running){
				render(null);
				}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	@Override
	public void tick(){
		handler.tick();
		if(gameState == State.Game){
			hud.tick();
			spawner.tick();
		}else{
			menu.tick();
		}
	}
	
	@Override
	public void render(Graphics g){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		
		
		if(gameState == State.Menu){
			menu.render(g);
		}else{
			spawner.render(g);
			hud.render(g);
			handler.render(g);
		}
		g.dispose();

		bs.show();
		}

	public static void main(String[] args) {
		new Game();
	}
	

	
	public static int clamp(int var, int min, int max){
		if( var >= max)
			return max;
		else if ( var <= min)
			return min;
		else
			return var;
	}



	public void changeState(State game) {
		this.gameState = game;
		
	}



}
