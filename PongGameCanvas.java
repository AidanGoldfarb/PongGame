/**
 * Game canvas class, is a Jpanel with a KeyListener
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongGameCanvas extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;  //constants and variables
	private static int time = 10;					// time variable to adjust timer in seconds
	private static int tCount = 0;					// variable to be adjusted every tic, then /40 to convert to seconds
	private static int level = 1;	
	private static int xM = 10, yM = 6;				// multiplies for x and y velocities, changes every level
	public static boolean gameOver = false;			//will end game when lives = 0
	private static int score = 0, lives = 3;		
	private Timer timer;							//initializing timer
	public static int barX = 0,ballX = 0,ballY = 0, c, g = 1;	// positional variables for ball and bar
	private static double ballT = Math.toRadians(295), ballVx, ballVy; 
	
	
	public PongGameCanvas() {						//constructor whichs adds listener and timer
		setFocusable(true);
		timer = new Timer(25, new TimerCallback());
		addKeyListener(this);
		timer.start();
	}
	
	protected class TimerCallback implements ActionListener {	//timer class
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			tCount++;
			if(tCount % 40 == 0) {								//adjusts tcount to seconds
				time--;
			}
			if(time==0) {										//on to next level
				time = 15;
				xM = xM*2;
				yM = yM*2;
				level++;
			}
			int h = getHeight()-1;
			int w = getWidth()-1;
			int barY = h-(h/10);
			changeV(ballX,ballY,barY,barX,w);					//method that changes ball state
			ballX += ballVx;
			ballY += ballVy;
			barX += c;										
			repaint();
			if(lives == 0)										//when they lose
				timer.stop();
		}
		
	}
	/*
	 * method that adjusts ball state, each if/ifelse statement corresponds to a wall or event
	 */
	public static void changeV(int x, int y, int barY, int barX, int w) {
		if(y <= 0) {	
			//x,-y
			g++;
			ballVx = xM*Math.cos(ballT);
			ballVy = yM*Math.sin(ballT); //-g could be added for gravity 
			ballVy = -ballVy;
			
		}
		else if(x < 0) {
			//-x,y
			ballVx = -ballVx;
		}
		else if(y>= barY-50) {
			if(barX-50<=ballX && barX+50>=ballX) {
				//x,-y
				ballVy = -ballVy;
				score++;
				g=1;
			}
			else {
				//fall
				if(lives != 0) {
					lives--;
					ballX = 0;
					ballY = 0;
				}
				if(lives == 0) {
				gameOver = true;
				ballVy = 0;
				ballVx = 0;
				}
			}
		}
		else if(x>= w-50) {
			//-x,y
			ballVx = -ballVx;
		}
		else if((ballX > 495 && ballY < 5) || ballX < 5 && ballY<5) {
			ballVx = -ballVx;
			ballVy = -ballVy;
		}
		
	}
	/*
	 * paint method
	 */
	public void paint(Graphics g) {
		int w = getWidth()-1;
		int h = getHeight()-1;
		g.clearRect(0, 0, w, h);
		g.setColor(Color.CYAN);
		g.fillRect(barX, h-(h/10), 50, 10); //paints bar
		g.setColor(Color.RED);
		g.fillOval(ballX, ballY, 50, 50);  //paints ball
		g.setColor(Color.MAGENTA);
		g.drawString("SCORE: " + score, getWidth()-100, 10);  //paints score
		g.drawString("TIME: " + time, w/2-30, 20);				//paints time
		g.drawString("Level " + level, w/2-30, 30);				//paints level
		if(lives == 3) {										//each if/else adjusts number of squares coresponding to lives
		g.fillRect(3*getWidth()/4, 40, 20, 20);
		g.fillRect(3*getWidth()/4 + 40, 40, 20, 20);
		g.fillRect(3*getWidth()/4 + 80, 40, 20, 20);
		}
		else if(lives == 2) {
			g.fillRect(3*getWidth()/4, 40, 20, 20);
			g.fillRect(3*getWidth()/4 + 40, 40, 20, 20);
		}
		else if(lives ==1 )
			g.fillRect(3*getWidth()/4, 40, 20, 20);
		if(gameOver) {										//when they lose
			g.setFont(new Font("Impact", Font.PLAIN, 35));
			g.setColor(Color.green);
			g.drawString("Game Over", getWidth()/2 - 95, getHeight()/2);		//prints "GameOver" and their score
			g.drawString("Score = " + score, getWidth()/2 - 95, getHeight()/2+50);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) { //keylistener method
	    int keyCode = arg0.getKeyCode();
	    switch( keyCode ) { 
	        case KeyEvent.VK_LEFT: //when left is pressed
	        	if(barX < 1) { //if on edge
	        		c= 0;
	        		barX = 0;
	        	}
	        	else
	        		c = -10; //sets bar incrementer
	            break;
	            
	        case KeyEvent.VK_RIGHT:	//when right is pressed
	        	if(barX+52 > getWidth()) { //if on edge
	        		c= 0;
	        		barX = getWidth()-51;
	        	}
	        	else
	        		c = 10;   //sets bar incrementer 
	            break;
	     }
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		c = 0;		//stops bar moving after key released 
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
