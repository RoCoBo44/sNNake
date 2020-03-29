package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Food {

	private Point Location;
	
	public Food(){
		Location = new Point();
		this.NewLocation();
	}
	
	public Food(Food f){
		Location = new Point();
		Location.setLocation(f.GetLocation().getX(), f.GetLocation().getY());
	}
	
	public void SetLocation(Food f){
		Location.setLocation(f.GetLocation().getX(), f.GetLocation().getY());
	}
	
	public void NewLocation(){
		Location.setLocation(Math.floor(Math.random()*MainWindow.Width)*MainWindow.Pixel, Math.floor(Math.random()*MainWindow.Height)*MainWindow.Pixel);
	//	System.out.println(Location.toString());
	}
	
	public void draw (Graphics g){
		g.setColor(new Color(204,96,59));
		g.fillRect((int)Location.getX(), (int)Location.getY(), MainWindow.Pixel, MainWindow.Pixel);
	}
	
	public Point GetLocation(){
		Point L = new Point();
		L.setLocation(Location.getX(), Location.getY());
		return L;
	}
}
