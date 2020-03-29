package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class maintesting {

	public static void main(String[] args) {

//		ActivationFunction af = new Sigmoid();
//		List<Integer> Layers = new ArrayList<Integer>();
//		
//		int i = 2;
//		Layers.add(i);
//		Layers.add(4);
//		Layers.add(4);
//		Layers.add(2);
//		
//		
//		NeuralNetwork NN = new NeuralNetwork(Layers, af);
//		
//		Double[] inp = new Double[i];
//		for (int j = 0 ; j < i ; j++){
//			inp[j] = 0.0;
//		}
//		
//		Double[] cal = NN.Calculate(inp);
//		
//		
//		if (cal != null){
//			System.out.println("Real Output");
//			for (int e = 0; e < cal.length ; e++){
//				System.out.println(cal[e]);
//			}
//			System.out.println(cal.length);
//		}
//		
//		Double[] target = new Double[2]; 
//		
//		for (int k =  0 ; k < 3000 ; k++){
//			inp[0] = 0.0;
//			inp[1] = 0.0;
//			target[0] = 0.0;
//			target[1] = 0.0;
//			NN.Train(inp,target, 0.3);
//			inp[0] = -1.0;
//			inp[1] = -1.0;
//			target[0] = 0.0;
//			target[1] = 0.0;
//			NN.Train(inp,target, 0.3);
//			inp[0] = 0.0;
//			inp[1] = 1.0;
//			target[0] = 1.0;
//			target[1] = 0.0;
//			NN.Train(inp,target, 0.3);
//			inp[0] = 1.0;
//			inp[1] = 0.0;
//			target[0] = 0.0;
//			target[1] = 1.0;
//			NN.Train(inp,target, 0.3);
//			
//		}
//		
//		inp[0] = 0.0;
//		inp[1] = 0.0;
//		cal = NN.Calculate(inp);
//		
//		
//		if (cal != null){
//			System.out.println("Real Output");
//			for (int e = 0; e < cal.length ; e++){
//				System.out.println(cal[e]);
//			}
//			System.out.println(cal.length);
//		}
//		
//		inp[0] = 0.0;
//		inp[1] = 1.0;
//		cal = NN.Calculate(inp);
//		
//		
//		if (cal != null){
//			System.out.println("Real Output");
//			for (int e = 0; e < cal.length ; e++){
//				System.out.println(cal[e]);
//			}
//			System.out.println(cal.length);
//		}
//		inp[0] = 1.0;
//		inp[1] = 0.0;
//		cal = NN.Calculate(inp);
//		
//		
//		if (cal != null){
//			System.out.println("Real Output");
//			for (int e = 0; e < cal.length ; e++){
//				System.out.println(cal[e]);
//			}
//			System.out.println(cal.length);
//		}
//		inp[0] = -1.0;
//		inp[1] = -1.0;
//		cal = NN.Calculate(inp);
//		
//		
//		if (cal != null){
//			System.out.println("Real Output");
//			for (int e = 0; e < cal.length ; e++){
//				System.out.println(cal[e]);
//			}
//			System.out.println(cal.length);
//		}

		Point f =  new Point (100,150);
		
		Point Head = new Point (100,70);
		
		Point MIzq = new Point ((int)Head.getX(),(int)Head.getY()-10); 
		Point MFron = new Point ((int)Head.getX()-10,(int)Head.getY()); 
		Point MDer = new Point ((int)Head.getX(),(int)Head.getY()+10); 
		Point Motro = new Point ((int)Head.getX()+10,(int)Head.getY()); 
		
//		System.out.println((Math.atan2((f.getY()- Head.getY()) - (MDer.getY()- Head.getY()), (f.getX()- Head.getX() )- (MDer.getX() - Head.getX() )))*180/Math.PI);
//		System.out.println((Math.atan2((f.getY()- Head.getY()) - (MFron.getY()- Head.getY()), (f.getX()- Head.getX() )- (MFron.getX() - Head.getX() )))*180/Math.PI);
//		System.out.println((Math.atan2((f.getY()- Head.getY()) - (MIzq.getY()- Head.getY()), (f.getX()- Head.getX() )- (MIzq.getX() - Head.getX() )))*180/Math.PI);
//		System.out.println("-------");
//		System.out.println((Math.atan2((f.getX()- Head.getX() )- (MDer.getX() - Head.getX() ), (f.getY()- Head.getY()) - (MDer.getY()- Head.getY())))*180/Math.PI);
//		System.out.println((Math.atan2((f.getX()- Head.getX() )- (MFron.getX() - Head.getX() ),(f.getY()- Head.getY()) - (MFron.getY()- Head.getY())))*180/Math.PI);
//		System.out.println((Math.atan2((f.getX()- Head.getX() )- (MIzq.getX() - Head.getX() ),(f.getY()- Head.getY()) - (MIzq.getY()- Head.getY())))*180/Math.PI);
//	
//		System.out.println("---examples------");
//		System.out.println(Math.atan2(1 ,  0)*180/Math.PI);
//		System.out.println(Math.atan2(0 ,  1)*180/Math.PI);
//		System.out.println(Math.atan2(f.getY()- Head.getY(),f.getX()- Head.getX())*180/Math.PI);
//		
//		
//		System.out.println((double)-1);
		
		System.out.println("---examples------");
		Double a = AngleToFood(MIzq,f,Head);
		Double b = AngleToFood(MFron,f,Head);
		Double c = AngleToFood(MDer,f,Head);
		Double d = AngleToFood(Motro,f,Head);
		
	}
	private static Double AngleToFood(Point p, Point F, Point Head){
			// -1 to 1   -- Using angle to define direction
			// atan2 -- angulo entre el primer punto Y, y el segundo punto X
//			Double aux  = Math.atan2(F.GetLocation().getX() - p.getX(), F.GetLocation().getY() - p.getY());
//			return aux/180;
			
			Double AngleDirection = Math.atan2(p.getY()-Head.getY(), p.getX()-Head.getX());
			
			//esto se hace para tener el angulo siempre teniendo en cuenta de donde viene la serpiente
			
			Double AngleFood = Math.atan2(F.getY()-Head.getY(), F.getX()-Head.getX()); 
		
			System.out.println("Angulos: ");
			System.out.println("An Dir: "+ AngleDirection);
			System.out.println("An Food: "+ AngleFood);

			//System.out.println("An resta / 360: "+ (AngleDirection-AngleFood)/360);
			if ((AngleDirection-AngleFood) > Math.PI){
				System.out.println("An resta a grados 360-x: "+ (2*Math.PI-(AngleDirection-AngleFood))*180/Math.PI);
				System.out.println("An resta a grados 360-x: "+ (2*Math.PI-(AngleDirection-AngleFood))/180);
				return (2*Math.PI-(AngleDirection-AngleFood))/180;
			}
			if ((AngleDirection-AngleFood) < -Math.PI){
				System.out.println("An resta a grados 360+x: "+ (2*Math.PI+(AngleDirection-AngleFood))*180/Math.PI);
				System.out.println("An resta a grados 360-x: "+ (2*Math.PI+(AngleDirection-AngleFood))/180);
				return (2*Math.PI-(AngleDirection+AngleFood))/180;
			}
			System.out.println("An resta a grados: "+ (AngleDirection-AngleFood)*180/Math.PI);
			System.out.println("An resta / 180: "+ (AngleDirection-AngleFood)/180);
			return (AngleDirection-AngleFood)/180;
		}
		
}
	
