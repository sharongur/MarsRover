package com.sharongur.marsrover.model;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends GameObject {

	public Obstacle(float xPosition, float yPosition, Type type, float width,
			float height) {
		super(xPosition, yPosition, type, width, height);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)xPosition,  (int)yPosition, (int)width, (int)height);
	}

}
