package snake;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AISnake extends Snake {
	
	private NeuralNetwork NN;
	private Food F; //needs to know where to go
	private List<Integer> Neurons; 
	private int view; //this number is for each the block the snake can see

	public  AISnake(){
		super();
		
		Neurons =  new ArrayList<Integer>();
		Neurons.add(8); // IN en el calculate input se ve mejor que hay de inp
		//Neurons.add(MainWindow.Height*MainWindow.Width*2); //Segundo INTENTO, doble matriz
		Neurons.add(12); // HIDDEN
		Neurons.add(12); // HIDDEN
		Neurons.add(3); // OUT dobla iz, sigue derecho, dobla derecha 
		
		NN = new NeuralNetwork(Neurons,new Sigmoid());
	}
	
	public Double[] Decision (Double[] input){
		
		if (input.length == this.Neurons.get(0)){
			Double[] Out = NN.Calculate(input);
			return Out;
		}
		//System.out.println("Cant make a decision because of size"+ input.length + "  " + NN.getNeurons().size());
		return null;
	}
	
	public void SetFood(Food F){
		this.F = F;
	}
	

	public void AutoMove(){
		
		Double[] Output = NN.Calculate(this.CalculateInput());

		this.UpdateDirection(Output);
		
		this.Move();
	}
	
	private Double AngleToFood(Point p){

		
		Double AngleDirection = Math.atan2(p.getY()-this.GetHead().getY(), p.getX()-this.GetHead().getX());
		
		//esto se hace para tener el angulo siempre teniendo en cuenta de donde viene la serpiente
		
		Double AngleFood = Math.atan2(F.GetLocation().getY()-this.GetHead().getY(), F.GetLocation().getX()-this.GetHead().getX()); 
	
		
		if ((AngleDirection-AngleFood) > Math.PI){

			return (2*Math.PI-(AngleDirection-AngleFood))/180;
		}
		if ((AngleDirection-AngleFood) < -Math.PI){

			return (2*Math.PI-(AngleDirection+AngleFood))/180;
		}

		
		return (AngleDirection-AngleFood)/180;
	}
	
	private Double[] CalculateInput(){
				Double[] Input = new Double[this.Neurons.get(0)];
				//the first 7 are vision 
				Input[0] = this.CalculateView(this.ForwardMove());
				
				Point C = new Point(this.Rotate(this.ForwardMove(),1)); //Clockwise
				Point CC = new Point(this.Rotate(this.ForwardMove(),-1)); //CounterClockwise
				
				Input[1] = this.CalculateView(C);
				Input[2] = this.CalculateView(CC);
				
				Input[3] = this.CalculateView(new Point((int)(this.ForwardMove().getX()+C.getX()),(int)(this.ForwardMove().getY()+C.getY()) ));
				Input[4] = this.CalculateView(new Point((int)(this.ForwardMove().getX()+CC.getX()),(int)(this.ForwardMove().getY()+CC.getY()) ));
				

				Input[5] = this.CalculateView(new Point((int)(this.ForwardMove().getX()*-1+C.getX()),(int)(this.ForwardMove().getY()*-1+C.getY()) ));
				Input[6] = this.CalculateView(new Point((int)(this.ForwardMove().getX()*-1+CC.getX()),(int)(this.ForwardMove().getY()*-1+CC.getY()) ));
				

				Input[7] = this.LessAngle();
				

				return Input;
	}
	
	private double LessAngle(){
		int Dir = 2 ;
		Point NextMov = new Point();
		Point FM = this.ForwardMove();
		Double MinorDistance = Double.MAX_VALUE;
		for (int j = -1 ; j < 2 ;j++){
			Point Rotation = this.Rotate(FM, j);
			NextMov.setLocation(Rotation.getX()*MainWindow.Pixel+this.GetHead().getX(), Rotation.getY()*MainWindow.Pixel+this.GetHead().getY());
			if ( Math.abs(this.AngleToFood(NextMov)) < MinorDistance){
				MinorDistance = Math.abs(this.AngleToFood(NextMov));
				Dir = j;
			}
		}
		return (double)Dir;
	}
	
	
	private void UpdateDirection(Double[] Output){
		
		int LuMayor = this.MayorUbication(Output); //The one who is bigger define the curse of action 
		//System.out.println(LuMayor);
		Point NewDir = this.Rotate(this.GetDirection(), --LuMayor);
		this.SetDirection((int)NewDir.getX(), (int)NewDir.getY());
		
	}
	
	private int MayorUbication(Double[] Output){ //te dice donde esta el mayor en el output
		
		Double Mayor = Output[0];
		int LuMayor = 0;
		for (int i = 1 ; i < Output.length ; i++){
			if (Mayor.compareTo(Output[i]) < 0){
				Mayor = Output[i];
				LuMayor = i;
			}
		}
		return LuMayor;
	}
	
	public void SetView(int a){
		this.view = a;
	}
	
	public Double CalculateView(Point dir){
		
		//numer between 0 to 1, being 1 where the snake cant move
		Point Proximo = new Point(this.GetHead());
		
		for (int i = 0 ; i < this.view ; i++){
			Proximo.translate( (int)dir.getX()* MainWindow.Pixel, (int)dir.getY()* MainWindow.Pixel);
			if (this.FaceWall(Proximo) || this.inSnake(Proximo)){
				return (double) (1.0-1.0*((double)i/(double)this.view));
			}
		}
		return 0.0;
	}
	
	private Point Rotate(Point p , int clock){
		
		//calculate direction 
		Point out = new Point();
		if (clock == 1){ // CLOCK WISE
			out.setLocation(p.getY(), p.getX()*-1);
		}else{
			if (clock == -1 ){ // COUNTER CLOCK WISE
				out.setLocation(p.getY()*-1, p.getX());
			}else{
				out.setLocation(p);
			}
		}
		return out;
	}
	

	public void Train(int x, Double ValueTrain){
		
		//Train the neural network
		
		Double[] Solution =  new Double[this.Neurons.get(this.Neurons.size()-1)];

		for (int i = 0 ;  i < this.Neurons.get(this.Neurons.size()-1);i++){
			Solution[i] = 0.0;
		}
		Solution[x+1] = 1.0; //el +1 es porque: -1 izq | 0 siguo igual | 1 der
		
		Double[] Output = this.Decision(this.CalculateInput());
		
		
		if (this.MayorUbication(Output) != this.MayorUbication(Solution)){
			//System.out.println("I LEARNED SOMETHING NEW TODAY");
			NN.Train(this.CalculateInput(), Solution, ValueTrain);
		}

	}
	
	public void StartTraining(int iter, int NumberBody, Double TrainValue, int NContinue){
		
		//guardar los datos importantes -hard copies
		
		Food SaveF = new Food(this.F);
		int SaveBloques = this.Bloques;
		List<Point> SaveBody =  new ArrayList<Point>();
		SaveBody.addAll(this.Body);
		Point SaveDirection =  new Point();
		SaveDirection.setLocation(this.Direction.getX(), this.Direction.getY());
		
		

		
		
		for (int i = 0; i < iter ; i++){
			//cargar datos falsos pero correctos de como seria la solucion 
			this.F.NewLocation();
			
			this.Direction = RandomDirection(this.GetHead());
			this.Body.clear();
			this.Body = RandomBody(NumberBody); 
			

			int Dir = this.RecomendedDirection(2);
			if (Dir < 2){ //significa que encontro algo
				this.Train(Dir, TrainValue); 
				this.TrainRecursively(NContinue, TrainValue);
				
			}

		}
		
		//cargar todo como estaba
		F.SetLocation(SaveF);
		Bloques = SaveBloques;
		Body = SaveBody;
		this.Direction = SaveDirection;


	}
	
	private void TrainRecursively (int i, Double TrainValue){
		
		if (i > 0){

					Point RecomendedPoint=  new Point(this.Rotate(this.ForwardMove(), this.RecomendedDirection(2))); //posible solucion al problema de la doble vuelta
					this.Body.add(new Point ((int)(this.GetHead().getX()+ RecomendedPoint.getX()*MainWindow.Pixel) ,(int)(this.GetHead().getY() + RecomendedPoint.getY()*MainWindow.Pixel)));
					this.Body.remove(0);
					if (this.eat(this.F)){
						F.NewLocation();
					}
					int Dir = this.RecomendedDirection(2);
					if (Dir < 2){
						this.Train(Dir, TrainValue); 
						this.TrainRecursively(i-1, TrainValue);
					}
		}

		
	}
	
	private int RecomendedDirection(int Dir){
		
		Point NextMov = new Point();
		Point FM = this.ForwardMove();
		Double MinorDistance = Double.MAX_VALUE;
		for (int j = -1 ; j < 2 ;j++){
			//NextMov.setLocation(this.ForwardMove().getX()*MainWindow.Pixel+this.GetHead().getX(), this.ForwardMove().getY()*MainWindow.Pixel+this.GetHead().getY()); 
			//Point Rotation = this.Rotate(NextMov, j);
			Point Rotation = this.Rotate(FM, j);
			NextMov.setLocation(Rotation.getX()*MainWindow.Pixel+this.GetHead().getX(), Rotation.getY()*MainWindow.Pixel+this.GetHead().getY());
			//System.out.println(NextMov.toString());
			//System.out.println("j= " + j + " UsedBlock " + this.UsedBlock(NextMov) + " Distance " + Math.abs(this.AngleToFood(NextMov)));
			if (!this.UsedBlock(NextMov) && Math.abs(this.AngleToFood(NextMov)) < MinorDistance){
				MinorDistance = Math.abs(this.AngleToFood(NextMov));
				Dir = j;
				//System.out.println("AND THE WINNER IS " + j);
			}
		}
		return Dir;
	}
	
	private Point ForwardMove(){
		Point Paux = new Point();
		// EL *-1 para que de el contrario
		Paux.setLocation((this.GetHead().getX() - this.Body.get(this.Body.size()-2).getX())/MainWindow.Pixel, (this.GetHead().getY() - this.Body.get(this.Body.size()-2).getY())/MainWindow.Pixel);
		return Paux;
	}

	private List<Point> RandomBody(int VRandom) {
		
		List<Point> LAUX = new ArrayList<Point>();
		Point p1 = new Point ();
		p1.setLocation((int)(Math.random()*MainWindow.Width)* MainWindow.Pixel, (int)(Math.random()*MainWindow.Height)* MainWindow.Pixel);
		while (this.UsedBlock(p1)){
			p1.setLocation((int)(Math.random()*MainWindow.Width)* MainWindow.Pixel, (int)(Math.random()*MainWindow.Height)* MainWindow.Pixel);
		}
		this.Bloques = 1;
		LAUX.add(p1);
		
		Point Paux = p1;
		boolean NoMoreSpace = false;
		
		
		while (this.Bloques < 2+Math.random()*VRandom && !NoMoreSpace ){
			Point Paux2 = this.RandomDirection(Paux);
			Paux = new Point(LAUX.get(LAUX.size()-1));
			
			if (Paux2 ==  null){
				NoMoreSpace = true;
			}else{
				Paux.translate( (int)Paux2.getX()* MainWindow.Pixel, (int)Paux2.getY()* MainWindow.Pixel);
				Bloques++;
				LAUX.add(Paux);
			}
		}
		Collections.reverse(LAUX);
		//System.out.println("NEW BODY: " + LAUX.toString());
		return LAUX;
	}

	private Point RandomDirection(Point in) {
		
		if (this.UsedBlock(new Point ((int)in.getX(),(int)in.getY()+1))  &&  this.UsedBlock(new Point ((int)in.getX()-1,(int)in.getY()))  &&   this.UsedBlock(new Point ((int)in.getX(),(int)in.getY()-1))  &&   this.UsedBlock(new Point ((int)in.getX()+1,(int)in.getY()))   ){
			return null;
		}else{
			Point p = new Point();
			p.setLocation(1, 0);
			for (int i = 0 ; i < 1+Math.random()*4 ; i++){
				p = this.Rotate(p, 1);
				while (this.UsedBlock(p)){
					p = this.Rotate(p, 1);
				}
				
			}
			return p;
		}
		
		
	}
	
	private boolean UsedBlock(Point in){
		if (this.inSnake(in) || this.FaceWall(in)){
			return true;
		}
		return false;
	}

}
