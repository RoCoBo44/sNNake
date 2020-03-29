package snake;

public class Layer {
	
	// Esta clase hace referencia a la capa actual y a los pesos y bias que hay entre ella y la anterior
	
	private Double[][] Weights;
	private Double[] Bias;
	private int previousNeurons;
	private int nNeurons;
	private ActivationFunction AF;
	
	// necesarias para backpropagation
	private Double[] Input;
	private Double[] CurrentError;

	public Layer(int previousNeurons, int neurons, ActivationFunction f) {
		this.nNeurons = neurons;
		this.previousNeurons = previousNeurons;
		this.AF = f;
		
		//cargo Bias
		Bias = new Double[this.nNeurons];
		for (int i = 0 ;  i < this.nNeurons; i++){
			Bias[i] = Math.random();
		}
		
		this.Input = new Double[this.previousNeurons];
		

		//Cargo pesos
		this.Weights = new Double [this.previousNeurons] [this.nNeurons]; //fila columna  
		for (int i = 0 ; i < this.nNeurons; i++){
			for (int j = 0 ; j < this.previousNeurons ; j++){
				this.Weights[j][i] = Math.random();
			}
		}
	}
	
	public String toString(){
		String s = null;
		
		s = "Bias: \n ";
		
		for (int i = 0 ;  i < this.nNeurons; i++){
			s = s + Bias[i] +" ";
		}
		s = s +"\n";
		
		s = s + "Matriz de pesos: \n";
		for (int i = 0 ; i < this.previousNeurons; i++){
			for (int j = 0 ; j < this.nNeurons ; j++){
				s = s + " "+ this.Weights[i][j];
			}
			s = s + "\n";
		}		
		return s;
	}
	
	public int getNeurons() {
		return nNeurons;
	}
	
	public int getPreviousNeurons() {
		return previousNeurons;
	}
	
	public Double[] Calculate(Double[] input ){
		
		if (input.length == this.previousNeurons){
			
			for (int i = 0 ;  i < this.previousNeurons ; i++){
				this.Input[i] = input[i]; //encapsulo
			}
			
			
			//veo que esten bien cargadas las cosas
			Double[] output =  new Double[this.nNeurons];
			
			for (int i = 0; i < this.nNeurons; i++){
				output[i] = 0.0 ;
				for (int j = 0 ; j < this.previousNeurons ; j++){ //basada en la formula que esta en todos lados
					output[i] += this.Weights[j][i]*input[j]; 
				}
				output[i] += this.Bias[i]; 
				output[i] = this.AF.transform(output[i]); //ReLu o Sigmoid --parace ser que para el output solo puede /tiene que ir sigmoid
				
			}
			return output;
		}
		System.out.println("Tamaños NO son correctos en Layer");
		return null;
	}
	

	
	public void SetBias(Double LearningRate){
		
		
			for (int i = 0 ; i < this.nNeurons ; i++){
				this.Bias[i] = this.Bias[i] + LearningRate * this.CurrentError[i];
			}
		
			
	}
	
	public void SetWeights(Double LearningRate){
		
		
			for (int i = 0 ; i < this.nNeurons ; i++){
				for (int j =  0 ;  j < this.previousNeurons ; j++){
					this.Weights[j][i] = this.Weights[j][i] + LearningRate * this.CurrentError[i] * this.Input[j];
				}
			}
		
	}
	
	public void SetError(Double[] e){
		this.CurrentError = e; //rompe encap
	}
	
	public Double[] GenerateError(Double[] previousError){
		
		// calculo el error que genera este layer
		
		Double[] Error = new Double[this.Input.length];
		for (int i = 0 ; i < this.previousNeurons; i++){
			Error[i] = 0.0;
			for (int j = 0 ; j < this.nNeurons ; j++){
				Error[i] += this.Weights[i][j]*previousError[j];
			}
			Error[i] = Error[i] * this.AF.Derivative(this.Input[i]);
		}
		return Error;
	}

}
