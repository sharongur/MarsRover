package com.sharongur.marsrover.model;

public enum Direction {
	
	Left(270),
	Right(90),
	Up(360),
	Down(180);
	
	 private final int degrees;
	 	Direction(int degrees) { this.degrees = degrees; }
	    public int getValue() { return degrees; }
}
