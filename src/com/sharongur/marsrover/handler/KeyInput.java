package com.sharongur.marsrover.handler;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.sharongur.marsrover.main.Game;
import com.sharongur.marsrover.model.Direction;
import com.sharongur.marsrover.model.GameObject;
import com.sharongur.marsrover.model.Rover;
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
				
				switch (key){
				case KeyEvent.VK_UP:
					moveRover((Rover)tempObject, forward);
					break;
				case KeyEvent.VK_DOWN:
					moveRover((Rover)tempObject, backward);
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
				}
			}
		}
	}
	
	private void moveRover(Rover rover,int ForwardBackwards){
		Direction roverDirection = (rover.getDirection());
		float roverWidth = (rover.getWidth());
		float roverHeight = (rover.getHeight());		
		float moveTo, handledMoveTo;
		switch (roverDirection){
		case Up:
			moveTo = rover.getYPosition()-(rover.getHeight()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, false);
			if(!collision(rover.getXPosition(),handledMoveTo, rover)){
				rover.setYPosition(handledMoveTo);
			}
			break;
		case Down:
			moveTo = rover.getYPosition()+(rover.getHeight()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, false);
			if(!collision(rover.getXPosition(),handledMoveTo, rover)){
				rover.setYPosition(handledMoveTo);
			}
			break;
		case Left:
			moveTo = rover.getXPosition()-(rover.getWidth()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, true);
			if(!collision(rover.getYPosition(),handledMoveTo, rover)){
				rover.setXPosition(handledMoveTo);
			}
			break;
		case Right:
			moveTo = rover.getXPosition()+(rover.getWidth()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, true);
			if(!collision(rover.getYPosition(),handledMoveTo, rover)){
				rover.setXPosition(handledMoveTo);
			}
			break;
		default:
			break;
		}
	}
	
	private boolean collision(float xPosition, float yPosition, Rover rover) {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if(tempObject.getType() == Type.Obstacle){
				if(tempObject.getBounds().intersects(new Rectangle(	(int)(xPosition ),
																	(int)(yPosition ),
																	(int)rover.getWidth(),(int)rover.getHeight()))){
					System.out.println("The Rovers Sensors Detect an Obstacle Ahead. Move Order Cancled");
					return true;
				}
			}
		}
		return false;
	}

	private float handleMoveOutOfBounds(float moveTo, float roverWidth, float roverHeight, boolean isXCoor ){
		if(moveTo > (Game.WIDTH - roverWidth) && isXCoor){
			return 0;
		}else if(moveTo > (Game.HEIGHT - roverHeight) && !isXCoor){
			return Game.TEXT_HEIGHT;
		}else if(moveTo < 0 && isXCoor){
				return Game.WIDTH - roverWidth; 
			}else if(moveTo < Game.TEXT_HEIGHT && !isXCoor){
				return Game.HEIGHT - roverHeight;
			}
		return moveTo;
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
		return null;
	}
}
