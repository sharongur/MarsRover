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
import com.sharongur.marsrover.model.Rover;
import com.sharongur.marsrover.model.Type;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1127822139570571210L;

	public static float WIDTH = 640, HEIGHT = WIDTH / 12 * 9 ,  TEXT_HEIGHT = 32;
	public static int MAP_X, MAP_Y;
	
	private Thread thread;
	private boolean running = false;
	private int[][] obstacleMap;
	
	Handler handler;
	
	public Game(){
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		Scanner reader = new Scanner(System.in);
		System.out.println("Please Choose the Map sizes (EG - 10 10)");
		MAP_X = reader.nextInt();
		MAP_Y = reader.nextInt();
		obstacleMap = new int[MAP_X][MAP_Y];
		
		
		// we set the rover size in relation to our window size
		int roverWidth =  (int) ((WIDTH)/ (MAP_X)); 
		int roverHeight = (int) ((HEIGHT - TEXT_HEIGHT) / (MAP_Y));
		
		addObstaclePhase(reader);
			
		new Window(WIDTH,HEIGHT,"Mars Rover!", this);
		
		handler.addObject(new Rover(0, (int)TEXT_HEIGHT, Type.Rover, roverWidth, roverHeight, handler));
		for (int i = 0; i < MAP_X; i++) {
			for (int j = 0; j < MAP_Y; j++) {
				if(obstacleMap[i][j] == 1){
					handler.addObject(new Obstacle((i)*roverWidth, (j)*roverHeight + (int)TEXT_HEIGHT, Type.Obstacle, roverWidth, roverHeight));
				}
			}
		}
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
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		handler.tick();
	}
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);
		g.setColor(Color.white);
		g.drawLine(0, (int)TEXT_HEIGHT,(int) WIDTH, 32);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
		}
	
	public static void main(String[] args) {
		new Game();
	}

}
