package snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class Snake {
	
	protected List<Point> Body;
	protected Point Direction;
	protected int Bloques; //cuanto ocuparia 
	
	public Snake(){
		Body = new ArrayList<Point>();
		Direction = new Point(1,0);
		this.Reset();
	}
	
	public void draw(Graphics g){
		int c = 0;
		for (Point p : Body){
			if (c % 2 == 0){
				g.setColor(new Color(123,204,59));
			}else{
				g.setColor(new Color(172,255,163));
			}
			g.fillRect((int)p.getX(), (int)p.getY(), MainWindow.Pixel, MainWindow.Pixel);
			c++;
			
		}
	}

	
	public void SetDirection (int x, int y ){
		
		if (this.Bloques == 1 || x+this.Direction.getX() != 0 || y+this.Direction.getY() != 0){ //significa que no podes hacer arriba-abajo o izq- der 
			this.Direction.setLocation(x, y);	
		}
	}
	
	public Point GetDirection () {
		Point Aux = new Point(this.Direction);
		return Aux;
	}
	
	public Point GetHead(){
		return this.Body.get(this.Body.size() - 1);
	}
	
	public boolean FaceWall(Point f){
		
		if (f.getX() >= MainWindow.Width* MainWindow.Pixel || f.getY() >= MainWindow.Height* MainWindow.Pixel){
			
			return true;
		}
		if (f.getX()  < 0 || f.getY() < 0 ){
			
			return true;
		}		
		return false;
	}
	
	
	
	public boolean SelfDeath(){

		Point Proximo = new Point(this.GetHead());
		Proximo.translate( (int)this.Direction.getX()* MainWindow.Pixel, (int)this.Direction.getY()* MainWindow.Pixel);
		return this.inSnake(Proximo);
	}
	
	public boolean inSnake(Point p){
		
		if (this.Body.contains(p)){
			
			return true;
		}
		return false;
	}
	
	public void Move(){
		Point Proximo = new Point(this.Body.get(this.Body.size() - 1));
		Proximo.translate( (int)this.Direction.getX() *MainWindow.Pixel , (int)this.Direction.getY()*MainWindow.Pixel );
		
		if (this.Body.size() != this.Bloques){
			this.Body.remove(0); //por el corrimiento el primero se va
		}
		this.Body.add(Proximo);
		
		
	}
	
	public void Reset(){
		

		Body.clear();
		Direction.setLocation(1, 0);
		Bloques = 2;
		Body.add(new Point(1* MainWindow.Pixel , 2 * MainWindow.Pixel));
		Body.add(new Point(2* MainWindow.Pixel , 2 * MainWindow.Pixel));
		
	}
	
	public boolean eat (Food f){
		if (this.GetHead().equals(f.GetLocation())){
			return true;
		}
		return false;
	}
	
	public void IncrementLenght (){
		this.Bloques++;
	}
}
