package com.sharongur.marsrover.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.handler.KeyInput;
import com.sharongur.marsrover.model.GameObject;
import com.sharongur.marsrover.model.Obstacle;
import com.sharongur.marsrover.model.Render;
import com.sharongur.marsrover.model.Rover;
import com.sharongur.marsrover.model.State;
import com.sharongur.marsrover.model.Type;

public class Game extends Canvas implements Runnable,Render {

	private static final long serialVersionUID = 1127822139570571210L;

	public static float WIDTH = 640, HEIGHT = WIDTH / 12 * 9 ,  TEXT_HEIGHT = 31;
	public static int MAP_X, MAP_Y;
	public State gameState = State.Game;
	
	private Thread thread;
	private boolean running = false;
	private int[][] obstacleMap;
	private Menu menu;
	private HUD hud;
	
	Handler handler;
	
	public Game(){
		handler = new Handler();
		menu = new Menu();
		hud = new HUD();
		this.addKeyListener(new KeyInput(handler));
		Scanner reader = new Scanner(System.in);
		System.out.println("Please Choose the Map sizes (EG - 10 10)");
		MAP_X = reader.nextInt();
		MAP_Y = reader.nextInt();
		obstacleMap = new int[MAP_X][MAP_Y];
		gameState = State.Game;
		
		
		// we set the rover size in relation to our window size
		float roverWidth =   ((WIDTH)/ (MAP_X)); 
		float roverHeight = ((HEIGHT - TEXT_HEIGHT) / (MAP_Y));
		
		addObstaclePhase(reader);
			
		new Window(WIDTH,HEIGHT,"Mars Rover!", this);
		
		handler.addObject(new Rover(0, (int)TEXT_HEIGHT, Type.Rover, roverWidth, roverHeight, handler));
		for (int i = 0; i < MAP_X; i++) {
			for (int j = 0; j < MAP_Y; j++) {
				if(obstacleMap[i][j] == 1){
					handler.addObject(new Obstacle((i)*roverWidth, (j)*roverHeight + (int)TEXT_HEIGHT, Type.Obstacle, roverWidth -2, roverHeight-2));
				}
			}
		}
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
		
		g.setColor(Color.black);
		g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);
		g.setColor(Color.white);
		g.drawLine(0, (int)TEXT_HEIGHT,(int) WIDTH, 32);
		
		handler.render(g);
		if(gameState == State.Menu){
			menu.render(g);
		}else{
			hud.render(g);
		}
		g.dispose();

		bs.show();
		}

	public static void main(String[] args) {
		new Game();
	}
	
private void addObstaclePhase(Scanner reader) {
		
		addObstacle(reader);
		
		boolean rightOption = false;
		String anotherObstacleString;
		
		while(!rightOption){
			System.out.println("Obstacle added, do you wish to add another? Y/N");
			anotherObstacleString = reader.next();
			if(anotherObstacleString.equalsIgnoreCase("N")){
				rightOption = true;
			}else if(anotherObstacleString.equalsIgnoreCase("Y")){
				addObstacle(reader);
			}else{
				System.out.println("Wrong option chosen, please choose Y/N");
			}
		}
	}
	
	private void addObstacle(Scanner reader){
		int obstacleXPosition;
		int obstacleYPosition;
		
		boolean validLocation = false;
		
		while(!validLocation){
			System.out.println("Please Choose an obstacle Location");
			obstacleXPosition = reader.nextInt();
			obstacleYPosition = reader.nextInt();
			try{
				if(!checkLocationValidation(obstacleXPosition,  obstacleYPosition)){
					System.out.println("Theres already an obstacle in this location, Please choose another.");
				}else{
					obstacleMap[obstacleXPosition][obstacleYPosition] = 1;
					validLocation = true;
				}
			} catch (ArrayIndexOutOfBoundsException e){
				System.out.println("You chose a location outside the map.");
			}
		}
	}


	private boolean checkLocationValidation(int obstacleXPosition,
		int obstacleYPosition) {
		
		if(obstacleMap[obstacleXPosition][obstacleYPosition] == 1){
			return false;
		}
		return true;
	}
	
	public static int clamp(int var, int min, int max){
		if( var >= max)
			return max;
		else if ( var <= min)
			return min;
		else
			return var;
	}



}
