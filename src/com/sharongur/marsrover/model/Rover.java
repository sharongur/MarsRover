package com.sharongur.marsrover.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javafx.scene.image.Image;

import com.sharongur.marsrover.handler.Handler;
import com.sharongur.marsrover.main.Game;

public class Rover extends GameObject {

	private Direction direction;
	private Handler handler;
	
	public Rover(int xPosition, int yPosition, Type type,float width, float height, Handler handler) {
		super(xPosition, yPosition, type, width, height);
		direction = Direction.Up;
		this.handler = handler;
	}

	@Override
	public void tick() {
		collision();
	}

	private void collision(){
		
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)xPosition,  (int)yPosition, (int)width, (int)height);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	
	
	

}
