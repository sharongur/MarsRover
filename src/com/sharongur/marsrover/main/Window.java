package com.sharongur.marsrover.main;

import java.awt.Canvas;
import java.awt.Container;
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
	
		Container c = frame.getContentPane();
		c.setBackground(game.gameBackgroundColor);
		c.setPreferredSize(new Dimension((int)width, (int)height));
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.pack();
		
		frame.setVisible(true);
		
		game.start();
		
	}

}
