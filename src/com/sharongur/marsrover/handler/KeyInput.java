package com.sharongur.marsrover.handler;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.sharongur.marsrover.main.Game;
import com.sharongur.marsrover.main.HUD;
import com.sharongur.marsrover.model.Direction;
import com.sharongur.marsrover.model.GameObject;
import com.sharongur.marsrover.model.Rover;
import com.sharongur.marsrover.model.State;
import com.sharongur.marsrover.model.Type;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private final int forward = 1;
	private final int backward = -1;
	
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			
			if(tempObject.getType() == Type.Rover){
				if(key == KeyEvent.VK_UP){
					
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			
			if(tempObject.getType() == Type.Rover){
				Direction roverDirection;
				Direction newDirection;
				if(Game.gameState == State.Game){
					switch (key){
					case KeyEvent.VK_UP:
						((Rover)tempObject).move(forward);
						break;
					case KeyEvent.VK_DOWN:
						((Rover)tempObject).move(backward);
						break;
					case KeyEvent.VK_LEFT:
						roverDirection = ((Rover) tempObject).getDirection();
						System.out.println("Rover Direction: " + roverDirection);
						newDirection = getDirectionFromDegrees(roverDirection.getValue() - 90);
						System.out.println("New Rover Direction: " + newDirection);
						((Rover) tempObject).setDirection(newDirection);
						break;
					case KeyEvent.VK_RIGHT:
						roverDirection = ((Rover) tempObject).getDirection();
						System.out.println("Rover Direction: " + roverDirection);
						newDirection = getDirectionFromDegrees(roverDirection.getValue() + 90);
						System.out.println("New Rover Direction: " + newDirection);
						((Rover) tempObject).setDirection(newDirection);
						break;
					case KeyEvent.VK_ESCAPE:
						Game.gameState = State.Menu;
					}
				}
			}
		}
	}
	
	private Direction getDirectionFromDegrees(int degrees){
		switch (degrees){
			case 450:// will only be when turning right when facing up, to the new direction is always up
				return Direction.Right;
			case 360:
				return Direction.Up;
			case 270:
				return Direction.Left;
			case 180:
				return Direction.Down;
			case 90:
				return Direction.Right;
			case 0:
				return Direction.Up;
		}
		return Direction.Up;
	}
}
