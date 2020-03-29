package snake;

public class ReLu implements ActivationFunction {

	public ReLu(){
			
	}
	
	public Double transform(Double in) {
		
		// La mas usada pero con el output puede dar error
		if (in < 0.0){
			return 0.0;
		}else{
			return in;
		}
	}

	@Override
	public Double Derivative(Double in) {
		if (in > 0){
			return 1.0;
		}
		return 0.0;
	}

}
