package com.sharongur.marsrover.handler;

import java.awt.Graphics;
import java.util.LinkedList;

import com.sharongur.marsrover.model.GameObject;

public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick(){
		for(int i = 0; i< objects.size(); i++){
			GameObject tempObject = objects.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i< objects.size(); i++){
			GameObject tempObject = objects.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object){
		this.objects.add(object);
	}
	
	public void removerObject(GameObject object){
		this.objects.remove(object);
	}
}
