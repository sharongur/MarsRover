package com.sharongur.marsrover.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -173919501710755967L;
	
	public Window ( float width, float height, String title, Game game){
		JFrame frame = new JFrame(title);
		frame.getContentPane().setPreferredSize(new Dimension((int)width,(int)height));
		frame.setMaximumSize(new Dimension((int)width,(int)height));
		frame.setMinimumSize(new Dimension((int)width,(int)height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		
	}

}
