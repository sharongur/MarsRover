package com.sharongur.marsrover.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.main.Game;
import com.sharongur.marsrover.main.HUD;

public class Spawner implements Render {
	
	private Handler handler;
	private HUD hud;
	private Game game;
	private int[][] obstacleMap;

	public  int MAP_X, MAP_Y;
	
	
	public Spawner(Handler handler,Game game, HUD hud) {
		super();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}
	
	public void startGame(){
		Scanner reader = new Scanner(System.in);
		System.out.println("Please Choose the Map sizes (EG - 10 10)");
		MAP_X = reader.nextInt();
		MAP_Y = reader.nextInt();
		obstacleMap = new int[MAP_X][MAP_Y];

		// we set the rover size in relation to our window size
		float roverWidth =   ((Game.WIDTH)/ (MAP_X)); 
		float roverHeight = ((Game.HEIGHT - Game.TEXT_HEIGHT) / (MAP_Y));
		
		handler.addObject(new Rover(0, (int)Game.TEXT_HEIGHT, Type.Rover, roverWidth, roverHeight, handler));

		addObstaclePhase(reader);
		
		for (int i = 0; i < MAP_X; i++) {
			for (int j = 0; j < MAP_Y; j++) {
				if(obstacleMap[i][j] == 1){
					handler.addObject(new Obstacle((i)*roverWidth, (j)*roverHeight + (int)Game.TEXT_HEIGHT, Type.Obstacle, roverWidth -2, roverHeight-2));
				}
			}
		}	
		
		
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, (int)Game.WIDTH, (int)Game.HEIGHT);
		g.setColor(Color.white);
		g.drawLine(0, (int)Game.TEXT_HEIGHT,(int) Game.WIDTH, (int)Game.TEXT_HEIGHT);
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

}
