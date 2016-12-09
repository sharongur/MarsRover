package com.sharongur.marsrover.model;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject { 

	protected  float xPosition, yPosition, width, height;
	protected Type type;

	public abstract void tick();
	public abstract void render(Graphics g);
	
	public Rectangle getBounds() {
		return new Rectangle((int)xPosition,(int)yPosition,(int)width,(int)height);
	}
	
	public GameObject(float xPosition, float yPosition, Type type, float width, float height){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.type = type;
		this.width = width;
		this.height = height;
	}

	
	public float getXPosition() {
		return xPosition;
	}
	public void setXPosition(float startXPos) {
		this.xPosition = startXPos;
	}
	public float getYPosition() {
		return yPosition;
	}
	public void setYPosition(float startYPos) {
		this.yPosition = startYPos;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	
}
