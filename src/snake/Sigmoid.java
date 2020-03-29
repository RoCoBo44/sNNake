package snake;

public class Sigmoid implements ActivationFunction{


	public Sigmoid(){
		
	}
	
	public Double transform(Double in) {
		

		//problema con gradiente, si es muy sercano a 0 se estanca y su formula no esta centrada en 0 tampoco
		return (1/ (1 + Math.exp(-in)) );
	}

	@Override
	public Double Derivative(Double in) {
		
		return in * (1.0 - in);
	}

}
