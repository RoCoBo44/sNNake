package snake;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;


public class MainWindow extends Applet implements Runnable, KeyListener {

	static int Width ;
	static int Height ;
	static int Pixel;
	
	private Graphics gfx;
	private Image img;
	private AISnake s;
	private Thread thread;
	private Food fo;
	private boolean InTraining =  false;
	private boolean AutoMove =  false;
	
	public void init(){
		this.Width = 20;
		this.Height = 20;
		this.Pixel = 10;
		
		this.resize(this.Width*this.Pixel, this.Height*this.Pixel);
		img = createImage(this.Width*this.Pixel, this.Height*this.Pixel);
		gfx = img.getGraphics();
		
		
		this.addKeyListener(this);
		s = new AISnake();
		fo = new Food();
		s.SetFood(fo);
		s.SetView(1);
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g){
		gfx.setColor(new Color(20,20,20));
		gfx.fillRect(0, 0, this.Width*this.Pixel , this.Height*this.Pixel);
		fo.draw(gfx);
		s.draw(gfx);
		
		
		g.drawImage(img, 0, 0, null);
	}
	
	public void update (Graphics g){
		paint(g);
	}
	
	public void repaint(Graphics g){
		paint (g);
	}

	@Override
	public void run() {
		
		while(true){
			
			if (s.FaceWall(s.GetHead()) || s.SelfDeath()){
				s.Reset();
			}else{
				
				if (s.eat(fo)){
					s.IncrementLenght();
					fo.NewLocation();
				}
				if (this.InTraining){
					s.StartTraining(900000, 38, 0.01, 5); //iteracion , longitud snake max , learning rate, continue lenght iter
					this.InTraining = false;
				}
				if (this.AutoMove){
					s.AutoMove();
				}else{
					s.Move();
				}
			}
			this.repaint();
			try { Thread.sleep(100); } catch (InterruptedException e) {	e.printStackTrace(); }
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!this.AutoMove){
			if (e.getKeyCode() == KeyEvent.VK_UP){
				s.SetDirection(0, -1);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN){
				s.SetDirection(0, 1);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
				s.SetDirection(-1, 0);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				s.SetDirection(1, 0);
			}
			if (e.getKeyCode() == KeyEvent.VK_A){
				this.AutoMove = true;
			}
			
		}else{
			if (e.getKeyCode() == KeyEvent.VK_A){
				this.AutoMove = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			this.InTraining = true;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
}

	