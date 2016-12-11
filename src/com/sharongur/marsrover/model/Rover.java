package com.sharongur.marsrover.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javafx.scene.image.Image;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.main.Game;
import com.sharongur.marsrover.main.HUD;

public class Rover extends GameObject implements Movable {

	private Direction direction;
	private Handler handler;
	
	public Rover(int xPosition, int yPosition, Type type,float width, float height, Handler handler) {
		super(xPosition, yPosition, type, width, height);
		direction = Direction.Up;
		this.handler = handler;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)xPosition,  (int)yPosition, (int)width, (int)height);
	}
	
	public void move(int ForwardBackwards){
		Direction roverDirection = (this.getDirection());
		float roverWidth = (this.getWidth());
		float roverHeight = (this.getHeight());		
		float moveTo, handledMoveTo;
		switch (roverDirection){
		case Up:
			moveTo = this.getYPosition()-(this.getHeight()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, false);
			if(!collision(this.getXPosition(),handledMoveTo)){
				this.setYPosition(handledMoveTo);
			}
			break;
		case Down:
			moveTo = this.getYPosition()+(this.getHeight()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, false);
			if(!collision(this.getXPosition(),handledMoveTo)){
				this.setYPosition(handledMoveTo);
			}
			break;
		case Left:
			moveTo = this.getXPosition()-(this.getWidth()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, true);
			if(!collision(handledMoveTo, this.getYPosition())){
				this.setXPosition(handledMoveTo);
			}
			break;
		case Right:
			moveTo = this.getXPosition()+(this.getWidth()*ForwardBackwards);
			handledMoveTo = handleMoveOutOfBounds(moveTo, roverWidth, roverHeight, true);
			if(!collision(handledMoveTo, this.getYPosition())){
				this.setXPosition(handledMoveTo);
			}
			break;
		default:
			break;
		}
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
	
	private boolean collision(float xPosition, float yPosition) {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if(tempObject.getType() == Type.Obstacle){
				if(tempObject.getBounds().intersects(new Rectangle(	(int)(xPosition ),
																	(int)(yPosition ),
																	(int)this.getWidth(),(int)this.getHeight()))){
					
					System.out.println("The Rovers Sensors Detect an Obstacle Ahead. Move Order Cancled");
					HUD.HEALTH -= 10;
					return true;
				}
			}
		}
		return false;
	}


	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	
	
	

}
